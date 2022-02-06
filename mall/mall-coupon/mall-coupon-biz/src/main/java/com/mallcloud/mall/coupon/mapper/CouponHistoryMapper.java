package com.mallcloud.mall.coupon.mapper;

import com.mallcloud.mall.coupon.api.entity.CouponHistory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 优惠券领取历史记录 Mapper 接口
 * </p>
 *
 * @author Painter
 * @since 2021-07-10
 */
@Mapper
public interface CouponHistoryMapper extends BaseMapper<CouponHistory> {

}
