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
 * 商品满减信息
 * </p>
 *
 * @author Painter
 * @since 2021-07-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sms_sku_full_reduction")
@ApiModel(value="SkuFullReduction对象", description="商品满减信息")
public class SkuFullReduction implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id")
    private Long id;

    @ApiModelProperty(value = "spu_id")
    @TableField("sku_id")
    private Long skuId;

    @ApiModelProperty(value = "满多少")
    @TableField("full_price")
    private BigDecimal fullPrice;

    @ApiModelProperty(value = "减多少")
    @TableField("reduce_price")
    private BigDecimal reducePrice;

    @ApiModelProperty(value = "是否参与其他优惠")
    @TableField("add_other")
    private Boolean addOther;


}
