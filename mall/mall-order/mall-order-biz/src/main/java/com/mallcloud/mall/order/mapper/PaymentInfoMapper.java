package com.mallcloud.mall.order.mapper;

import com.mallcloud.mall.order.api.entity.PaymentInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 支付信息表 Mapper 接口
 * </p>
 *
 * @author Painter
 * @since 2021-07-10
 */
@Mapper
public interface PaymentInfoMapper extends BaseMapper<PaymentInfo> {

}
