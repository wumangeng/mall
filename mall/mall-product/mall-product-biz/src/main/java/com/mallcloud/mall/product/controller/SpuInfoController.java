package com.mallcloud.mall.product.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mallcloud.mall.product.vo.SpuInfoSearchVO;
import com.mallcloud.mall.product.vo.SpuSaveVo;
import com.mallcloud.mall.common.core.util.R;
import com.mallcloud.mall.common.log.annotation.SysLog;
import com.mallcloud.mall.product.api.entity.SpuInfo;
import com.mallcloud.mall.product.service.SpuInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * spu信息
 *
 * @author painter
 * @date 2021-08-27 17:09:10
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/spuinfo" )
@Api(value = "spuinfo", tags = "商品(spu)信息管理")
public class SpuInfoController {

	private final  SpuInfoService spuInfoService;


	@ApiOperation(value = "分页查询", notes = "分页查询")
	@SysLog("spu信息-分页查询")
	@GetMapping("/page" )
//	@PreAuthorize("@pms.hasPermission('product_pmsspuinfo_get')" )
	public R getPmsSpuInfoPage(Page page, SpuInfo pmsSpuInfo) {
		return R.ok(spuInfoService.page(page, Wrappers.query(pmsSpuInfo)));
	}

	@ApiOperation(value = "条件分页查询", notes = "条件分页查询")
	@SysLog("spu信息-条件分页查询")
	@GetMapping("/searchPage" )
//	@PreAuthorize("@pms.hasPermission('product_pmsspuinfo_get')" )
	public R getPmsSpuInfoSearchPage(SpuInfoSearchVO spuInfoSearchVO) {
		System.err.println(spuInfoSearchVO);
		return R.ok(spuInfoService.queryPageByCondition(spuInfoSearchVO));
	}

	@ApiOperation(value = "商品上架", notes = "商品上架")
	@SysLog("spu信息-商品上架")
	@PostMapping("/upSpu/{spuId}")
	public R upSpu(@PathVariable Long spuId) {
		return R.ok(spuInfoService.upSpu(spuId));
	}


	/**
	 * 通过id查询spu信息
	 * @param id id
	 * @return R
	 */
	@ApiOperation(value = "通过id查询", notes = "通过id查询")
	@GetMapping("/{id}" )
//	@PreAuthorize("@pms.hasPermission('product_pmsspuinfo_get')" )
	public R getById(@PathVariable("id" ) Long id) {
		return R.ok(spuInfoService.getById(id));
	}


	@ApiOperation(value = "添加spu(商品)信息", notes = "添加spu(商品)信息")
	@SysLog("添加spu(商品)信息" )
	@PostMapping
//	@PreAuthorize("@pms.hasPermission('product_pmsspuinfo_add')" )
	public R saveSpu(@RequestBody SpuSaveVo spuSaveVo) {
		return R.ok(spuInfoService.saveSpuInfo(spuSaveVo));
	}


	@ApiOperation(value = "修改spu信息", notes = "修改spu信息")
	@SysLog("修改spu信息" )
	@PutMapping
//	@PreAuthorize("@pms.hasPermission('product_pmsspuinfo_edit')" )
	public R updateById(@RequestBody SpuInfo pmsSpuInfo) {
		return R.ok(spuInfoService.updateById(pmsSpuInfo));
	}

	/**
	 * 通过id删除spu信息
	 * @param id id
	 * @return R
	 */
	@ApiOperation(value = "通过id删除spu信息", notes = "通过id删除spu信息")
	@SysLog("通过id删除spu信息" )
	@DeleteMapping("/{id}" )
//	@PreAuthorize("@pms.hasPermission('product_spuinfo_del')" )
	public R removeById(@PathVariable Long id) {
		return R.ok(spuInfoService.removeById(id));
	}

}
