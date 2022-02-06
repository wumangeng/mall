package com.mallcloud.mall.coupon.api.feign;

import com.mallcloud.mall.coupon.api.entity.SpuBounds;
import com.mallcloud.mall.coupon.api.feign.factory.RemoteSpuBoundsServiceFallbackFactory;
import com.mallcloud.mall.common.core.constant.ServiceNameConstants;
import com.mallcloud.mall.common.core.util.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Component
@FeignClient(contextId = "remoteSpuBoundsService", value = ServiceNameConstants.COUPON_SERVICE,
		fallbackFactory = RemoteSpuBoundsServiceFallbackFactory.class)
public interface RemoteSpuBoundsService {

	@ApiOperation(value = "新增spu信息", notes = "新增spu信息")
	@PostMapping("/spuBounds")
	R<Boolean> saveSpuBounds(@RequestBody SpuBounds spuBounds);
}
