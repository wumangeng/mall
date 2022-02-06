package com.mallcloud.mall.order.service.impl;

import com.mallcloud.mall.order.service.OrderReturnReasonService;
import com.mallcloud.mall.order.api.entity.OrderReturnReason;
import com.mallcloud.mall.order.mapper.OrderReturnReasonMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 退货原因 服务实现类
 * </p>
 *
 * @author Painter
 * @since 2021-07-10
 */
@Service
public class OrderReturnReasonServiceImpl extends ServiceImpl<OrderReturnReasonMapper, OrderReturnReason> implements OrderReturnReasonService {

}
