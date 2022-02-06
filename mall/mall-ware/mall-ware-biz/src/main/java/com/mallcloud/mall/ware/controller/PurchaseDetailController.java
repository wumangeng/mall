
package com.mallcloud.mall.ware.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mallcloud.mall.ware.api.entity.PurchaseDetail;
import com.mallcloud.mall.ware.service.PurchaseDetailService;
import com.mallcloud.mall.ware.vo.PurchaseDetailSearchVO;
import com.mallcloud.mall.common.core.util.R;
import com.mallcloud.mall.common.log.annotation.SysLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;


/**
 * @author Painter
 * @date 2021-09-02 14:54:54
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/purchaseDetail" )
@Api(value = "purchaseDetail", tags = "采购需求清单管理")
public class PurchaseDetailController {

	private final  PurchaseDetailService purchaseDetailService;


	@ApiOperation(value = "条件分页查询", notes = "条件分页查询")
	@GetMapping("/searchPage" )
//	@PreAuthorize("@pms.hasPermission('ware_wmspurchasedetail_get')" )
	public R getWmsPurchaseDetailSearchPage(PurchaseDetailSearchVO searchVO) {
		return R.ok(purchaseDetailService.queryPage(searchVO));
	}

	@ApiOperation(value = "分页查询", notes = "分页查询")
	@GetMapping("/page" )
//	@PreAuthorize("@pms.hasPermission('ware_wmspurchasedetail_get')" )
	public R getWmsPurchaseDetailPage(Page page, PurchaseDetail purchaseDetail) {
		return R.ok(purchaseDetailService.page(page, Wrappers.query(purchaseDetail)));
	}



	@ApiOperation(value = "通过id查询", notes = "通过id查询")
	@GetMapping("/{id}" )
//	@PreAuthorize("@pms.hasPermission('ware_wmspurchasedetail_get')" )
	public R getById(@PathVariable("id" ) Long id) {
		return R.ok(purchaseDetailService.getById(id));
	}


	@ApiOperation(value = "新增", notes = "新增")
	@SysLog("新增" )
	@PostMapping
//	@PreAuthorize("@pms.hasPermission('ware_wmspurchasedetail_add')" )
	public R save(@RequestBody PurchaseDetail purchaseDetail) {
		return R.ok(purchaseDetailService.save(purchaseDetail));
	}

	@ApiOperation(value = "修改", notes = "修改")
	@SysLog("修改" )
	@PutMapping
//	@PreAuthorize("@pms.hasPermission('ware_wmspurchasedetail_edit')" )
	public R updateById(@RequestBody PurchaseDetail purchaseDetail) {
		return R.ok(purchaseDetailService.updateById(purchaseDetail));
	}


	@ApiOperation(value = "通过id删除", notes = "通过id删除")
	@SysLog("通过id删除" )
	@DeleteMapping("/{id}" )
//	@PreAuthorize("@pms.hasPermission('ware_wmspurchasedetail_del')" )
	public R removeById(@PathVariable Long id) {
		return R.ok(purchaseDetailService.removeById(id));
	}

	@ApiOperation(value = "批量删除采购需求信息", notes = "批量删除采购需求信息")
	@SysLog("批量删除采购需求信息" )
	@DeleteMapping("/deleteBatch" )
//	@PreAuthorize("@pms.hasPermission('ware_wmspurchase_del')" )
	public R removeBatchById(@RequestBody Long[] Ids) {
		return R.ok(purchaseDetailService.removeByIds(Arrays.asList(Ids)));
	}
}
