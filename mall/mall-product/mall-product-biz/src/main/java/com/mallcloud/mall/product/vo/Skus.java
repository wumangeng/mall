package com.mallcloud.mall.product.vo;
import com.mallcloud.mall.coupon.api.vo.MemberPriceVO;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;


@Data
public class Skus {

    private List<AttrTemp> attr;
    private String skuName;
    private BigDecimal price;
    private String skuTitle;
    private String skuSubtitle;
    private List<Images> images;
    private List<String> descar;
    private Integer fullCount;
    private BigDecimal discount;
    private Integer countStatus;
    private BigDecimal fullPrice;
    private BigDecimal reducePrice;
    private Integer priceStatus;
    private List<MemberPriceVO> memberPrice;


}
