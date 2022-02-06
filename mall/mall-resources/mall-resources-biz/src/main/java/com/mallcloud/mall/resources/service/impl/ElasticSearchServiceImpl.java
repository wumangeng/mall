package com.mallcloud.mall.resources.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.mallcloud.mall.common.core.util.R;
import com.mallcloud.mall.product.api.feign.RemoteAttrService;
import com.mallcloud.mall.product.api.vo.AttrResVO;
import com.mallcloud.mall.resources.api.entity.SkuEsModel;
import com.mallcloud.mall.resources.api.vo.ProSearchParam;
import com.mallcloud.mall.resources.api.vo.ProSearchResp;
import com.mallcloud.mall.resources.config.ElasticSearchConfig;
import com.mallcloud.mall.resources.constant.ESConstant;
import com.mallcloud.mall.resources.service.ElasticSearchService;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.NestedQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.MultiBucketsAggregation;
import org.elasticsearch.search.aggregations.bucket.nested.NestedAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.nested.ParsedNested;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedLongTerms;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ElasticSearchServiceImpl implements ElasticSearchService {
	@Autowired
	private RestHighLevelClient restHighLevelClient;
	
	@Autowired
	private RemoteAttrService attrService;

	@Override
	public boolean addUpSpu(List<SkuEsModel> skuEsModelList) throws IOException {
		//1、建立索引(es中已建立跳过)

		//2、批量保存
		BulkRequest bulkRequest = new BulkRequest();
		for (SkuEsModel skuEsModel : skuEsModelList) {
			//构造保存请求
			IndexRequest indexRequest = new IndexRequest(ESConstant.PRODUCT_INDEX);
			indexRequest.id(skuEsModel.getSkuId().toString());
			indexRequest.source(JSONUtil.toJsonStr(skuEsModel), XContentType.JSON);
			bulkRequest.add(indexRequest);
		}
		BulkResponse result = restHighLevelClient.bulk(bulkRequest, ElasticSearchConfig.COMMON_OPTIONS);
		//是否有错误(true 有错误)
		return !result.hasFailures();
	}

	@Override
	public ProSearchResp searchPro(ProSearchParam param) {
		// 动态构建出查询需要的DSL语句
		ProSearchResp result = null;

		//1、构建检索请求
		SearchRequest searchRequest = buildSearchRequest(param);

		try {
			//2、执行检索请求
			SearchResponse response = restHighLevelClient.search(searchRequest, ElasticSearchConfig.COMMON_OPTIONS);

			//3、分析响应数据，封装结果
			result = buildSearchResult(response, param);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return result;
	}


	/**
	 * 准备检索请求
	 * 模糊匹配，过滤（按照属性，分类，品牌，价格区间，库存），排序，分页，高亮，聚合分析
	 */
	private SearchRequest buildSearchRequest(ProSearchParam searchParam) {
		// 检索请求构建
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

		/**   查询：模糊匹配，过滤（按照属性，分类，品牌，价格区间，库存）   */

		//1. 构建 bool-query
		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

		//1.1 bool-must 模糊匹配
		if (StrUtil.isNotEmpty(searchParam.getKeyword())) {
			boolQueryBuilder.must(QueryBuilders.matchQuery("skuTitle", searchParam.getKeyword()));
		}

		//1.2.1 bool-filter catalogId 按照三级分类id查询
		if (null != searchParam.getCatalog3Id()) {
			boolQueryBuilder.filter(QueryBuilders.termQuery("catalogId", searchParam.getCatalog3Id()));
		}

		//1.2.2 bool-filter brandId 按照品牌id查询
		if (null != searchParam.getBrandId() && searchParam.getBrandId().size() > 0) {
			boolQueryBuilder.filter(QueryBuilders.termsQuery("brandId", searchParam.getBrandId()));
		}

		//1.2.3 bool-filter attrs 按照指定的属性查询
		if (searchParam.getAttrs() != null && searchParam.getAttrs().size() > 0) {
			searchParam.getAttrs().forEach(item -> {
				//attrs=1_5寸:8寸&2_16G:8G
				BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();

				//attrs=1_5寸:8寸
				String[] s = item.split("_");
				String attrId = s[0]; // 检索的属性id
				String[] attrValues = s[1].split(":");//这个属性检索用的值
				boolQuery.must(QueryBuilders.termQuery("attrs.attrId", attrId));
				boolQuery.must(QueryBuilders.termsQuery("attrs.attrValue", attrValues));

				// 每一个属性都要生成一个 nested 查询
				NestedQueryBuilder nestedQueryBuilder = QueryBuilders.nestedQuery("attrs", boolQuery, ScoreMode.None);
				boolQueryBuilder.filter(nestedQueryBuilder);
			});

		}
		//1.2.4 bool-filter hasStock 按照是否有库存查询
		if (null != searchParam.getHasStock()) {
			boolQueryBuilder.filter(QueryBuilders.termQuery("hasStock", searchParam.getHasStock() == 1));
		}
		//1.2.5 skuPrice bool-filter 按照价格区间查询
		if (StrUtil.isNotEmpty(searchParam.getSkuPrice())) {
			//skuPrice形式为：1_500或_500或500_
			RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("skuPrice");
			String[] price = searchParam.getSkuPrice().split("_");
			if (price.length == 2) {
				rangeQueryBuilder.gte(price[0]).lte(price[1]);
			} else if (price.length == 1) {
				if (searchParam.getSkuPrice().startsWith("_")) {
					rangeQueryBuilder.lte(price[1]);
				}
				if (searchParam.getSkuPrice().endsWith("_")) {
					rangeQueryBuilder.gte(price[0]);
				}
			}
			boolQueryBuilder.filter(rangeQueryBuilder);
		}

		// 封装所有的查询条件
		searchSourceBuilder.query(boolQueryBuilder);


		/**
		 * 排序，分页，高亮
		 */
		// 2.1 排序  形式为sort=hotScore_asc/desc
		if (StrUtil.isNotEmpty(searchParam.getSort())) {
			String sort = searchParam.getSort();
			// sort=hotScore_asc/desc
			String[] sortFields = sort.split("_");

			SortOrder sortOrder = "asc".equalsIgnoreCase(sortFields[1]) ? SortOrder.ASC : SortOrder.DESC;
			searchSourceBuilder.sort(sortFields[0], sortOrder);
		}

		// 2.2 分页 from = (pageNum - 1) * pageSize
		searchSourceBuilder.from((searchParam.getPageNum() - 1) * ESConstant.PRODUCT_PAGE_SIZE);
		searchSourceBuilder.size(ESConstant.PRODUCT_PAGE_SIZE);

		// 2.3 高亮
		if (StrUtil.isNotEmpty(searchParam.getKeyword())) {
			HighlightBuilder highlightBuilder = new HighlightBuilder();
			highlightBuilder.field("skuTitle");
			highlightBuilder.preTags("<b style='color:red'>");
			highlightBuilder.postTags("</b>");

			searchSourceBuilder.highlighter(highlightBuilder);
		}

		System.err.println("构建的DSL语句" + searchSourceBuilder.toString());

		/**
		 * 聚合分析
		 */
		//1. 按照品牌进行聚合
		TermsAggregationBuilder brand_agg = AggregationBuilders.terms("brand_agg");
		brand_agg.field("brandId").size(50);

		//1.1 品牌的子聚合-品牌名聚合
		brand_agg.subAggregation(AggregationBuilders.terms("brand_name_agg").field("brandName").size(1));
		//1.2 品牌的子聚合-品牌图片聚合
		brand_agg.subAggregation(AggregationBuilders.terms("brand_img_agg").field("brandImg").size(1));
		searchSourceBuilder.aggregation(brand_agg);

		//2. 按照分类信息进行聚合
		TermsAggregationBuilder catalog_agg = AggregationBuilders.terms("catalog_agg");
		catalog_agg.field("catalogId").size(20);
		catalog_agg.subAggregation(AggregationBuilders.terms("catalog_name_agg").field("catalogName").size(1));
		searchSourceBuilder.aggregation(catalog_agg);

		// 3. 按照属性信息进行聚合
		NestedAggregationBuilder attr_agg = AggregationBuilders.nested("attr_agg", "attrs");
		//3.1 按照属性ID进行聚合
		TermsAggregationBuilder attr_id_agg = AggregationBuilders.terms("attr_id_agg").field("attrs.attrId");
		attr_agg.subAggregation(attr_id_agg);
		//3.1.1 在每个属性ID下，按照属性名进行聚合
		attr_id_agg.subAggregation(AggregationBuilders.terms("attr_name_agg").field("attrs.attrName").size(1));
		//3.1.2 在每个属性ID下，按照属性值进行聚合
		attr_id_agg.subAggregation(AggregationBuilders.terms("attr_value_agg").field("attrs.attrValue").size(50));
		searchSourceBuilder.aggregation(attr_agg);

		log.debug("构建的DSL语句 {}", searchSourceBuilder.toString());

		SearchRequest searchRequest = new SearchRequest(new String[]{ESConstant.PRODUCT_INDEX}, searchSourceBuilder);
		return searchRequest;
	}

	/**
	 * 构建结果数据
	 * 模糊匹配，过滤（按照属性、分类、品牌，价格区间，库存），完成排序、分页、高亮,聚合分析功能
	 */
	private ProSearchResp buildSearchResult(SearchResponse response, ProSearchParam param) {
		ProSearchResp result = new ProSearchResp();

		//1、返回的所有查询到的商品
		SearchHits hits = response.getHits();

		List<SkuEsModel> esModels = new ArrayList<>();
		//遍历所有商品信息
		if (hits.getHits() != null && hits.getHits().length > 0) {
			for (SearchHit hit : hits.getHits()) {
				String sourceAsString = hit.getSourceAsString();
				SkuEsModel esModel = JSON.parseObject(sourceAsString, SkuEsModel.class);

				//判断是否按关键字检索，若是就显示高亮，否则不显示
				if (!StringUtils.isEmpty(param.getKeyword())) {
					//拿到高亮信息显示标题
					HighlightField skuTitle = hit.getHighlightFields().get("skuTitle");
					String skuTitleValue = skuTitle.getFragments()[0].string();
					esModel.setSkuTitle(skuTitleValue);
				}
				esModels.add(esModel);
			}
		}
		result.setProduct(esModels);

		//2、当前商品涉及到的所有属性信息
		List<ProSearchResp.AttrVo> attrVos = new ArrayList<>();
		//获取属性信息的聚合
		ParsedNested attrsAgg = response.getAggregations().get("attr_agg");
		ParsedLongTerms attrIdAgg = attrsAgg.getAggregations().get("attr_id_agg");
		for (Terms.Bucket bucket : attrIdAgg.getBuckets()) {
			ProSearchResp.AttrVo attrVo = new ProSearchResp.AttrVo();
			//1、得到属性的id
			long attrId = bucket.getKeyAsNumber().longValue();
			attrVo.setAttrId(attrId);

			//2、得到属性的名字
			ParsedStringTerms attrNameAgg = bucket.getAggregations().get("attr_name_agg");
			String attrName = attrNameAgg.getBuckets().get(0).getKeyAsString();
			attrVo.setAttrName(attrName);

			//3、得到属性的所有值
			ParsedStringTerms attrValueAgg = bucket.getAggregations().get("attr_value_agg");
			List<String> attrValues = attrValueAgg.getBuckets().stream().map(MultiBucketsAggregation.Bucket::getKeyAsString).collect(Collectors.toList());
			attrVo.setAttrValue(attrValues);

			attrVos.add(attrVo);
		}

		result.setAttrs(attrVos);

		//3、当前商品涉及到的所有品牌信息
		List<ProSearchResp.BrandVo> brandVos = new ArrayList<>();
		//获取到品牌的聚合
		ParsedLongTerms brandAgg = response.getAggregations().get("brand_agg");
		for (Terms.Bucket bucket : brandAgg.getBuckets()) {
			ProSearchResp.BrandVo brandVo = new ProSearchResp.BrandVo();

			//1、得到品牌的id
			long brandId = bucket.getKeyAsNumber().longValue();
			brandVo.setBrandId(brandId);

			//2、得到品牌的名字
			ParsedStringTerms brandNameAgg = bucket.getAggregations().get("brand_name_agg");
			String brandName = brandNameAgg.getBuckets().get(0).getKeyAsString();
			brandVo.setBrandName(brandName);

			//3、得到品牌的图片
			ParsedStringTerms brandImgAgg = bucket.getAggregations().get("brand_img_agg");
			String brandImg = brandImgAgg.getBuckets().get(0).getKeyAsString();
			brandVo.setBrandImg(brandImg);

			brandVos.add(brandVo);
		}
		result.setBrands(brandVos);

		//4、当前商品涉及到的所有分类信息
		//获取到分类的聚合
		List<ProSearchResp.CatalogVo> catalogVos = new ArrayList<>();
		ParsedLongTerms catalogAgg = response.getAggregations().get("catalog_agg");
		for (Terms.Bucket bucket : catalogAgg.getBuckets()) {
			ProSearchResp.CatalogVo catalogVo = new ProSearchResp.CatalogVo();
			//得到分类id
			String keyAsString = bucket.getKeyAsString();
			catalogVo.setCatalogId(Long.parseLong(keyAsString));

			//得到分类名
			ParsedStringTerms catalogNameAgg = bucket.getAggregations().get("catalog_name_agg");
			String catalogName = catalogNameAgg.getBuckets().get(0).getKeyAsString();
			catalogVo.setCatalogName(catalogName);
			catalogVos.add(catalogVo);
		}

		result.setCatalogs(catalogVos);
		//===============以上可以从聚合信息中获取====================//

		//5、分页信息-页码
		result.setPageNum(param.getPageNum());
		//5、1分页信息、总记录数
		long total = hits.getTotalHits().value;
		result.setTotal(total);

		//5、2分页信息-总页码-计算
		int totalPages = (int) total % ESConstant.PRODUCT_PAGE_SIZE == 0 ?
				(int) total / ESConstant.PRODUCT_PAGE_SIZE : ((int) total / ESConstant.PRODUCT_PAGE_SIZE + 1);
		result.setTotalPages(totalPages);

		List<Integer> pageNavs = new ArrayList<>();
		for (int i = 1; i <= totalPages; i++) {
			pageNavs.add(i);
		}
		result.setPageNavs(pageNavs);

		//6、构建面包屑导航
		if (param.getAttrs() != null && param.getAttrs().size() > 0) {
			List<ProSearchResp.NavVo> collect = param.getAttrs().stream().map(attr -> {
				//1、分析每一个attrs传过来的参数值
				ProSearchResp.NavVo navVo = new ProSearchResp.NavVo();
				String[] s = attr.split("_");
				navVo.setNavValue(s[1]);
				R r = attrService.getAttrById(Long.parseLong(s[0]));
				if (r.getCode() == 0) {
					AttrResVO attrResVO = (AttrResVO)r.getData();
					navVo.setNavName(attrResVO.getAttrName());
				} else {
					navVo.setNavName(s[0]);
				}

				//2、取消了这个面包屑以后，我们要跳转到哪个地方，将请求的地址url里面的当前置空
				//拿到所有的查询条件，去掉当前
				String encode = null;
				try {
					encode = URLEncoder.encode(attr, "UTF-8");
					encode.replace("+", "%20");  //浏览器对空格的编码和Java不一样，差异化处理
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				String replace = param.get_queryString().replace("&attrs=" + attr, "");
				navVo.setLink("http://search.gulimall.com/list.html?" + replace);

				return navVo;
			}).collect(Collectors.toList());

			result.setNavs(collect);
		}

		return result;
	}
}
