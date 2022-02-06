package com.mallcloud.mall.order.api.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 支付信息表
 * </p>
 *
 * @author Painter
 * @since 2021-07-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("oms_payment_info")
@ApiModel(value="PaymentInfo对象", description="支付信息表")
public class PaymentInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id")
    private Long id;

    @ApiModelProperty(value = "订单号（对外业务号）")
    @TableField("order_sn")
    private String orderSn;

    @ApiModelProperty(value = "订单id")
    @TableField("order_id")
    private Long orderId;

    @ApiModelProperty(value = "支付宝交易流水号")
    @TableField("alipay_trade_no")
    private String alipayTradeNo;

    @ApiModelProperty(value = "支付总金额")
    @TableField("total_amount")
    private BigDecimal totalAmount;

    @ApiModelProperty(value = "交易内容")
    private String subject;

    @ApiModelProperty(value = "支付状态")
    @TableField("payment_status")
    private String paymentStatus;

    @ApiModelProperty(value = "创建时间")
    @TableField("create_time")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "确认时间")
    @TableField("confirm_time")
    private LocalDateTime confirmTime;

    @ApiModelProperty(value = "回调内容")
    @TableField("callback_content")
    private String callbackContent;

    @ApiModelProperty(value = "回调时间")
    @TableField("callback_time")
    private LocalDateTime callbackTime;


}
