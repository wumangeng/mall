package com.mallcloud.mall.coupon.api.entity;

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
 * 优惠券领取历史记录
 * </p>
 *
 * @author Painter
 * @since 2021-07-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sms_coupon_history")
@ApiModel(value="CouponHistory对象", description="优惠券领取历史记录")
public class CouponHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id")
    private Long id;

    @ApiModelProperty(value = "优惠券id")
    @TableField("coupon_id")
    private Long couponId;

    @ApiModelProperty(value = "会员id")
    @TableField("member_id")
    private Long memberId;

    @ApiModelProperty(value = "会员名字")
    @TableField("member_nick_name")
    private String memberNickName;

    @ApiModelProperty(value = "获取方式[0->后台赠送；1->主动领取]")
    @TableField("get_type")
    private Boolean getType;

    @ApiModelProperty(value = "创建时间")
    @TableField("create_time")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "使用状态[0->未使用；1->已使用；2->已过期]")
    @TableField("use_type")
    private Boolean useType;

    @ApiModelProperty(value = "使用时间")
    @TableField("use_time")
    private LocalDateTime useTime;

    @ApiModelProperty(value = "订单id")
    @TableField("order_id")
    private Long orderId;

    @ApiModelProperty(value = "订单号")
    @TableField("order_sn")
    private Long orderSn;


}
