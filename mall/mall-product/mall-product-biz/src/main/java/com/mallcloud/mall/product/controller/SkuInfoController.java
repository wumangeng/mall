package com.mallcloud.mall.product.controller;

import com.mallcloud.mall.product.vo.SkuInfoSearchVO;
import com.mallcloud.mall.common.core.util.R;
import com.mallcloud.mall.common.log.annotation.SysLog;
import com.mallcloud.mall.product.api.entity.SkuInfo;
import com.mallcloud.mall.product.service.SkuInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * sku信息
 *
 * @author painter
 * @date 2021-08-27 17:09:11
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/skuInfo" )
@Api(value = "skuInfo", tags = "sku信息管理")
public class SkuInfoController {

	private final  SkuInfoService skuInfoService;

	@ApiOperation(value = "SkuInfo分页条件查询", notes = "SkuInfo分页条件查询")
	@GetMapping("/list" )
	@SysLog("SkuInfo分页条件查询")
//	@PreAuthorize("@pms.hasPermission('product_pmsskuinfo_get')" )
	public R getSkuInfoPage(SkuInfoSearchVO searchVO) {
		return R.ok(skuInfoService.queryPageByCondition(searchVO));
	}


	/**
	 * 通过id查询sku信息
	 * @param skuId id
	 * @return R
	 */
	@ApiOperation(value = "通过id查询sku信息", notes = "通过id查询sku信息")
	@GetMapping("/{skuId}" )
//	@PreAuthorize("@pms.hasPermission('product_pmsskuinfo_get')" )
	public R getById(@PathVariable("skuId" ) Long skuId) {
		return R.ok(skuInfoService.getById(skuId));
	}

	/**
	 * 新增sku信息
	 * @param skuInfo sku信息
	 * @return R
	 */
	@ApiOperation(value = "新增sku信息", notes = "新增sku信息")
	@SysLog("新增sku信息" )
	@PostMapping
//	@PreAuthorize("@pms.hasPermission('product_pmsskuinfo_add')" )
	public R save(@RequestBody SkuInfo skuInfo) {
		return R.ok(skuInfoService.save(skuInfo));
	}

	/**
	 * 修改sku信息
	 * @param pmsSkuInfo sku信息
	 * @return R
	 */
	@ApiOperation(value = "修改sku信息", notes = "修改sku信息")
	@SysLog("修改sku信息" )
	@PutMapping
//	@PreAuthorize("@pms.hasPermission('product_pmsskuinfo_edit')" )
	public R updateById(@RequestBody SkuInfo pmsSkuInfo) {
		return R.ok(skuInfoService.updateById(pmsSkuInfo));
	}

	/**
	 * 通过id删除sku信息
	 * @param skuId id
	 * @return R
	 */
	@ApiOperation(value = "通过id删除sku信息", notes = "通过id删除sku信息")
	@SysLog("通过id删除sku信息" )
	@DeleteMapping("/{skuId}" )
//	@PreAuthorize("@pms.hasPermission('product_pmsskuinfo_del')" )
	public R removeById(@PathVariable Long skuId) {
		return R.ok(skuInfoService.removeById(skuId));
	}

}
