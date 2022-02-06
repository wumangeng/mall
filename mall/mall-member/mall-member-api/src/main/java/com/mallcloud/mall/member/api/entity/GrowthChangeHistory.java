package com.mallcloud.mall.member.api.entity;

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
 * 成长值变化历史记录
 * </p>
 *
 * @author Painter
 * @since 2021-07-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("ums_growth_change_history")
@ApiModel(value="GrowthChangeHistory对象", description="成长值变化历史记录")
public class GrowthChangeHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id")
    private Long id;

    @ApiModelProperty(value = "member_id")
    @TableField("member_id")
    private Long memberId;

    @ApiModelProperty(value = "create_time")
    @TableField("create_time")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "改变的值（正负计数）")
    @TableField("change_count")
    private Integer changeCount;

    @ApiModelProperty(value = "备注")
    private String note;

    @ApiModelProperty(value = "积分来源[0-购物，1-管理员修改]")
    @TableField("source_type")
    private Integer sourceType;


}
