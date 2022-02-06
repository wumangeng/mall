package com.mallcloud.mall.ware.vo;

import lombok.Data;

@Data
public class SearchVO {
	private Long pageSize;
	private Long currentPage;
	private String searchParam;
}
