package com.mallcloud.mall.coupon.api.entity;

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
 * 商品阶梯价格
 * </p>
 *
 * @author Painter
 * @since 2021-07-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sms_sku_ladder")
@ApiModel(value="SkuLadder对象", description="商品阶梯价格")
public class SkuLadder implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id")
    private Long id;

    @ApiModelProperty(value = "spu_id")
    @TableField("sku_id")
    private Long skuId;

    @ApiModelProperty(value = "满几件")
    @TableField("full_count")
    private Integer fullCount;

    @ApiModelProperty(value = "打几折")
    private BigDecimal discount;

    @ApiModelProperty(value = "折后价")
    private BigDecimal price;

    @ApiModelProperty(value = "是否叠加其他优惠[0-不可叠加，1-可叠加]")
    @TableField("add_other")
    private Integer addOther;


}
