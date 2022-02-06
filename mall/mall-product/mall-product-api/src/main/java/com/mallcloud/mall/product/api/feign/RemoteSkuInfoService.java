package com.mallcloud.mall.product.api.feign;

import com.mallcloud.mall.product.api.feign.factory.RemoteSkuInfoServiceFallbackFactory;
import com.mallcloud.mall.common.core.constant.ServiceNameConstants;
import com.mallcloud.mall.common.core.util.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(contextId = "remoteSkuInfoService", value = ServiceNameConstants.PRODUCT_SERVICE,
		fallbackFactory = RemoteSkuInfoServiceFallbackFactory.class)
public interface RemoteSkuInfoService {

	@ApiOperation(value = "通过skuId查询sku信息", notes = "通过skuId查询sku信息")
	@GetMapping("/skuInfo/{skuId}" )
	R getById(@PathVariable("skuId" ) Long skuId);
}
