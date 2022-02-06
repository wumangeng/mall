/*
 *    Copyright (c) 2018-2025, lengleng All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * Neither the name of the pig4cloud.com developer nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 * Author: lengleng (wangiegie@gmail.com)
 */
package com.mallcloud.mall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mallcloud.mall.product.api.entity.Brand;
import com.mallcloud.mall.product.api.entity.Category;
import com.mallcloud.mall.product.api.entity.CategoryBrandRelation;
import com.mallcloud.mall.product.mapper.CategoryBrandRelationMapper;
import com.mallcloud.mall.product.service.BrandService;
import com.mallcloud.mall.product.service.CategoryBrandRelationService;
import com.mallcloud.mall.product.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 
 *
 * @author painter
 * @date 2021-08-11 21:13:45
 */
@Service
public class CategoryBrandRelationServiceImpl extends ServiceImpl<CategoryBrandRelationMapper, CategoryBrandRelation> implements CategoryBrandRelationService {

	@Autowired
	private CategoryService categoryService;
	@Autowired
	private BrandService brandService;
	@Autowired
	private CategoryBrandRelationService relationService;

	@Override
	public boolean saveCategoryBrandRelation(CategoryBrandRelation categoryBrandRelation) {
		Category category = categoryService.getById(categoryBrandRelation.getCatelogId());
		Brand brand = brandService.getById(categoryBrandRelation.getBrandId());

		categoryBrandRelation.setCatelogName(category.getName());
		categoryBrandRelation.setBrandName(brand.getName());

		return baseMapper.insert(categoryBrandRelation)>0;
	}

	@Override
	public boolean updateBrandName(Long brandId, String brandName) {
		CategoryBrandRelation relation = new CategoryBrandRelation();
		relation.setBrandId(brandId);
		relation.setBrandName(brandName);
		return baseMapper.update(relation,new QueryWrapper<CategoryBrandRelation>().eq("brand_id",brandId))>0;
	}

	@Override
	public boolean updateCategoryName(Long categoryId, String categoryName) {
		CategoryBrandRelation relation = new CategoryBrandRelation();
		relation.setCatelogId(categoryId);
		relation.setCatelogName(categoryName);
		return baseMapper.update(relation,new QueryWrapper<CategoryBrandRelation>().eq("catelog_id",categoryId))>0;
	}

	@Override
	public List<Brand> getBrandsByCatId(Long catId) {
		List<CategoryBrandRelation> catelogId;
		if (catId==null){
			catelogId= relationService.list();
		}else {
			catelogId= relationService.list(new QueryWrapper<CategoryBrandRelation>().eq("catelog_id", catId));
		}
		List<Brand> collect = catelogId.stream().map(item -> {
			return brandService.getById(item.getBrandId());
		}).collect(Collectors.toList());
		return collect;
	}
}
