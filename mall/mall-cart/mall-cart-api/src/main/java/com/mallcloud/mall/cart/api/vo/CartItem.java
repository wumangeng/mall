package com.mallcloud.mall.cart.api.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@ApiModel(value="购物车内商品对象", description="购物项内容")
public class CartItem {
	@ApiModelProperty(value = "商品id")
	private Long skuId;

	@ApiModelProperty(value = "是否选中")
	private Boolean check = true;

	@ApiModelProperty(value = "商品标题")
	private String title;

	@ApiModelProperty(value = "商品图片")
	private String image;

	@ApiModelProperty(value = "商品属性")
	private List<String> skuAttr;

	@ApiModelProperty(value = "商品单价")
	private BigDecimal price;

	@ApiModelProperty(value = "商品数量")
	private Integer count;

	@ApiModelProperty(value = "商品总价")
	private BigDecimal totalPrice;

	public BigDecimal getTotalPrice() {
		return this.price.multiply(new BigDecimal(""+this.count));
	}
}
