package com.mallcloud.mall.member.api.entity;

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
 * 会员统计信息
 * </p>
 *
 * @author Painter
 * @since 2021-07-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("ums_member_statistics_info")
@ApiModel(value="MemberStatisticsInfo对象", description="会员统计信息")
public class MemberStatisticsInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id")
    private Long id;

    @ApiModelProperty(value = "会员id")
    @TableField("member_id")
    private Long memberId;

    @ApiModelProperty(value = "累计消费金额")
    @TableField("consume_amount")
    private BigDecimal consumeAmount;

    @ApiModelProperty(value = "累计优惠金额")
    @TableField("coupon_amount")
    private BigDecimal couponAmount;

    @ApiModelProperty(value = "订单数量")
    @TableField("order_count")
    private Integer orderCount;

    @ApiModelProperty(value = "优惠券数量")
    @TableField("coupon_count")
    private Integer couponCount;

    @ApiModelProperty(value = "评价数")
    @TableField("comment_count")
    private Integer commentCount;

    @ApiModelProperty(value = "退货数量")
    @TableField("return_order_count")
    private Integer returnOrderCount;

    @ApiModelProperty(value = "登录次数")
    @TableField("login_count")
    private Integer loginCount;

    @ApiModelProperty(value = "关注数量")
    @TableField("attend_count")
    private Integer attendCount;

    @ApiModelProperty(value = "粉丝数量")
    @TableField("fans_count")
    private Integer fansCount;

    @ApiModelProperty(value = "收藏的商品数量")
    @TableField("collect_product_count")
    private Integer collectProductCount;

    @ApiModelProperty(value = "收藏的专题活动数量")
    @TableField("collect_subject_count")
    private Integer collectSubjectCount;

    @ApiModelProperty(value = "收藏的评论数量")
    @TableField("collect_comment_count")
    private Integer collectCommentCount;

    @ApiModelProperty(value = "邀请的朋友数量")
    @TableField("invite_friend_count")
    private Integer inviteFriendCount;


}
