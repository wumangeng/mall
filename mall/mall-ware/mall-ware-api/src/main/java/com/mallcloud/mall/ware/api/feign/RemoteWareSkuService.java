package com.mallcloud.mall.ware.api.feign;

import com.mallcloud.mall.ware.api.feign.factory.RemoteWareSkuServiceFallbackFactory;
import com.mallcloud.mall.common.core.constant.ServiceNameConstants;
import com.mallcloud.mall.common.core.util.R;
import com.mallcloud.mall.ware.api.vo.SkuHasStockVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(contextId = "remoteWareSkuService", value = ServiceNameConstants.WARE_SERVICE,
		fallbackFactory = RemoteWareSkuServiceFallbackFactory.class)
public interface RemoteWareSkuService {

	@ApiOperation(value = "查询sku是否有库存", notes = "查询sku是否有库存")
	@PostMapping(value = "/wareSku/hasStock")
	R<List<SkuHasStockVo>> getSkuHasStock(@RequestBody List<Long> skuIds);

}
