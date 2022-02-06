package com.mallcloud.mall.product.controller;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.mallcloud.mall.common.core.util.R;
import com.mallcloud.mall.product.api.entity.Category;
import com.mallcloud.mall.product.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * <p>
 * 商品三级分类 前端控制器
 * </p>
 *
 * @author Painter
 * @since 2021-07-10
 */
@RestController
@RequestMapping("/category")
@Api(tags = "商品分类")
public class CategoryController {
	@Autowired
	private CategoryService categoryService;

	@ApiOperation("查询所有分类以及子分类（树形）")
	@GetMapping("/list/tree")
	public R listWithTree(){
		return R.ok().setData(categoryService.listWithTree());
	}

	@ApiOperation("根据id查询分类")
	@GetMapping("/getCategoryById/{catId}")
	public R getCategoryById(@PathVariable Long catId){
		return R.ok().setData(categoryService.getById(catId));
	}


	@ApiOperation("批量删除")
	@PostMapping("/deleteBatch")
	public R deleteBatch(@RequestBody Long[] catIdList){
		boolean b = categoryService.removeMenusBatchByIds(Arrays.asList(catIdList));
		return b ? R.ok(true):R.failed(false);
	}

	@ApiOperation("添加分类")
	@PostMapping("/addCategory")
	public R addCategory(@RequestBody Category category){
		boolean b = categoryService.saveCategory(category);
		return b ? R.ok(true):R.failed(false);
	}

	@ApiOperation("修改分类")
	@PostMapping("/updateCategory")
	public R updateCategory(@RequestBody Category category){
		boolean b = categoryService.updateDetail(category);
		return b ? R.ok(true):R.failed(false);
	}


}
