package com.mallcloud.mall.order.service.impl;

import com.mallcloud.mall.order.service.OrderService;
import com.mallcloud.mall.order.api.entity.Order;
import com.mallcloud.mall.order.mapper.OrderMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author Painter
 * @since 2021-07-10
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

}
