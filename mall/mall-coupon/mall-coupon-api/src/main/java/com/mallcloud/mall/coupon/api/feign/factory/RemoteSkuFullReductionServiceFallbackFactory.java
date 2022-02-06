package com.mallcloud.mall.coupon.api.feign.factory;

import com.mallcloud.mall.coupon.api.feign.RemoteSkuFullReductionService;
import com.mallcloud.mall.coupon.api.feign.fallback.RemoteSkuFullReductionServiceFallbackImpl;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class RemoteSkuFullReductionServiceFallbackFactory implements FallbackFactory<RemoteSkuFullReductionService> {
	@Override
	public RemoteSkuFullReductionService create(Throwable throwable) {
		RemoteSkuFullReductionServiceFallbackImpl skuFullReductionServiceFallback = new RemoteSkuFullReductionServiceFallbackImpl();
		skuFullReductionServiceFallback.setCause(throwable);
		return skuFullReductionServiceFallback;
	}
}
