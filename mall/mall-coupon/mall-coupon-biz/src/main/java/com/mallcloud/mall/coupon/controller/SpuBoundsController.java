package com.mallcloud.mall.coupon.controller;


import com.mallcloud.mall.coupon.api.entity.SpuBounds;
import com.mallcloud.mall.coupon.service.SpuBoundsService;
import com.mallcloud.mall.common.core.util.R;
import com.mallcloud.mall.common.log.annotation.SysLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 商品spu积分设置 前端控制器
 * </p>
 *
 * @author Painter
 * @since 2021-07-10
 */
@RestController
@RequestMapping("/spuBounds")
@Api(tags = "商品spu积分设置控制器")
public class SpuBoundsController {

	@Autowired
	private SpuBoundsService spuBoundsService;

	@ApiOperation(value = "新增spu信息", notes = "新增spu信息")
	@SysLog("新增spu信息" )
	@PostMapping
	public R save(@RequestBody SpuBounds spuBounds) {
		return R.ok(spuBoundsService.save(spuBounds));
	}


}
