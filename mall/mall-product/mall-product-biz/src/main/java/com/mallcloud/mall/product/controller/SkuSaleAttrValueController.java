package com.mallcloud.mall.product.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mallcloud.mall.common.core.util.R;
import com.mallcloud.mall.common.log.annotation.SysLog;
import com.mallcloud.mall.product.api.entity.SkuSaleAttrValue;
import com.mallcloud.mall.product.service.SkuSaleAttrValueService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * sku销售属性&值
 *
 * @author painter
 * @date 2021-08-27 17:09:11
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/pmsskusaleattrvalue" )
@Api(value = "pmsskusaleattrvalue", tags = "sku销售属性&值管理")
public class SkuSaleAttrValueController {

	private final  SkuSaleAttrValueService pmsSkuSaleAttrValueService;

	/**
	 * 分页查询
	 * @param page 分页对象
	 * @param pmsSkuSaleAttrValue sku销售属性&值
	 * @return
	 */
	@ApiOperation(value = "分页查询", notes = "分页查询")
	@GetMapping("/page" )
//	@PreAuthorize("@pms.hasPermission('product_pmsskusaleattrvalue_get')" )
	public R getPmsSkuSaleAttrValuePage(Page page, SkuSaleAttrValue pmsSkuSaleAttrValue) {
		return R.ok(pmsSkuSaleAttrValueService.page(page, Wrappers.query(pmsSkuSaleAttrValue)));
	}


	/**
	 * 通过id查询sku销售属性&值
	 * @param id id
	 * @return R
	 */
	@ApiOperation(value = "通过id查询", notes = "通过id查询")
	@GetMapping("/{id}" )
//	@PreAuthorize("@pms.hasPermission('product_pmsskusaleattrvalue_get')" )
	public R getById(@PathVariable("id" ) Long id) {
		return R.ok(pmsSkuSaleAttrValueService.getById(id));
	}

	/**
	 * 新增sku销售属性&值
	 * @param SkuSaleAttrValue sku销售属性&值
	 * @return R
	 */
	@ApiOperation(value = "新增sku销售属性&值", notes = "新增sku销售属性&值")
	@SysLog("新增sku销售属性&值" )
	@PostMapping
//	@PreAuthorize("@pms.hasPermission('product_pmsskusaleattrvalue_add')" )
	public R save(@RequestBody SkuSaleAttrValue pmsSkuSaleAttrValue) {
		return R.ok(pmsSkuSaleAttrValueService.save(pmsSkuSaleAttrValue));
	}

	/**
	 * 修改sku销售属性&值
	 * @param SkuSaleAttrValue sku销售属性&值
	 * @return R
	 */
	@ApiOperation(value = "修改sku销售属性&值", notes = "修改sku销售属性&值")
	@SysLog("修改sku销售属性&值" )
	@PutMapping
//	@PreAuthorize("@pms.hasPermission('product_pmsskusaleattrvalue_edit')" )
	public R updateById(@RequestBody SkuSaleAttrValue pmsSkuSaleAttrValue) {
		return R.ok(pmsSkuSaleAttrValueService.updateById(pmsSkuSaleAttrValue));
	}

	/**
	 * 通过id删除sku销售属性&值
	 * @param id id
	 * @return R
	 */
	@ApiOperation(value = "通过id删除sku销售属性&值", notes = "通过id删除sku销售属性&值")
	@SysLog("通过id删除sku销售属性&值" )
	@DeleteMapping("/{id}" )
//	@PreAuthorize("@pms.hasPermission('product_pmsskusaleattrvalue_del')" )
	public R removeById(@PathVariable Long id) {
		return R.ok(pmsSkuSaleAttrValueService.removeById(id));
	}

}
