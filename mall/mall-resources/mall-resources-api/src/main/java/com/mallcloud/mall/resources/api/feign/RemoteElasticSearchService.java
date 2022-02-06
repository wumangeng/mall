package com.mallcloud.mall.resources.api.feign;

import com.mallcloud.mall.common.core.constant.ServiceNameConstants;
import com.mallcloud.mall.common.core.util.R;
import com.mallcloud.mall.resources.api.entity.SkuEsModel;
import com.mallcloud.mall.resources.api.feign.factory.RemoteElasticSearchServiceFallbackFactory;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;


@FeignClient(contextId = "remoteElasticSearchService", value = ServiceNameConstants.RESOURCES_SERVICE,
		fallbackFactory = RemoteElasticSearchServiceFallbackFactory.class)
public interface RemoteElasticSearchService {

	@ApiOperation(value = "ES添加商品上架信息", notes = "ES添加商品上架信息")
	@PostMapping("/elasticSearch/addUpSpu")
	R<Boolean> saveUpSpu(@RequestBody List<SkuEsModel> skuEsModelList);
}
