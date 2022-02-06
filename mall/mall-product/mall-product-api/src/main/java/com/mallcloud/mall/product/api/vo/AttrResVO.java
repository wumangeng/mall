package com.mallcloud.mall.product.api.vo;

import lombok.Data;

@Data
public class AttrResVO extends AttrVO {
	private String categoryName;
	private String attrGroupName;
	private Long[] catelogIdPath;
}
