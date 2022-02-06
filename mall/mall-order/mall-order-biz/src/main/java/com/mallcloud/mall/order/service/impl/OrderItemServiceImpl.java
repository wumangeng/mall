package com.mallcloud.mall.order.service.impl;

import com.mallcloud.mall.order.mapper.OrderItemMapper;
import com.mallcloud.mall.order.service.OrderItemService;
import com.mallcloud.mall.order.api.entity.OrderItem;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单项信息 服务实现类
 * </p>
 *
 * @author Painter
 * @since 2021-07-10
 */
@Service
public class OrderItemServiceImpl extends ServiceImpl<OrderItemMapper, OrderItem> implements OrderItemService {

}
