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
 * 订单退货申请
 * </p>
 *
 * @author Painter
 * @since 2021-07-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("oms_order_return_apply")
@ApiModel(value="OrderReturnApply对象", description="订单退货申请")
public class OrderReturnApply implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id")
    private Long id;

    @ApiModelProperty(value = "order_id")
    @TableField("order_id")
    private Long orderId;

    @ApiModelProperty(value = "退货商品id")
    @TableField("sku_id")
    private Long skuId;

    @ApiModelProperty(value = "订单编号")
    @TableField("order_sn")
    private String orderSn;

    @ApiModelProperty(value = "申请时间")
    @TableField("create_time")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "会员用户名")
    @TableField("member_username")
    private String memberUsername;

    @ApiModelProperty(value = "退款金额")
    @TableField("return_amount")
    private BigDecimal returnAmount;

    @ApiModelProperty(value = "退货人姓名")
    @TableField("return_name")
    private String returnName;

    @ApiModelProperty(value = "退货人电话")
    @TableField("return_phone")
    private String returnPhone;

    @ApiModelProperty(value = "申请状态[0->待处理；1->退货中；2->已完成；3->已拒绝]")
    private Boolean status;

    @ApiModelProperty(value = "处理时间")
    @TableField("handle_time")
    private LocalDateTime handleTime;

    @ApiModelProperty(value = "商品图片")
    @TableField("sku_img")
    private String skuImg;

    @ApiModelProperty(value = "商品名称")
    @TableField("sku_name")
    private String skuName;

    @ApiModelProperty(value = "商品品牌")
    @TableField("sku_brand")
    private String skuBrand;

    @ApiModelProperty(value = "商品销售属性(JSON)")
    @TableField("sku_attrs_vals")
    private String skuAttrsVals;

    @ApiModelProperty(value = "退货数量")
    @TableField("sku_count")
    private Integer skuCount;

    @ApiModelProperty(value = "商品单价")
    @TableField("sku_price")
    private BigDecimal skuPrice;

    @ApiModelProperty(value = "商品实际支付单价")
    @TableField("sku_real_price")
    private BigDecimal skuRealPrice;

    @ApiModelProperty(value = "原因")
    private String reason;

    @ApiModelProperty(value = "描述")
    private String description述;

    @ApiModelProperty(value = "凭证图片，以逗号隔开")
    @TableField("desc_pics")
    private String descPics;

    @ApiModelProperty(value = "处理备注")
    @TableField("handle_note")
    private String handleNote;

    @ApiModelProperty(value = "处理人员")
    @TableField("handle_man")
    private String handleMan;

    @ApiModelProperty(value = "收货人")
    @TableField("receive_man")
    private String receiveMan;

    @ApiModelProperty(value = "收货时间")
    @TableField("receive_time")
    private LocalDateTime receiveTime;

    @ApiModelProperty(value = "收货备注")
    @TableField("receive_note")
    private String receiveNote;

    @ApiModelProperty(value = "收货电话")
    @TableField("receive_phone")
    private String receivePhone;

    @ApiModelProperty(value = "公司收货地址")
    @TableField("company_address")
    private String companyAddress;


}
