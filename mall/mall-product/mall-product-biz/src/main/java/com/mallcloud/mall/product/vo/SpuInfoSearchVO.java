package com.mallcloud.mall.product.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SpuInfoSearchVO {

	private long pageSize;
	private long currentPage;

	@ApiModelProperty(value = "搜索框参数")
	private String searchParam;

	@ApiModelProperty(value = "上架状态[0 - 下架，1 - 上架]")
	private Integer publishStatus;

	@ApiModelProperty(value = "品牌id")
	private Long brandId;

	@ApiModelProperty(value = "所属分类id")
	private Long catalogId;
}
