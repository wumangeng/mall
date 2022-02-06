package com.mallcloud.mall.product.api.feign.fallback;

import com.mallcloud.mall.common.core.util.R;
import com.mallcloud.mall.product.api.feign.RemoteAttrService;
import com.mallcloud.mall.product.api.vo.AttrResVO;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RemoteAttrServiceFallbackImpl implements RemoteAttrService {
	@Setter
	private Throwable cause;

	@Override
	public R<AttrResVO> getAttrById(Long attrId) {
		log.error("查询商品属性attrId={} 服务调用失败！",attrId,cause);
		return null;
	}
}
