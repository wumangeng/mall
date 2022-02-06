package com.mallcloud.mall.coupon.service;

import com.mallcloud.mall.coupon.api.entity.SkuFullReduction;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mallcloud.mall.coupon.api.vo.SkuReductionTo;

/**
 * <p>
 * 商品满减信息 服务类
 * </p>
 *
 * @author Painter
 * @since 2021-07-10
 */
public interface SkuFullReductionService extends IService<SkuFullReduction> {

    Boolean saveSkuReduction(SkuReductionTo reductionTo);
}
