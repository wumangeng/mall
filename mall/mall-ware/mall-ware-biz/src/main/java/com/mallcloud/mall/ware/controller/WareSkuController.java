package com.mallcloud.mall.ware.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mallcloud.mall.ware.vo.SkuSearchVO;
import com.mallcloud.mall.common.core.util.R;
import com.mallcloud.mall.common.log.annotation.SysLog;
import com.mallcloud.mall.ware.api.entity.WareSku;
import com.mallcloud.mall.ware.service.WareSkuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;


/**
 * 商品库存
 *
 * @author Painter
 * @date 2021-09-02 14:54:55
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/wareSku" )
@Api(value = "wareSku", tags = "商品库存管理")
public class WareSkuController {

	private final  WareSkuService wareSkuService;

	/**
	 * 锁定库存
	 * 库存解锁的场景
	 *      1）、下订单成功，订单过期没有支付被系统自动取消或者被用户手动取消，都要解锁库存
	 *      2）、下订单成功，库存锁定成功，接下来的业务调用失败，导致订单回滚。之前锁定的库存就要自动解锁
	 *      3）、
	 */
/*    @PostMapping(value = "/lock/order")
    public R orderLockStock(@RequestBody WareSkuLockVo vo) {

        try {
            boolean lockStock = wareSkuService.orderLockStock(vo);
            return R.ok().setData(lockStock);
        } catch (NoStockException e) {
            return R.error(NO_STOCK_EXCEPTION.getCode(),NO_STOCK_EXCEPTION.getMessage());
        }
    }*/


	@ApiOperation(value = "查询sku是否有库存", notes = "查询sku是否有库存")
	@PostMapping(value = "/hasStock")
	public R getSkuHasStock(@RequestBody List<Long> skuIds) {
		return R.ok().setData(wareSkuService.getSkuHasStock(skuIds));
	}


	@ApiOperation(value = "分页查询", notes = "分页查询")
	@GetMapping("/page" )
//	@PreAuthorize("@pms.hasPermission('ware_wmswaresku_get')" )
	public R getWmsWareSkuPage(Page page, WareSku wareSku) {
		return R.ok(wareSkuService.page(page, Wrappers.query(wareSku)));
	}

	@ApiOperation(value = "条件分页查询", notes = "条件分页查询")
	@GetMapping("/list" )
	public R getWareSkuSearchPage(SkuSearchVO searchVO) {
		return R.ok(wareSkuService.queryPage(searchVO));
	}

	@ApiOperation(value = "通过id查询", notes = "通过id查询")
	@GetMapping("/{id}" )
//	@PreAuthorize("@pms.hasPermission('ware_wmswaresku_get')" )
	public R getById(@PathVariable("id" ) Long id) {
		return R.ok(wareSkuService.getById(id));
	}

	@ApiOperation(value = "新增商品库存", notes = "新增商品库存")
	@SysLog("新增商品库存" )
	@PostMapping
//	@PreAuthorize("@pms.hasPermission('ware_wmswaresku_add')" )
	public R save(@RequestBody WareSku wareSku) {
		return R.ok(wareSkuService.save(wareSku));
	}

	@ApiOperation(value = "修改商品库存", notes = "修改商品库存")
	@SysLog("修改商品库存" )
	@PutMapping
//	@PreAuthorize("@pms.hasPermission('ware_wmswaresku_edit')" )
	public R updateById(@RequestBody WareSku wareSku) {
		return R.ok(wareSkuService.updateById(wareSku));
	}

	@ApiOperation(value = "通过id删除商品库存", notes = "通过id删除商品库存")
	@SysLog("通过id删除商品库存" )
	@DeleteMapping("/{id}" )
//	@PreAuthorize("@pms.hasPermission('ware_wmswaresku_del')" )
	public R removeById(@PathVariable Long id) {
		return R.ok(wareSkuService.removeById(id));
	}

	@ApiOperation(value = "批量删除商品库存", notes = "批量删除商品库存")
	@SysLog("批量删除商品库存" )
	@DeleteMapping("/delete" )
//	@PreAuthorize("@pms.hasPermission('ware_wmswaresku_del')" )
	public R removeBatchById(@RequestBody Long[] skuIds) {
		return R.ok(wareSkuService.removeByIds(Arrays.asList(skuIds)));
	}

}
