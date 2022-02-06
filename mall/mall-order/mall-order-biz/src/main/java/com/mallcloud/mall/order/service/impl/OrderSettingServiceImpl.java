package com.mallcloud.mall.order.service.impl;

import com.mallcloud.mall.order.service.OrderSettingService;
import com.mallcloud.mall.order.api.entity.OrderSetting;
import com.mallcloud.mall.order.mapper.OrderSettingMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单配置信息 服务实现类
 * </p>
 *
 * @author Painter
 * @since 2021-07-10
 */
@Service
public class OrderSettingServiceImpl extends ServiceImpl<OrderSettingMapper, OrderSetting> implements OrderSettingService {

}
