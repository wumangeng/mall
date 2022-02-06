package com.mallcloud.mall.ware.api.feign.factory;

import com.mallcloud.mall.ware.api.feign.RemoteWareSkuService;
import com.mallcloud.mall.ware.api.feign.fallback.RemoteWareSkuServiceFallbackImpl;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class RemoteWareSkuServiceFallbackFactory implements FallbackFactory<RemoteWareSkuService> {
	@Override
	public RemoteWareSkuService create(Throwable throwable) {
		RemoteWareSkuServiceFallbackImpl skuInfoServiceFallback = new RemoteWareSkuServiceFallbackImpl();
		skuInfoServiceFallback.setCause(throwable);
		return skuInfoServiceFallback;
	}
}
