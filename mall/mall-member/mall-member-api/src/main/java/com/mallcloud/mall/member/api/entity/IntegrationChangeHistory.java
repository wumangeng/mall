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
 * 积分变化历史记录
 * </p>
 *
 * @author Painter
 * @since 2021-07-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("ums_integration_change_history")
@ApiModel(value="IntegrationChangeHistory对象", description="积分变化历史记录")
public class IntegrationChangeHistory implements Serializable {

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

    @ApiModelProperty(value = "变化的值")
    @TableField("change_count")
    private Integer changeCount;

    @ApiModelProperty(value = "备注")
    private String note;

    @ApiModelProperty(value = "来源[0->购物；1->管理员修改;2->活动]")
    @TableField("source_tyoe")
    private Integer sourceTyoe;


}
