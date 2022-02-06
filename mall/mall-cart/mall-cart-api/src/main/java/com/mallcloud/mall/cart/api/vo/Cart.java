package com.mallcloud.mall.cart.api.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@ApiModel(value="购物车对象", description="")
public class Cart {

	@ApiModelProperty("商品项")
	private List<CartItem> cartItems;

	@ApiModelProperty("商品总数")
	private Integer countNum;

	@ApiModelProperty("商品类型")
	private Integer countType;

	@ApiModelProperty("商品总价")
	private BigDecimal totalAmount;

	@ApiModelProperty("优惠价格")
	private BigDecimal reduce=new BigDecimal("0.00");

	public Integer getCountNum() {
		return cartItems.stream().map(CartItem::getCount).reduce(0,Integer::sum);
	}

	public Integer getCountType() {
		return cartItems.size();
	}

	public BigDecimal getTotalAmount() {
		// 统计总价
		BigDecimal totalAmount = new BigDecimal("0.00");
		if (cartItems!=null){
			for (CartItem cartItem : cartItems) {
				totalAmount = totalAmount.add(cartItem.getTotalPrice());
			}
		}
		// 减去优惠价格
		return totalAmount.subtract(getReduce());
	}
}
