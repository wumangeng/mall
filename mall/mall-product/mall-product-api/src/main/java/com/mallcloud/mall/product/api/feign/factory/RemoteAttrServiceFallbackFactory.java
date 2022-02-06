package com.mallcloud.mall.product.api.feign.factory;

import com.mallcloud.mall.product.api.feign.RemoteAttrService;
import com.mallcloud.mall.product.api.feign.fallback.RemoteAttrServiceFallbackImpl;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class RemoteAttrServiceFallbackFactory implements FallbackFactory<RemoteAttrService> {
	@Override
	public RemoteAttrService create(Throwable throwable) {
		RemoteAttrServiceFallbackImpl attrServiceFallback = new RemoteAttrServiceFallbackImpl();
		attrServiceFallback.setCause(throwable);
		return attrServiceFallback;
	}
}
