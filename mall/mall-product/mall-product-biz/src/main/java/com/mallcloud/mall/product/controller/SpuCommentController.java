
package com.mallcloud.mall.product.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mallcloud.mall.common.core.util.R;
import com.mallcloud.mall.common.log.annotation.SysLog;
import com.mallcloud.mall.product.api.entity.SpuComment;
import com.mallcloud.mall.product.service.SpuCommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 商品评价
 *
 * @author painter
 * @date 2021-08-27 17:09:10
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/spuComment" )
@Api(value = "spuComment", tags = "商品评价管理")
public class SpuCommentController {

	private final  SpuCommentService pmsSpuCommentService;

	@ApiOperation(value = "分页查询", notes = "分页查询")
	@GetMapping("/page" )
//	@PreAuthorize("@pms.hasPermission('product_pmsspucomment_get')" )
	public R getPmsSpuCommentPage(Page page, SpuComment spuComment) {
		return R.ok(pmsSpuCommentService.page(page, Wrappers.query(spuComment)));
	}


	/**
	 * 通过id查询商品评价
	 * @param id id
	 * @return R
	 */
	@ApiOperation(value = "通过id查询", notes = "通过id查询")
	@GetMapping("/{id}" )
//	@PreAuthorize("@pms.hasPermission('product_pmsspucomment_get')" )
	public R getById(@PathVariable("id" ) Long id) {
		return R.ok(pmsSpuCommentService.getById(id));
	}

	/**
	 * 新增商品评价
	 * @param pmsSpuComment 商品评价
	 * @return R
	 */
	@ApiOperation(value = "新增商品评价", notes = "新增商品评价")
	@SysLog("新增商品评价" )
	@PostMapping
//	@PreAuthorize("@pms.hasPermission('product_pmsspucomment_add')" )
	public R save(@RequestBody SpuComment pmsSpuComment) {
		return R.ok(pmsSpuCommentService.save(pmsSpuComment));
	}

	/**
	 * 修改商品评价
	 * @param pmsSpuComment 商品评价
	 * @return R
	 */
	@ApiOperation(value = "修改商品评价", notes = "修改商品评价")
	@SysLog("修改商品评价" )
	@PutMapping
//	@PreAuthorize("@pms.hasPermission('product_pmsspucomment_edit')" )
	public R updateById(@RequestBody SpuComment pmsSpuComment) {
		return R.ok(pmsSpuCommentService.updateById(pmsSpuComment));
	}

	/**
	 * 通过id删除商品评价
	 * @param id id
	 * @return R
	 */
	@ApiOperation(value = "通过id删除商品评价", notes = "通过id删除商品评价")
	@SysLog("通过id删除商品评价" )
	@DeleteMapping("/{id}" )
//	@PreAuthorize("@pms.hasPermission('product_pmsspucomment_del')" )
	public R removeById(@PathVariable Long id) {
		return R.ok(pmsSpuCommentService.removeById(id));
	}

}
