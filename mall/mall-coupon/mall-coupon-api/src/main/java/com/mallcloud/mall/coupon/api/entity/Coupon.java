package com.mallcloud.mall.coupon.api.entity;

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
 * 优惠券信息
 * </p>
 *
 * @author Painter
 * @since 2021-07-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sms_coupon")
@ApiModel(value="Coupon对象", description="优惠券信息")
public class Coupon implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id")
    private Long id;

    @ApiModelProperty(value = "优惠卷类型[0->全场赠券；1->会员赠券；2->购物赠券；3->注册赠券]")
    @TableField("coupon_type")
    private Boolean couponType;

    @ApiModelProperty(value = "优惠券图片")
    @TableField("coupon_img")
    private String couponImg;

    @ApiModelProperty(value = "优惠卷名字")
    @TableField("coupon_name")
    private String couponName;

    @ApiModelProperty(value = "数量")
    private Integer num;

    @ApiModelProperty(value = "金额")
    private BigDecimal amount;

    @ApiModelProperty(value = "每人限领张数")
    @TableField("per_limit")
    private Integer perLimit;

    @ApiModelProperty(value = "使用门槛")
    @TableField("min_point")
    private BigDecimal minPoint;

    @ApiModelProperty(value = "开始时间")
    @TableField("start_time")
    private LocalDateTime startTime;

    @ApiModelProperty(value = "结束时间")
    @TableField("end_time")
    private LocalDateTime endTime;

    @ApiModelProperty(value = "使用类型[0->全场通用；1->指定分类；2->指定商品]")
    @TableField("use_type")
    private Boolean useType;

    @ApiModelProperty(value = "备注")
    private String note;

    @ApiModelProperty(value = "发行数量")
    @TableField("publish_count")
    private Integer publishCount;

    @ApiModelProperty(value = "已使用数量")
    @TableField("use_count")
    private Integer useCount;

    @ApiModelProperty(value = "领取数量")
    @TableField("receive_count")
    private Integer receiveCount;

    @ApiModelProperty(value = "可以领取的开始日期")
    @TableField("enable_start_time")
    private LocalDateTime enableStartTime;

    @ApiModelProperty(value = "可以领取的结束日期")
    @TableField("enable_end_time")
    private LocalDateTime enableEndTime;

    @ApiModelProperty(value = "优惠码")
    private String code;

    @ApiModelProperty(value = "可以领取的会员等级[0->不限等级，其他-对应等级]")
    @TableField("member_level")
    private Boolean memberLevel;

    @ApiModelProperty(value = "发布状态[0-未发布，1-已发布]")
    private Boolean publish;


}
