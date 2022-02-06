package com.mallcloud.mall.coupon.api.feign.fallback;

import com.mallcloud.mall.coupon.api.entity.SpuBounds;
import com.mallcloud.mall.coupon.api.feign.RemoteSpuBoundsService;
import com.mallcloud.mall.common.core.util.R;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RemoteSpuBoundsServiceFallbackImpl implements RemoteSpuBoundsService {
	@Setter
	private Throwable cause;


	@Override
	public R<Boolean> saveSpuBounds(SpuBounds spuBounds) {
		log.error("feign 添加商品spu积分设置信息失败", cause);
		return null;
	}
}
