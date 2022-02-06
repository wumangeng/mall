package com.mallcloud.mall.product.api.feign.fallback;

import com.mallcloud.mall.product.api.feign.RemoteSkuInfoService;
import com.mallcloud.mall.common.core.util.R;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RemoteSkuInfoServiceFallbackImpl implements RemoteSkuInfoService {
	@Setter
	private Throwable cause;


	@Override
	public R getById(Long skuId) {
		log.error("远程获取 skuId={} 信息  服务调用失败！",skuId,cause);
		return null;
	}
}
