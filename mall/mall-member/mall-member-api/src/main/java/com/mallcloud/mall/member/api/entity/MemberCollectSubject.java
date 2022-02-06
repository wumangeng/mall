package com.mallcloud.mall.member.api.entity;

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
 * 会员收藏的专题活动
 * </p>
 *
 * @author Painter
 * @since 2021-07-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("ums_member_collect_subject")
@ApiModel(value="MemberCollectSubject对象", description="会员收藏的专题活动")
public class MemberCollectSubject implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id")
    private Long id;

    @ApiModelProperty(value = "subject_id")
    @TableField("subject_id")
    private Long subjectId;

    @ApiModelProperty(value = "subject_name")
    @TableField("subject_name")
    private String subjectName;

    @ApiModelProperty(value = "subject_img")
    @TableField("subject_img")
    private String subjectImg;

    @ApiModelProperty(value = "活动url")
    @TableField("subject_urll")
    private String subjectUrll;


}
