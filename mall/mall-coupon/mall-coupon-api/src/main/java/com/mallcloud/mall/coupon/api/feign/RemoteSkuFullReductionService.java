package com.mallcloud.mall.coupon.api.feign;

import com.mallcloud.mall.coupon.api.feign.factory.RemoteSkuFullReductionServiceFallbackFactory;
import com.mallcloud.mall.coupon.api.vo.SkuReductionTo;
import com.mallcloud.mall.common.core.constant.ServiceNameConstants;
import com.mallcloud.mall.common.core.util.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Component
@FeignClient(contextId = "remoteSkuFullReductionService", value = ServiceNameConstants.COUPON_SERVICE,
		fallbackFactory = RemoteSkuFullReductionServiceFallbackFactory.class)
public interface RemoteSkuFullReductionService {

	@PostMapping("/skuFullReduction/saveSkuReduction")
	R<Boolean> saveSkuReduction(@RequestBody SkuReductionTo reductionTo);
}
