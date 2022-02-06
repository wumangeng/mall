package com.mallcloud.mall.coupon.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mallcloud.mall.coupon.api.entity.Coupon;
import com.mallcloud.mall.coupon.service.CouponService;
import com.mallcloud.mall.common.core.util.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 优惠券信息 前端控制器
 * </p>
 *
 * @author Painter
 * @since 2021-07-10
 */
@RestController
@RequestMapping("/coupon")
@Api(tags = "优惠券信息控制器")
public class CouponController {
	@Autowired
	private CouponService couponService;

	@PostMapping("/addCoupon")
	@ApiOperation(value = "添加优惠券")
	public R addCoupon(@RequestBody Coupon coupon){
		boolean save = couponService.save(coupon);
		return R.ok(save);
	}

	@GetMapping("/getCouponAll/{page}/{limit}")
	@ApiOperation(value = "查询所有优惠券")
	public R getCouponAll(@PathVariable Long page,@PathVariable Long limit){
		Page<Coupon> couponPage = couponService.page(new Page<>(page,limit));
		return R.ok(couponPage);
	}

}
