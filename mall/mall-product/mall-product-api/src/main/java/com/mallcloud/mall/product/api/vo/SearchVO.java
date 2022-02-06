package com.mallcloud.mall.product.api.vo;

import lombok.Data;

@Data
public class SearchVO {
	private long size;
	private long current;
	private String searchParam;
}
