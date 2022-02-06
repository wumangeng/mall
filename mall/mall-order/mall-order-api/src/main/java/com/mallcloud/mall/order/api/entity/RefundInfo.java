package com.mallcloud.mall.order.api.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 退款信息
 * </p>
 *
 * @author Painter
 * @since 2021-07-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("oms_refund_info")
@ApiModel(value="RefundInfo对象", description="退款信息")
public class RefundInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id")
    private Long id;

    @ApiModelProperty(value = "退款的订单")
    @TableField("order_return_id")
    private Long orderReturnId;

    @ApiModelProperty(value = "退款金额")
    private BigDecimal refund;

    @ApiModelProperty(value = "退款交易流水号")
    @TableField("refund_sn")
    private String refundSn;

    @ApiModelProperty(value = "退款状态")
    @TableField("refund_status")
    private Boolean refundStatus;

    @ApiModelProperty(value = "退款渠道[1-支付宝，2-微信，3-银联，4-汇款]")
    @TableField("refund_channel")
    private Integer refundChannel;

    @TableField("refund_content")
    private String refundContent;


}
