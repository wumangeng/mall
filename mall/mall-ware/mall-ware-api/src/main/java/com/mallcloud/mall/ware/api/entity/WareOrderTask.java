package com.mallcloud.mall.ware.api.entity;

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
 * 库存工作单
 * </p>
 *
 * @author Painter
 * @since 2021-07-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wms_ware_order_task")
@ApiModel(value="WareOrderTask对象", description="库存工作单")
public class WareOrderTask implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id")
    private Long id;

    @ApiModelProperty(value = "order_id")
    @TableField("order_id")
    private Long orderId;

    @ApiModelProperty(value = "order_sn")
    @TableField("order_sn")
    private String orderSn;

    @ApiModelProperty(value = "收货人")
    private String consignee;

    @ApiModelProperty(value = "收货人电话")
    @TableField("consignee_tel")
    private String consigneeTel;

    @ApiModelProperty(value = "配送地址")
    @TableField("delivery_address")
    private String deliveryAddress;

    @ApiModelProperty(value = "订单备注")
    @TableField("order_comment")
    private String orderComment;

    @ApiModelProperty(value = "付款方式【 1:在线付款 2:货到付款】")
    @TableField("payment_way")
    private Boolean paymentWay;

    @ApiModelProperty(value = "任务状态")
    @TableField("task_status")
    private Integer taskStatus;

    @ApiModelProperty(value = "订单描述")
    @TableField("order_body")
    private String orderBody;

    @ApiModelProperty(value = "物流单号")
    @TableField("tracking_no")
    private String trackingNo;

    @ApiModelProperty(value = "create_time")
    @TableField("create_time")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "仓库id")
    @TableField("ware_id")
    private Long wareId;

    @ApiModelProperty(value = "工作单备注")
    @TableField("task_comment")
    private String taskComment;


}
