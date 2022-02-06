package com.mallcloud.mall.order.mapper;

import com.mallcloud.mall.order.api.entity.OrderSetting;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 订单配置信息 Mapper 接口
 * </p>
 *
 * @author Painter
 * @since 2021-07-10
 */
@Mapper
public interface OrderSettingMapper extends BaseMapper<OrderSetting> {

}
