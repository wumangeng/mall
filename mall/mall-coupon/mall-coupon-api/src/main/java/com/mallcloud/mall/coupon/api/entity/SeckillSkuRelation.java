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
 * 秒杀活动商品关联
 * </p>
 *
 * @author Painter
 * @since 2021-07-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sms_seckill_sku_relation")
@ApiModel(value="SeckillSkuRelation对象", description="秒杀活动商品关联")
public class SeckillSkuRelation implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id")
    private Long id;

    @ApiModelProperty(value = "活动id")
    @TableField("promotion_id")
    private Long promotionId;

    @ApiModelProperty(value = "活动场次id")
    @TableField("promotion_session_id")
    private Long promotionSessionId;

    @ApiModelProperty(value = "商品id")
    @TableField("sku_id")
    private Long skuId;

    @ApiModelProperty(value = "秒杀价格")
    @TableField("seckill_price")
    private BigDecimal seckillPrice;

    @ApiModelProperty(value = "秒杀总量")
    @TableField("seckill_count")
    private BigDecimal seckillCount;

    @ApiModelProperty(value = "每人限购数量")
    @TableField("seckill_limit")
    private BigDecimal seckillLimit;

    @ApiModelProperty(value = "排序")
    @TableField("seckill_sort")
    private Integer seckillSort;


}
