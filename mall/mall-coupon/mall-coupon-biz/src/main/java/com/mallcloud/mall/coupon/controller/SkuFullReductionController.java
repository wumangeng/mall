package com.mallcloud.mall.coupon.controller;


import com.mallcloud.mall.coupon.api.vo.SkuReductionTo;
import com.mallcloud.mall.coupon.service.SkuFullReductionService;
import com.mallcloud.mall.common.core.util.R;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 商品满减信息 前端控制器
 * </p>
 *
 * @author Painter
 * @since 2021-07-10
 */
@RestController
@RequestMapping("/skuFullReduction")
@Api(tags = "商品满减信息控制器")
public class SkuFullReductionController {

	@Autowired
	private SkuFullReductionService skuFullReductionService;


	@PostMapping("/saveSkuReduction")
	public R saveSkuReduction(@RequestBody SkuReductionTo reductionTo){
		return R.ok(skuFullReductionService.saveSkuReduction(reductionTo));
	}

}
