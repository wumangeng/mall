package com.mallcloud.mall.ware.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mallcloud.mall.common.core.util.R;
import com.mallcloud.mall.common.log.annotation.SysLog;
import com.mallcloud.mall.ware.api.entity.WareOrderTaskDetail;
import com.mallcloud.mall.ware.service.WareOrderTaskDetailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 库存工作单
 *
 * @author Painter
 * @date 2021-09-02 14:54:55
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/wareOrderTaskdetail" )
@Api(value = "wareOrderTaskdetail", tags = "库存工作单管理")
public class WareOrderTaskDetailController {

	private final  WareOrderTaskDetailService wareOrderTaskDetailService;


	@ApiOperation(value = "分页查询", notes = "分页查询")
	@GetMapping("/page" )
//	@PreAuthorize("@pms.hasPermission('ware_wmswareordertaskdetail_get')" )
	public R getWmsWareOrderTaskDetailPage(Page page, WareOrderTaskDetail wareOrderTaskDetail) {
		return R.ok(wareOrderTaskDetailService.page(page, Wrappers.query(wareOrderTaskDetail)));
	}


	@ApiOperation(value = "通过id查询", notes = "通过id查询")
	@GetMapping("/{id}" )
//	@PreAuthorize("@pms.hasPermission('ware_wmswareordertaskdetail_get')" )
	public R getById(@PathVariable("id" ) Long id) {
		return R.ok(wareOrderTaskDetailService.getById(id));
	}

	@ApiOperation(value = "新增库存工作单", notes = "新增库存工作单")
	@SysLog("新增库存工作单" )
	@PostMapping
//	@PreAuthorize("@pms.hasPermission('ware_wmswareordertaskdetail_add')" )
	public R save(@RequestBody WareOrderTaskDetail wareOrderTaskDetail) {
		return R.ok(wareOrderTaskDetailService.save(wareOrderTaskDetail));
	}


	@ApiOperation(value = "修改库存工作单", notes = "修改库存工作单")
	@SysLog("修改库存工作单" )
	@PutMapping
//	@PreAuthorize("@pms.hasPermission('ware_wmswareordertaskdetail_edit')" )
	public R updateById(@RequestBody WareOrderTaskDetail wareOrderTaskDetail) {
		return R.ok(wareOrderTaskDetailService.updateById(wareOrderTaskDetail));
	}


	@ApiOperation(value = "通过id删除库存工作单", notes = "通过id删除库存工作单")
	@SysLog("通过id删除库存工作单" )
	@DeleteMapping("/{id}" )
//	@PreAuthorize("@pms.hasPermission('ware_wmswareordertaskdetail_del')" )
	public R removeById(@PathVariable Long id) {
		return R.ok(wareOrderTaskDetailService.removeById(id));
	}

}
