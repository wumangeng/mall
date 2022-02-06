package com.mallcloud.mall.product.service.impl;

import cn.hutool.core.util.StrUtil;
import com.mallcloud.mall.product.api.entity.Brand;
import com.mallcloud.mall.product.mapper.BrandMapper;
import com.mallcloud.mall.product.service.BrandService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mallcloud.mall.product.service.CategoryBrandRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 品牌 服务实现类
 * </p>
 *
 * @author Painter
 * @since 2021-07-10
 */
@Service
public class BrandServiceImpl extends ServiceImpl<BrandMapper, Brand> implements BrandService {

    @Autowired
	private CategoryBrandRelationService categoryBrandRelationService;


	@Override
	public boolean updateDetail(Brand brand) {
		//更新关联表
		if (StrUtil.isNotEmpty(brand.getName())){
			categoryBrandRelationService.updateBrandName(brand.getBrandId(),brand.getName());
		}
		return baseMapper.updateById(brand)>0;
	}
}
