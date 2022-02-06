package com.mallcloud.mall.ware.vo;

import lombok.Data;

@Data
public class SkuSearchVO {

	private long pageSize;
	private long currentPage;
	private Long skuId;
	private Long wareId;

}
