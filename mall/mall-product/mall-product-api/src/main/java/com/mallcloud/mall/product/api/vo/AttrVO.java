package com.mallcloud.mall.product.api.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AttrVO {

	@ApiModelProperty(value = "属性id")
	private Long attrId;

	@ApiModelProperty(value = "属性名")
	private String attrName;

	@ApiModelProperty(value = "是否需要检索[0-不需要，1-需要]")
	private Integer searchType;

	@ApiModelProperty(value = "属性图标")
	private String icon;

	@ApiModelProperty(value = "可选值列表[用逗号分隔]")
	private String valueSelect;

	@ApiModelProperty(value = "属性类型[0-销售属性，1-基本属性，2-既是销售属性又是基本属性]")
	private Integer attrType;

	@ApiModelProperty(value = "启用状态[0 - 禁用，1 - 启用]")
	private Integer enable;

	@ApiModelProperty(value = "所属分类")
	private Long catelogId;

	@ApiModelProperty(value = "快速展示【是否展示在介绍上；0-否 1-是】，在sku中仍然可以调整")
	private Integer showDesc;

	@ApiModelProperty(value = "属性分组id")
	private Long attrGroupId;

	@ApiModelProperty(value = "值类型[0 - 单选，1 - 多选]")
	private Integer valueType;
}
