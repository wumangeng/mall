package com.mallcloud.mall.resources.api.feign.factory;

import com.mallcloud.mall.resources.api.feign.RemoteElasticSearchService;
import com.mallcloud.mall.resources.api.feign.fallback.RemoteElasticSearchServiceFallbackImpl;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class RemoteElasticSearchServiceFallbackFactory implements FallbackFactory<RemoteElasticSearchService> {
	@Override
	public RemoteElasticSearchService create(Throwable throwable) {
		RemoteElasticSearchServiceFallbackImpl searchServiceFallback = new RemoteElasticSearchServiceFallbackImpl();
		searchServiceFallback.setCause(throwable);
		return searchServiceFallback;
	}
}
