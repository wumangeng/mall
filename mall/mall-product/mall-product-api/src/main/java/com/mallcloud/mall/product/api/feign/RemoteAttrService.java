package com.mallcloud.mall.product.api.feign;

import com.mallcloud.mall.common.core.constant.ServiceNameConstants;
import com.mallcloud.mall.common.core.util.R;
import com.mallcloud.mall.product.api.feign.factory.RemoteAttrServiceFallbackFactory;
import com.mallcloud.mall.product.api.vo.AttrResVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(contextId = "remoteAttrService", value = ServiceNameConstants.PRODUCT_SERVICE,
		fallbackFactory = RemoteAttrServiceFallbackFactory.class)
public interface RemoteAttrService {

	@ApiOperation(value = "通过id查询商品属性", notes = "通过id查询商品属性")
	@GetMapping("/attr/{attrId}" )
	R<AttrResVO> getAttrById(@PathVariable("attrId" ) Long attrId);
}
