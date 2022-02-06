package com.mallcloud.mall.product.api.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 属性分组
 * </p>
 *
 * @author Painter
 * @since 2021-07-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("pms_attr_group")
@ApiModel(value="AttrGroup对象", description="属性分组")
public class AttrGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "分组id")
    @TableId(value = "attr_group_id")
    private Long attrGroupId;

    @ApiModelProperty(value = "组名")
    @TableField("attr_group_name")
    private String attrGroupName;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "描述")
    private String descript;

    @ApiModelProperty(value = "组图标")
    private String icon;

    @ApiModelProperty(value = "所属分类id")
    @TableField("catelog_id")
    private Long catelogId;

	@ApiModelProperty(value = "分类路径")
	@TableField(exist = false)
	private String catelogName;

    @ApiModelProperty(value = "分类路径")
	@TableField(exist = false)
	private Long[] catelogIdPath;

}
