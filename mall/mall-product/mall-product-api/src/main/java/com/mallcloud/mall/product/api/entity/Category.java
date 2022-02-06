package com.mallcloud.mall.product.api.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 商品三级分类
 * </p>
 *
 * @author Painter
 * @since 2021-07-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("pms_category")
@ApiModel(value="Category对象", description="商品三级分类")
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "分类id")
    @TableId(value = "cat_id")
    private Long catId;

    @ApiModelProperty(value = "分类名称")
    private String name;

    @ApiModelProperty(value = "父分类id")
    @TableField("parent_cid")
    private Long parentCid;

    @ApiModelProperty(value = "层级")
    @TableField("cat_level")
    private Integer catLevel;

    @ApiModelProperty(value = "是否显示[0-不显示，1显示]")
    @TableField("show_status")
	@TableLogic(value = "1",delval = "0")
    private Integer showStatus;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "图标地址")
    private String icon;

    @ApiModelProperty(value = "计量单位")
    @TableField("product_unit")
    private String productUnit;

    @ApiModelProperty(value = "商品数量")
    @TableField("product_count")
    private Integer productCount;

    @ApiModelProperty(value = "子目录（节点）")
	@TableField(exist = false)
	@JsonInclude(JsonInclude.Include.NON_EMPTY) //此字段不为空时返回，为空则无此字段
	private List<Category> children;

}
