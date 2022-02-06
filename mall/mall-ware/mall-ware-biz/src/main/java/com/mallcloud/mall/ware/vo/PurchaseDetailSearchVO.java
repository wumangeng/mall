package com.mallcloud.mall.ware.vo;

import lombok.Data;

@Data
public class PurchaseDetailSearchVO {
	private long pageSize;
	private long currentPage;
	private String searchParam;
	private Integer status;
	private Long wareId;
}
