package com.mallcloud.mall.coupon.api.feign.factory;

import com.mallcloud.mall.coupon.api.feign.RemoteSpuBoundsService;
import com.mallcloud.mall.coupon.api.feign.fallback.RemoteSpuBoundsServiceFallbackImpl;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class RemoteSpuBoundsServiceFallbackFactory implements FallbackFactory<RemoteSpuBoundsService> {
	@Override
	public RemoteSpuBoundsService create(Throwable throwable) {
		RemoteSpuBoundsServiceFallbackImpl remoteSpuBoundsServiceFallback = new RemoteSpuBoundsServiceFallbackImpl();
		remoteSpuBoundsServiceFallback.setCause(throwable);
		return remoteSpuBoundsServiceFallback;
	}
}
