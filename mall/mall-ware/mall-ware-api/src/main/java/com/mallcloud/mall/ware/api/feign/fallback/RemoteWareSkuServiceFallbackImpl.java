package com.mallcloud.mall.ware.api.feign.fallback;

import com.mallcloud.mall.ware.api.feign.RemoteWareSkuService;
import com.mallcloud.mall.common.core.util.R;
import com.mallcloud.mall.ware.api.vo.SkuHasStockVo;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class RemoteWareSkuServiceFallbackImpl implements RemoteWareSkuService {
	@Setter
	private Throwable cause;

	@Override
	public R<List<SkuHasStockVo>> getSkuHasStock(List<Long> skuIds) {
		log.error("远程查询 skuIds={} 是否有库存，服务调用失败！",skuIds,cause);
		return null;
	}

}
