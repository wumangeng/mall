package com.mallcloud.mall.product.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SkuInfoSearchVO {
	private long pageSize;
	private long currentPage;

	@ApiModelProperty(value = "搜索框参数")
	private String searchParam;

	@ApiModelProperty(value = "品牌id")
	private Long brandId;

	@ApiModelProperty(value = "所属分类id")
	private Long catalogId;

	@ApiModelProperty(value = "价格范围(最低)")
	private Float min;

	@ApiModelProperty(value = "价格范围(最高)")
	private Float max;
}
