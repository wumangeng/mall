package com.mallcloud.mall.resources.api.feign.fallback;

import com.mallcloud.mall.common.core.util.R;
import com.mallcloud.mall.resources.api.entity.SkuEsModel;
import com.mallcloud.mall.resources.api.feign.RemoteElasticSearchService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class RemoteElasticSearchServiceFallbackImpl implements RemoteElasticSearchService {
	@Setter
	private Throwable cause;


	@Override
	public R<Boolean> saveUpSpu(List<SkuEsModel> skuEsModelList) {
		log.error("远程添加上架商品信息到ES 服务调用失败！",skuEsModelList,cause);
		return null;
	}
}
