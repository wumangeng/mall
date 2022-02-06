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

package com.mallcloud.mall.product.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mallcloud.mall.product.api.entity.Brand;
import com.mallcloud.mall.product.api.entity.CategoryBrandRelation;
import com.mallcloud.mall.product.service.CategoryBrandRelationService;
import com.mallcloud.mall.product.vo.BrandVO;
import com.mallcloud.mall.common.core.util.R;
import com.mallcloud.mall.common.log.annotation.SysLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


/**
 * 
 *
 * @author painter
 * @date 2021-08-11 21:13:45
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/categorybrandrelation" )
@Api(value = "categorybrandrelation", tags = "品牌分类关联管理")
public class CategoryBrandRelationController {

    private final CategoryBrandRelationService categoryBrandRelationService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param categoryBrandRelation
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getCategoryBrandRelationPage(Page page, CategoryBrandRelation categoryBrandRelation) {
        return R.ok(categoryBrandRelationService.page(page, Wrappers.query(categoryBrandRelation)));
    }

	@ApiOperation(value = "通过品牌id查询分类列表", notes = "通过品牌id查询分类列表")
	@GetMapping("/catelog/list/{brandId}" )
	public R getCategoryBrandRelationList(@PathVariable("brandId") Long brandId) {
		return R.ok( categoryBrandRelationService.list(
				new LambdaQueryWrapper<CategoryBrandRelation>().eq(CategoryBrandRelation::getBrandId, brandId)));
	}

	@ApiOperation(value = "获取指定分类下的所有品牌", notes = "获取指定分类下的所有品牌")
	@GetMapping("/brands/list/{catId}")
	public R relationBrandsList(@PathVariable Long catId){
		List<Brand> vos = categoryBrandRelationService.getBrandsByCatId(catId);
		System.err.println(vos);
		List<BrandVO> collect = vos.stream().map(item -> {
			BrandVO brandVo = new BrandVO();
			brandVo.setBrandId(item.getBrandId());
			brandVo.setBrandName(item.getName());

			return brandVo;
		}).collect(Collectors.toList());

		return R.ok(collect);

	}


    /**
     * 通过id查询
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Long id) {
        return R.ok(categoryBrandRelationService.getById(id));
    }

    /**
     * 新增
     * @param categoryBrandRelation 
     * @return R
     */
    @ApiOperation(value = "新增", notes = "新增")
    @SysLog("新增" )
    @PostMapping
    public R save(@RequestBody CategoryBrandRelation categoryBrandRelation) {
		CategoryBrandRelation relation = categoryBrandRelationService.getOne(new LambdaQueryWrapper<CategoryBrandRelation>()
				.eq(CategoryBrandRelation::getBrandId, categoryBrandRelation.getBrandId())
				.eq(CategoryBrandRelation::getCatelogId, categoryBrandRelation.getCatelogId())
		);
		if (relation!=null) return R.ok().setMsg("关联已存在！");
        return R.ok(categoryBrandRelationService.saveCategoryBrandRelation(categoryBrandRelation));
    }

    /**
     * 修改
     * @param categoryBrandRelation 
     * @return R
     */
    @ApiOperation(value = "修改", notes = "修改")
    @SysLog("修改" )
    @PutMapping
    public R updateById(@RequestBody CategoryBrandRelation categoryBrandRelation) {
        return R.ok(categoryBrandRelationService.updateById(categoryBrandRelation));
    }

    /**
     * 通过id删除
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除", notes = "通过id删除")
    @SysLog("通过id删除" )
    @DeleteMapping("/{id}" )
    public R removeById(@PathVariable Long id) {
        return R.ok(categoryBrandRelationService.removeById(id));
    }

}
