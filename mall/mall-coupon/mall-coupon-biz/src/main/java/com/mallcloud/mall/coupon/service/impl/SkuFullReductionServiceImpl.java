package com.mallcloud.mall.coupon.service.impl;

import com.mallcloud.mall.coupon.api.entity.MemberPrice;
import com.mallcloud.mall.coupon.api.entity.SkuFullReduction;
import com.mallcloud.mall.coupon.api.entity.SkuLadder;
import com.mallcloud.mall.coupon.api.vo.MemberPriceVO;
import com.mallcloud.mall.coupon.api.vo.SkuReductionTo;
import com.mallcloud.mall.coupon.mapper.SkuFullReductionMapper;
import com.mallcloud.mall.coupon.service.MemberPriceService;
import com.mallcloud.mall.coupon.service.SkuFullReductionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mallcloud.mall.coupon.service.SkuLadderService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 商品满减信息 服务实现类
 * </p>
 *
 * @author Painter
 * @since 2021-07-10
 */
@Service
public class SkuFullReductionServiceImpl extends ServiceImpl<SkuFullReductionMapper, SkuFullReduction> implements SkuFullReductionService {

	@Autowired
	 private SkuLadderService skuLadderService;

	@Autowired
	private MemberPriceService memberPriceService;

	@Override
	public Boolean saveSkuReduction(SkuReductionTo reductionTo) {
		//1、// 6.4）、sku的优惠、满减等信息；mall_sms->sms_sku_ladder\sms_sku_full_reduction\sms_member_price
		//sms_sku_ladder
		SkuLadder skuLadderEntity = new SkuLadder();
		skuLadderEntity.setSkuId(reductionTo.getSkuId());
		skuLadderEntity.setFullCount(reductionTo.getFullCount());
		skuLadderEntity.setDiscount(reductionTo.getDiscount());
		skuLadderEntity.setAddOther(reductionTo.getCountStatus());
		if(reductionTo.getFullCount() > 0){
			skuLadderService.save(skuLadderEntity);
		}




		//2、sms_sku_full_reduction
		SkuFullReduction reductionEntity = new SkuFullReduction();
		BeanUtils.copyProperties(reductionTo,reductionEntity);
		if(reductionEntity.getFullPrice().compareTo(new BigDecimal("0"))==1){
			this.save(reductionEntity);
		}


		//3、sms_member_price
		List<MemberPriceVO> memberPrice = reductionTo.getMemberPrice();

		List<MemberPrice> collect = memberPrice.stream().map(item -> {
			MemberPrice priceEntity = new MemberPrice();
			priceEntity.setSkuId(reductionTo.getSkuId());
			priceEntity.setMemberLevelId(item.getId());
			priceEntity.setMemberLevelName(item.getName());
			priceEntity.setMemberPrice(item.getPrice());
			priceEntity.setAddOther(1);
			return priceEntity;
		}).filter(item->{
			return item.getMemberPrice().compareTo(new BigDecimal("0")) == 1;
		}).collect(Collectors.toList());

		return memberPriceService.saveBatch(collect);
	}
}
