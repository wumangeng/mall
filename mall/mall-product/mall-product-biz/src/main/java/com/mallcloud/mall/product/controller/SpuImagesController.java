package com.mallcloud.mall.product.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mallcloud.mall.common.core.util.R;
import com.mallcloud.mall.common.log.annotation.SysLog;
import com.mallcloud.mall.product.api.entity.SpuImages;
import com.mallcloud.mall.product.service.SpuImagesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * spu图片
 *
 * @author painter
 * @date 2021-08-27 17:09:10
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/pmsspuimages" )
@Api(value = "pmsspuimages", tags = "商品(spu)图片管理")
public class SpuImagesController {

	private final  SpuImagesService pmsSpuImagesService;

	/**
	 * 分页查询
	 * @param page 分页对象
	 * @param pmsSpuImages spu图片
	 * @return
	 */
	@ApiOperation(value = "分页查询", notes = "分页查询")
	@GetMapping("/page" )
//	@PreAuthorize("@pms.hasPermission('product_pmsspuimages_get')" )
	public R getPmsSpuImagesPage(Page page, SpuImages pmsSpuImages) {
		return R.ok(pmsSpuImagesService.page(page, Wrappers.query(pmsSpuImages)));
	}


	/**
	 * 通过id查询spu图片
	 * @param id id
	 * @return R
	 */
	@ApiOperation(value = "通过id查询", notes = "通过id查询")
	@GetMapping("/{id}" )
//	@PreAuthorize("@pms.hasPermission('product_pmsspuimages_get')" )
	public R getById(@PathVariable("id" ) Long id) {
		return R.ok(pmsSpuImagesService.getById(id));
	}

	/**
	 * 新增spu图片
	 * @param pmsSpuImages spu图片
	 * @return R
	 */
	@ApiOperation(value = "新增spu图片", notes = "新增spu图片")
	@SysLog("新增spu图片" )
	@PostMapping
//	@PreAuthorize("@pms.hasPermission('product_pmsspuimages_add')" )
	public R save(@RequestBody SpuImages pmsSpuImages) {
		return R.ok(pmsSpuImagesService.save(pmsSpuImages));
	}

	/**
	 * 修改spu图片
	 * @param pmsSpuImages spu图片
	 * @return R
	 */
	@ApiOperation(value = "修改spu图片", notes = "修改spu图片")
	@SysLog("修改spu图片" )
	@PutMapping
//	@PreAuthorize("@pms.hasPermission('product_pmsspuimages_edit')" )
	public R updateById(@RequestBody SpuImages pmsSpuImages) {
		return R.ok(pmsSpuImagesService.updateById(pmsSpuImages));
	}

	/**
	 * 通过id删除spu图片
	 * @param id id
	 * @return R
	 */
	@ApiOperation(value = "通过id删除spu图片", notes = "通过id删除spu图片")
	@SysLog("通过id删除spu图片" )
	@DeleteMapping("/{id}" )
//	@PreAuthorize("@pms.hasPermission('product_pmsspuimages_del')" )
	public R removeById(@PathVariable Long id) {
		return R.ok(pmsSpuImagesService.removeById(id));
	}

}
