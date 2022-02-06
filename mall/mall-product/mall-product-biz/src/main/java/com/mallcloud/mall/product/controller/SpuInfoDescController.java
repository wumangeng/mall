package com.mallcloud.mall.product.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mallcloud.mall.common.core.util.R;
import com.mallcloud.mall.common.log.annotation.SysLog;
import com.mallcloud.mall.product.api.entity.SpuInfoDesc;
import com.mallcloud.mall.product.service.SpuInfoDescService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * spu信息介绍
 *
 * @author painter
 * @date 2021-08-27 17:09:10
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/spuinfodesc" )
@Api(value = "spuinfodesc", tags = "商品(spu)信息介绍管理")
public class SpuInfoDescController {

	private final  SpuInfoDescService spuInfoDescService;

	/**
	 * 分页查询
	 * @param page 分页对象
	 * @param SpuInfoDesc spu信息介绍
	 * @return
	 */
	@ApiOperation(value = "分页查询", notes = "分页查询")
	@GetMapping("/page" )
//	@PreAuthorize("@pms.hasPermission('product_pmsspuinfodesc_get')" )
	public R getPmsSpuInfoDescPage(Page page, SpuInfoDesc pmsSpuInfoDesc) {
		return R.ok(spuInfoDescService.page(page, Wrappers.query(pmsSpuInfoDesc)));
	}


	/**
	 * 通过id查询spu信息介绍
	 * @param spuId id
	 * @return R
	 */
	@ApiOperation(value = "通过id查询", notes = "通过id查询")
	@GetMapping("/{spuId}" )
//	@PreAuthorize("@pms.hasPermission('product_pmsspuinfodesc_get')" )
	public R getById(@PathVariable("spuId" ) Long spuId) {
		return R.ok(spuInfoDescService.getById(spuId));
	}

	/**
	 * 新增spu信息介绍
	 * @param SpuInfoDesc spu信息介绍
	 * @return R
	 */
	@ApiOperation(value = "新增spu信息介绍", notes = "新增spu信息介绍")
	@SysLog("新增spu信息介绍" )
	@PostMapping
//	@PreAuthorize("@pms.hasPermission('product_pmsspuinfodesc_add')" )
	public R save(@RequestBody SpuInfoDesc pmsSpuInfoDesc) {
		return R.ok(spuInfoDescService.save(pmsSpuInfoDesc));
	}

	/**
	 * 修改spu信息介绍
	 * @param SpuInfoDesc spu信息介绍
	 * @return R
	 */
	@ApiOperation(value = "修改spu信息介绍", notes = "修改spu信息介绍")
	@SysLog("修改spu信息介绍" )
	@PutMapping
//	@PreAuthorize("@pms.hasPermission('product_pmsspuinfodesc_edit')" )
	public R updateById(@RequestBody SpuInfoDesc pmsSpuInfoDesc) {
		return R.ok(spuInfoDescService.updateById(pmsSpuInfoDesc));
	}

	/**
	 * 通过id删除spu信息介绍
	 * @param spuId id
	 * @return R
	 */
	@ApiOperation(value = "通过id删除spu信息介绍", notes = "通过id删除spu信息介绍")
	@SysLog("通过id删除spu信息介绍" )
	@DeleteMapping("/{spuId}" )
//	@PreAuthorize("@pms.hasPermission('product_pmsspuinfodesc_del')" )
	public R removeById(@PathVariable Long spuId) {
		return R.ok(spuInfoDescService.removeById(spuId));
	}

}
