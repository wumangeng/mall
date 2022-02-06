package com.mallcloud.mall.coupon.api.feign.fallback;

import com.mallcloud.mall.coupon.api.feign.RemoteSkuFullReductionService;
import com.mallcloud.mall.coupon.api.vo.SkuReductionTo;
import com.mallcloud.mall.common.core.util.R;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RemoteSkuFullReductionServiceFallbackImpl implements RemoteSkuFullReductionService {
	@Setter
	private Throwable cause;


	@Override
	public R<Boolean> saveSkuReduction(SkuReductionTo reductionTo) {
		log.error("feign 添加商品满减信息失败", cause);
		return null;
	}
}
