package com.mallcloud.mall.order.service.impl;

import com.mallcloud.mall.order.mapper.PaymentInfoMapper;
import com.mallcloud.mall.order.service.PaymentInfoService;
import com.mallcloud.mall.order.api.entity.PaymentInfo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 支付信息表 服务实现类
 * </p>
 *
 * @author Painter
 * @since 2021-07-10
 */
@Service
public class PaymentInfoServiceImpl extends ServiceImpl<PaymentInfoMapper, PaymentInfo> implements PaymentInfoService {

}
