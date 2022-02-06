package com.mallcloud.mall.product.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mallcloud.mall.common.core.constant.enums.ProductTypeEnum;
import com.mallcloud.mall.common.core.util.R;
import com.mallcloud.mall.coupon.api.entity.SpuBounds;
import com.mallcloud.mall.coupon.api.feign.RemoteSkuFullReductionService;
import com.mallcloud.mall.coupon.api.feign.RemoteSpuBoundsService;
import com.mallcloud.mall.coupon.api.vo.SkuReductionTo;
import com.mallcloud.mall.product.api.entity.*;
import com.mallcloud.mall.resources.api.entity.SkuEsModel;
import com.mallcloud.mall.product.mapper.SpuInfoMapper;
import com.mallcloud.mall.product.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mallcloud.mall.product.vo.*;
import com.mallcloud.mall.resources.api.feign.RemoteElasticSearchService;
import com.mallcloud.mall.ware.api.feign.RemoteWareSkuService;
import com.mallcloud.mall.ware.api.vo.SkuHasStockVo;
import com.mallcloud.mall.common.mybatis.util.PageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * spu信息 服务实现类
 * </p>
 *
 * @author Painter
 * @since 2021-07-10
 */
@Service
@RequiredArgsConstructor
public class SpuInfoServiceImpl extends ServiceImpl<SpuInfoMapper, SpuInfo> implements SpuInfoService {

	private final SpuInfoDescService spuInfoDescService;
	private final SpuImagesService imagesService;
	private final AttrService attrService;
	private final ProductAttrValueService productAttrValueService;
	private final SkuInfoService skuInfoService;
	private final SkuImagesService skuImagesService;
	private final SkuSaleAttrValueService skuSaleAttrValueService;
	private final BrandService brandService;
	private final CategoryService categoryService;

	private final RemoteSpuBoundsService spuBoundsService;
	private final RemoteSkuFullReductionService saveSkuReduction;
	private final RemoteWareSkuService wareSkuService;
	private final RemoteElasticSearchService searchFeignService;


	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean saveSpuInfo(SpuSaveVo spuSaveVo) {
		//1、保存spu基本信息 pms_spu_info
		SpuInfo infoEntity = new SpuInfo();
		BeanUtils.copyProperties(spuSaveVo, infoEntity);
		infoEntity.setCreateTime(LocalDateTime.now()).setUpdateTime(LocalDateTime.now());
		baseMapper.insert(infoEntity);

		//2、保存Spu的描述图片 pms_spu_info_desc
		List<String> decript = spuSaveVo.getDecript();
		SpuInfoDesc descEntity = new SpuInfoDesc();
		descEntity.setSpuId(infoEntity.getId());
		descEntity.setDecript(String.join(",", decript)); //使用,间隔拼接字符串
		spuInfoDescService.save(descEntity);

		//3、保存spu的图片集 pms_spu_images
		List<String> images = spuSaveVo.getImages();
		imagesService.saveImages(infoEntity.getId(), images);

		//4、保存spu的规格参数;pms_product_attr_value
		List<BaseAttrs> baseAttrs = spuSaveVo.getBaseAttrs();
		List<ProductAttrValue> collect = baseAttrs.stream().map(attr -> {
			ProductAttrValue valueEntity = new ProductAttrValue();
			valueEntity.setAttrId(attr.getAttrId());
			Attr attrServiceById = attrService.getById(attr.getAttrId());
			if (attrServiceById!=null) valueEntity.setAttrName(attrServiceById.getAttrName());
			valueEntity.setAttrValue(attr.getAttrValues());
			valueEntity.setQuickShow(attr.getShowDesc());
			valueEntity.setSpuId(infoEntity.getId());

			return valueEntity;
		}).collect(Collectors.toList());
		productAttrValueService.saveBatch(collect);

		//5、保存spu的积分信息；gulimall_sms->sms_spu_bounds
		Bounds bounds = spuSaveVo.getBounds();
		SpuBounds spuBoundTo = new SpuBounds();
		BeanUtils.copyProperties(bounds, spuBoundTo);
		spuBoundTo.setSpuId(infoEntity.getId());

		spuBoundsService.saveSpuBounds(spuBoundTo);


		//6、保存当前spu对应的所有sku信息；
		List<Skus> skus = spuSaveVo.getSkus();
		if (skus != null && skus.size() > 0) {
			skus.forEach(item -> {
				//找到默认选中的图片
				String defaultImg = "";
				for (Images image : item.getImages()) {
					if (image.getDefaultImg() == 1) {
						defaultImg = image.getImgUrl();
					}
				}
				//    private String skuName;
				//    private BigDecimal price;
				//    private String skuTitle;
				//    private String skuSubtitle;
				SkuInfo skuInfoEntity = new SkuInfo();
				BeanUtils.copyProperties(item, skuInfoEntity);
				skuInfoEntity.setBrandId(infoEntity.getBrandId());
				skuInfoEntity.setCatalogId(infoEntity.getCatalogId());
				skuInfoEntity.setSaleCount(0L);
				skuInfoEntity.setSpuId(infoEntity.getId());
				skuInfoEntity.setSkuDefaultImg(defaultImg);
				//6.1）、sku的基本信息；pms_sku_info
				skuInfoService.save(skuInfoEntity);

				Long skuId = skuInfoEntity.getSkuId();
				// 保存sku图片
				List<SkuImages> imagesEntities = item.getImages().stream().map(img -> {
					SkuImages skuImagesEntity = new SkuImages();
					skuImagesEntity.setSkuId(skuId);
					skuImagesEntity.setImgUrl(img.getImgUrl());
					skuImagesEntity.setDefaultImg(img.getDefaultImg());
					return skuImagesEntity;
				}).filter(entity -> {
					// 过滤 没有图片路径的无需保存
					//返回true就是需要，false就是剔除
					return !StringUtils.isEmpty(entity.getImgUrl());
				}).collect(Collectors.toList());
				//6.2）、sku的图片信息；pms_sku_image
				skuImagesService.saveBatch(imagesEntities);

				List<AttrTemp> attr = item.getAttr();
				List<SkuSaleAttrValue> skuSaleAttrValueEntities = attr.stream().map(a -> {
					SkuSaleAttrValue attrValueEntity = new SkuSaleAttrValue();
					BeanUtils.copyProperties(a, attrValueEntity);
					attrValueEntity.setSkuId(skuId);

					return attrValueEntity;
				}).collect(Collectors.toList());
				//5.3）、sku的销售属性信息：pms_sku_sale_attr_value
				skuSaleAttrValueService.saveBatch(skuSaleAttrValueEntities);

				// //5.4）、sku的优惠、满减等信息；gulimall_sms->sms_sku_ladder\sms_sku_full_reduction\sms_member_price
				SkuReductionTo skuReductionTo = new SkuReductionTo();
				BeanUtils.copyProperties(item, skuReductionTo);
				skuReductionTo.setSkuId(skuId);
				if (skuReductionTo.getFullCount() > 0 || skuReductionTo.getFullPrice().compareTo(new BigDecimal("0")) == 1) {
					saveSkuReduction.saveSkuReduction(skuReductionTo);
				}
			});
		}
		return true;
	}

	@Override
	public PageUtils queryPageByCondition(SpuInfoSearchVO spuInfoSearchVO) {
		QueryWrapper<SpuInfo> wrapper = new QueryWrapper<>();

		String searchParam = spuInfoSearchVO.getSearchParam();
		if (StrUtil.isNotEmpty(searchParam)) {
			wrapper.like("spu_name", searchParam);
		}
		// status=1 and (id=1 or spu_name like xxx)
		Integer status = spuInfoSearchVO.getPublishStatus();
		if (status!=null) {
			wrapper.eq("publish_status", status);
		}

		Long brandId = spuInfoSearchVO.getBrandId();
		if (brandId != null && brandId != 0L) {
			wrapper.eq("brand_id", brandId);
		}

		Long catelogId = spuInfoSearchVO.getCatalogId();
		if (catelogId != null && catelogId != 0L) {
			wrapper.eq("catalog_id", catelogId);
		}

		Page<SpuInfo> spuInfoPage = baseMapper.selectPage(new Page<>(spuInfoSearchVO.getCurrentPage(), spuInfoSearchVO.getPageSize()), wrapper);

		return new PageUtils(spuInfoPage);
	}

	/**
	 * 商品上架 将商品信息封装成 SkuEsModel对象提交到es
	 * */
	@Override
	public boolean upSpu(Long spuId) {
		// 1、查出当前spuId对应的所有sku信息,品牌的名字
		List<SkuInfo> skuInfoEntities = skuInfoService.getSkusBySpuId(spuId);

		//  4、查出当前sku的所有可以被用来检索的规格属性
		List<ProductAttrValue> baseAttrs = productAttrValueService.baseAttrListforspu(spuId);

		List<Long> attrIds = baseAttrs.stream().map(ProductAttrValue::getAttrId).collect(Collectors.toList());

		List<Long> searchAttrIds = attrService.selectSearchAttrs(attrIds);
		//转换为Set集合
		Set<Long> idSet = new HashSet<>(searchAttrIds);

		List<SkuEsModel.Attrs> attrsList = baseAttrs.stream()
				.filter(item -> idSet.contains(item.getAttrId()))
				.map(item -> {
					SkuEsModel.Attrs attrs = new SkuEsModel.Attrs();
					BeanUtils.copyProperties(item, attrs);
					return attrs;
				}).collect(Collectors.toList());

		List<Long> skuIdList = skuInfoEntities.stream().map(SkuInfo::getSkuId).collect(Collectors.toList());
		// 1、发送远程调用，库存系统查询是否有库存
		Map<Long, Boolean> stockMap = null;
		try {
			R<List<SkuHasStockVo>> result = wareSkuService.getSkuHasStock(skuIdList);
			if (result!=null && result.getData()!=null){
				List<SkuHasStockVo> skuHasStockVoList = result.getData();
				stockMap = skuHasStockVoList.stream().collect(Collectors.toMap(SkuHasStockVo::getSkuId, SkuHasStockVo::getHasStock));
			}
		} catch (Exception e) {
			log.error("库存服务查询异常：原因{}", e);
		}

		//2、封装每个sku的信息
		Map<Long, Boolean> finalStockMap = stockMap;
		List<SkuEsModel> collect = skuInfoEntities.stream().map(sku -> {
			//组装需要的数据
			SkuEsModel esModel = new SkuEsModel();
			esModel.setSkuPrice(sku.getPrice());
			esModel.setSkuImg(sku.getSkuDefaultImg());

			// 设置库存信息(有库存true)
			if (finalStockMap == null) {
				esModel.setHasStock(true);
			} else {
				esModel.setHasStock(finalStockMap.get(sku.getSkuId()));
			}

			//  2、热度评分。0
			esModel.setHotScore(0L);

			//  3、查询品牌和分类的名字信息
			Brand brandEntity = brandService.getById(sku.getBrandId());
			esModel.setBrandName(brandEntity.getName());
			esModel.setBrandId(brandEntity.getBrandId());
			esModel.setBrandImg(brandEntity.getLogo());

			Category categoryEntity = categoryService.getById(sku.getCatalogId());
			esModel.setCatalogId(categoryEntity.getCatId());
			esModel.setCatalogName(categoryEntity.getName());

			// 设置检索属性
			esModel.setAttrs(attrsList);

			BeanUtils.copyProperties(sku, esModel);

			return esModel;
		}).collect(Collectors.toList());

		// 5、将数据发给es进行保存：mall-resources-biz
		R<Boolean> upSpuRes = searchFeignService.saveUpSpu(collect);

		// 远程调用结果
		if (upSpuRes.getData()) {
			// 6、修改当前spu的状态
			this.updaSpuStatus(spuId, ProductTypeEnum.ProductStatusEnum.SPU_UP.getCode());
		} else {
			// 远程调用失败
			// TODO 重复调用？接口幂等性:重试机制
		}

		return true;
	}

	@Override
	public boolean updaSpuStatus(Long spuId, int status) {
		SpuInfo spuInfo = new SpuInfo();
		spuInfo.setId(spuId).setPublishStatus(status).setUpdateTime(LocalDateTime.now());
		return baseMapper.updateById(spuInfo)>0;
	}

}
