package com.mallcloud.mall.member.api.entity;

import com.baomidou.mybatisplus.annotation.TableName;
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
 * 会员收藏的商品
 * </p>
 *
 * @author Painter
 * @since 2021-07-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("ums_member_collect_spu")
@ApiModel(value="MemberCollectSpu对象", description="会员收藏的商品")
public class MemberCollectSpu implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "会员id")
    @TableField("member_id")
    private Long memberId;

    @ApiModelProperty(value = "spu_id")
    @TableField("spu_id")
    private Long spuId;

    @ApiModelProperty(value = "spu_name")
    @TableField("spu_name")
    private String spuName;

    @ApiModelProperty(value = "spu_img")
    @TableField("spu_img")
    private String spuImg;

    @ApiModelProperty(value = "create_time")
    @TableField("create_time")
    private LocalDateTime createTime;


}
