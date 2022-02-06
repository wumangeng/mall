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
 * 订单项信息
 * </p>
 *
 * @author Painter
 * @since 2021-07-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("oms_order_item")
@ApiModel(value="OrderItem对象", description="订单项信息")
public class OrderItem implements Serializable {

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

    @ApiModelProperty(value = "spu_id")
    @TableField("spu_id")
    private Long spuId;

    @ApiModelProperty(value = "spu_name")
    @TableField("spu_name")
    private String spuName;

    @ApiModelProperty(value = "spu_pic")
    @TableField("spu_pic")
    private String spuPic;

    @ApiModelProperty(value = "品牌")
    @TableField("spu_brand")
    private String spuBrand;

    @ApiModelProperty(value = "商品分类id")
    @TableField("category_id")
    private Long categoryId;

    @ApiModelProperty(value = "商品sku编号")
    @TableField("sku_id")
    private Long skuId;

    @ApiModelProperty(value = "商品sku名字")
    @TableField("sku_name")
    private String skuName;

    @ApiModelProperty(value = "商品sku图片")
    @TableField("sku_pic")
    private String skuPic;

    @ApiModelProperty(value = "商品sku价格")
    @TableField("sku_price")
    private BigDecimal skuPrice;

    @ApiModelProperty(value = "商品购买的数量")
    @TableField("sku_quantity")
    private Integer skuQuantity;

    @ApiModelProperty(value = "商品销售属性组合（JSON）")
    @TableField("sku_attrs_vals")
    private String skuAttrsVals;

    @ApiModelProperty(value = "商品促销分解金额")
    @TableField("promotion_amount")
    private BigDecimal promotionAmount;

    @ApiModelProperty(value = "优惠券优惠分解金额")
    @TableField("coupon_amount")
    private BigDecimal couponAmount;

    @ApiModelProperty(value = "积分优惠分解金额")
    @TableField("integration_amount")
    private BigDecimal integrationAmount;

    @ApiModelProperty(value = "该商品经过优惠后的分解金额")
    @TableField("real_amount")
    private BigDecimal realAmount;

    @ApiModelProperty(value = "赠送积分")
    @TableField("gift_integration")
    private Integer giftIntegration;

    @ApiModelProperty(value = "赠送成长值")
    @TableField("gift_growth")
    private Integer giftGrowth;


}
