package com.mallcloud.mall.order.service.impl;

import com.mallcloud.mall.order.mapper.OrderOperateHistoryMapper;
import com.mallcloud.mall.order.service.OrderOperateHistoryService;
import com.mallcloud.mall.order.api.entity.OrderOperateHistory;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单操作历史记录 服务实现类
 * </p>
 *
 * @author Painter
 * @since 2021-07-10
 */
@Service
public class OrderOperateHistoryServiceImpl extends ServiceImpl<OrderOperateHistoryMapper, OrderOperateHistory> implements OrderOperateHistoryService {

}
