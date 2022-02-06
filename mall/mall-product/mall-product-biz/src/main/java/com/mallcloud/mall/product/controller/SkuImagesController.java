package com.mallcloud.mall.product.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mallcloud.mall.common.core.util.R;
import com.mallcloud.mall.common.log.annotation.SysLog;
import com.mallcloud.mall.product.api.entity.SkuImages;
import com.mallcloud.mall.product.service.SkuImagesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * sku图片
 *
 * @author painter
 * @date 2021-08-27 17:09:10
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/pmsskuimages" )
@Api(value = "pmsskuimages", tags = "sku图片管理")
public class SkuImagesController {

	private final  SkuImagesService skuImagesService;

	/**
	 * 分页查询
	 * @param page 分页对象
	 * @param pmsSkuImages sku图片
	 * @return
	 */
	@ApiOperation(value = "分页查询", notes = "分页查询")
	@GetMapping("/page" )
//	@PreAuthorize("@pms.hasPermission('product_pmsskuimages_get')" )
	public R getPmsSkuImagesPage(Page page, SkuImages pmsSkuImages) {
		return R.ok(skuImagesService.page(page, Wrappers.query(pmsSkuImages)));
	}


	/**
	 * 通过id查询sku图片
	 * @param id id
	 * @return R
	 */
	@ApiOperation(value = "通过id查询", notes = "通过id查询")
	@GetMapping("/{id}" )
//	@PreAuthorize("@pms.hasPermission('product_pmsskuimages_get')" )
	public R getById(@PathVariable("id" ) Long id) {
		return R.ok(skuImagesService.getById(id));
	}

	/**
	 * 新增sku图片
	 * @param pmsSkuImages sku图片
	 * @return R
	 */
	@ApiOperation(value = "新增sku图片", notes = "新增sku图片")
	@SysLog("新增sku图片" )
	@PostMapping
//	@PreAuthorize("@pms.hasPermission('product_pmsskuimages_add')" )
	public R save(@RequestBody SkuImages pmsSkuImages) {
		return R.ok(skuImagesService.save(pmsSkuImages));
	}

	/**
	 * 修改sku图片
	 * @param pmsSkuImages sku图片
	 * @return R
	 */
	@ApiOperation(value = "修改sku图片", notes = "修改sku图片")
	@SysLog("修改sku图片" )
	@PutMapping
//	@PreAuthorize("@pms.hasPermission('product_pmsskuimages_edit')" )
	public R updateById(@RequestBody SkuImages pmsSkuImages) {
		return R.ok(skuImagesService.updateById(pmsSkuImages));
	}

	/**
	 * 通过id删除sku图片
	 * @param id id
	 * @return R
	 */
	@ApiOperation(value = "通过id删除sku图片", notes = "通过id删除sku图片")
	@SysLog("通过id删除sku图片" )
	@DeleteMapping("/{id}" )
//	@PreAuthorize("@pms.hasPermission('product_pmsskuimages_del')" )
	public R removeById(@PathVariable Long id) {
		return R.ok(skuImagesService.removeById(id));
	}

}
