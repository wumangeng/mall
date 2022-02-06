package com.mallcloud.mall.resources.service;

import com.mallcloud.mall.resources.api.entity.SkuEsModel;
import com.mallcloud.mall.resources.api.vo.ProSearchParam;
import com.mallcloud.mall.resources.api.vo.ProSearchResp;

import java.io.IOException;
import java.util.List;

public interface ElasticSearchService {
	/**
	 * 添加上架商品信息到es
	 * */
	boolean addUpSpu(List<SkuEsModel> skuEsModelList) throws IOException;

	/**
	 * 商品检索
	 * */
	ProSearchResp searchPro(ProSearchParam param);
}
