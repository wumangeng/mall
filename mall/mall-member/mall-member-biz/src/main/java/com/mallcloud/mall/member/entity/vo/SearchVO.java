package com.mallcloud.mall.member.entity.vo;

import lombok.Data;

@Data
public class SearchVO {
	private long size;
	private long current;
	private String searchParam;
}
