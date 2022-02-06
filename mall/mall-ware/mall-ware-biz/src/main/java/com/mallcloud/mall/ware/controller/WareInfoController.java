package com.mallcloud.mall.ware.controller;

import com.mallcloud.mall.ware.vo.SearchVO;
import com.mallcloud.mall.common.core.util.R;
import com.mallcloud.mall.common.log.annotation.SysLog;
import com.mallcloud.mall.ware.api.entity.WareInfo;
import com.mallcloud.mall.ware.service.WareInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;


/**
 * 仓库信息
 *
 * @author Painter
 * @date 2021-09-02 14:54:54
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/wareInfo" )
@Api(value = "wareInfo", tags = "仓库信息管理")
public class WareInfoController {

	private final  WareInfoService wareInfoService;


	@ApiOperation(value = "分页查询", notes = "分页查询")
	@GetMapping("/list" )
//	@PreAuthorize("@pms.hasPermission('ware_wmswareinfo_get')" )
	public R getWmsWareInfoPage(SearchVO searchVO) {
		return R.ok(wareInfoService.queryPage(searchVO));
	}

	@ApiOperation(value = "查询所有", notes = "查询所有")
	@GetMapping("/listAll" )
//	@PreAuthorize("@pms.hasPermission('ware_wmswareinfo_get')" )
	public R getWmsWareInfoAll() {
		return R.ok(wareInfoService.list());
	}

	@ApiOperation(value = "通过id查询", notes = "通过id查询")
	@GetMapping("/{id}" )
//	@PreAuthorize("@pms.hasPermission('ware_wmswareinfo_get')" )
	public R getById(@PathVariable("id" ) Long id) {
		return R.ok(wareInfoService.getById(id));
	}

	@ApiOperation(value = "新增仓库信息", notes = "新增仓库信息")
	@SysLog("新增仓库信息" )
	@PostMapping
//	@PreAuthorize("@pms.hasPermission('ware_wmswareinfo_add')" )
	public R save(@RequestBody WareInfo wareInfo) {
		return R.ok(wareInfoService.save(wareInfo));
	}


	@ApiOperation(value = "修改仓库信息", notes = "修改仓库信息")
	@SysLog("修改仓库信息" )
	@PutMapping
//	@PreAuthorize("@pms.hasPermission('ware_wmswareinfo_edit')" )
	public R updateById(@RequestBody WareInfo wareInfo) {
		return R.ok(wareInfoService.updateById(wareInfo));
	}


	@ApiOperation(value = "通过id删除仓库信息", notes = "通过id删除仓库信息")
	@SysLog("通过id删除仓库信息" )
	@DeleteMapping("/{id}" )
//	@PreAuthorize("@pms.hasPermission('ware_wmswareinfo_del')" )
	public R removeById(@PathVariable Long id) {
		return R.ok(wareInfoService.removeById(id));
	}

	@ApiOperation(value = "批量删除仓库信息", notes = "批量删除仓库信息")
	@SysLog("批量删除仓库信息" )
	@DeleteMapping("/deleteBatch")
	public R deleteBatch(@RequestBody Long[] ids){
		return R.ok(wareInfoService.removeByIds(Arrays.asList(ids)));
	}
}
