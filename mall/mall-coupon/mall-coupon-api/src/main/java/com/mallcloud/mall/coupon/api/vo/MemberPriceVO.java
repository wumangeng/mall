package com.mallcloud.mall.coupon.api.vo;

import lombok.Data;

import java.math.BigDecimal;


@Data
public class MemberPriceVO {

    private Long id;
    private String name;
    private BigDecimal price;

}
