package com.mallcloud.mall.product.api.feign.factory;

import com.mallcloud.mall.product.api.feign.RemoteSkuInfoService;
import com.mallcloud.mall.product.api.feign.fallback.RemoteSkuInfoServiceFallbackImpl;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class RemoteSkuInfoServiceFallbackFactory implements FallbackFactory<RemoteSkuInfoService> {
	@Override
	public RemoteSkuInfoService create(Throwable throwable) {
		RemoteSkuInfoServiceFallbackImpl skuInfoServiceFallback = new RemoteSkuInfoServiceFallbackImpl();
		skuInfoServiceFallback.setCause(throwable);
		return skuInfoServiceFallback;
	}
}
