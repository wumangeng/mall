
package com.mallcloud.mall.ware.controller;

import com.mallcloud.mall.ware.api.entity.Purchase;
import com.mallcloud.mall.ware.api.entity.WareInfo;
import com.mallcloud.mall.ware.service.PurchaseService;
import com.mallcloud.mall.ware.service.WareInfoService;
import com.mallcloud.mall.ware.vo.MergeVo;
import com.mallcloud.mall.ware.vo.PurchaseDoneVo;
import com.mallcloud.mall.ware.vo.SearchVO;
import com.mallcloud.mall.common.core.util.R;
import com.mallcloud.mall.common.log.annotation.SysLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;


/**
 * 采购信息
 *
 * @author Painter
 * @date 2021-09-02 14:54:54
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/purchase" )
@Api(value = "purchase", tags = "采购信息管理")
public class PurchaseController {

	private final  PurchaseService purchaseService;
	private final WareInfoService wareInfoService;


  /**
   *   采购流程
   * 1、创建采购需求(人工或库存预警生成)
   * 2、生成采购单（人工或者系统自动合并采购需求）
   * 3、分配采购单到采购人员
   * 4、采购人员领取采购单(通知供应商或者自主采购)
   * 5、采购单入库
   * 6、添加库存
   * */

	@ApiOperation(value = "完成采购单", notes = "完成采购单")
	@PostMapping("/donePurchase")
	@SysLog("完成采购单")
	public R finishPurchase(@RequestBody PurchaseDoneVo doneVo){
		return R.ok(purchaseService.finishPurchase(doneVo));
	}

	@ApiOperation(value = "领取采购单", notes = "领取采购单")
	@PostMapping("/receive")
	@SysLog("领取采购单")
	public R receivePurchase(@RequestBody List<Long> ids){
		return R.ok(purchaseService.receivePurchase(ids));
	}

	@ApiOperation(value = "查询未领取采购单", notes = "查询未领取采购单")
	@GetMapping("/unreceive")
	@SysLog("查询未领取采购单")
	public R unreceivePurchase(SearchVO searchVO){
		return R.ok().setData(purchaseService.queryPageUnreceivePurchase(searchVO));
	}

	@ApiOperation(value = "合并采购需求", notes = "合并采购需求")
	@PostMapping("/merge")
	@SysLog("合并采购需求")
	public R mergePurchase(@RequestBody MergeVo mergeVo){
		return R.ok(purchaseService.mergePurchase(mergeVo));
	}


	@ApiOperation(value = "分页查询", notes = "分页查询")
	@GetMapping("/page" )
	@SysLog("分页查询")
//	@PreAuthorize("@pms.hasPermission('ware_wmspurchase_get')" )
	public R getWmsPurchasePage(SearchVO searchVO) {
		return R.ok(purchaseService.queryPage(searchVO));
	}

	@ApiOperation(value = "通过id查询", notes = "通过id查询")
	@GetMapping("/{id}" )
//	@PreAuthorize("@pms.hasPermission('ware_wmspurchase_get')" )
	public R getById(@PathVariable("id" ) Long id) {
		Purchase purchase = purchaseService.getById(id);
		WareInfo wareInfo = wareInfoService.getById(purchase.getWareId());
		if (wareInfo!=null) purchase.setWareName(wareInfo.getName());
		return R.ok(purchase);
	}

	@ApiOperation(value = "新增采购信息", notes = "新增采购信息")
	@SysLog("新增采购信息" )
	@PostMapping
//	@PreAuthorize("@pms.hasPermission('ware_wmspurchase_add')" )
	public R save(@RequestBody Purchase purchase) {
		return R.ok(purchaseService.save(purchase));
	}

	@ApiOperation(value = "修改采购信息", notes = "修改采购信息")
	@SysLog("修改采购信息" )
	@PutMapping
//	@PreAuthorize("@pms.hasPermission('ware_wmspurchase_edit')" )
	public R updateById(@RequestBody Purchase purchase) {
		purchase.setCreateTime(null).setUpdateTime(LocalDateTime.now());
		return R.ok(purchaseService.updateById(purchase));
	}

	/**
	 * 通过id删除采购信息
	 * @param id id
	 * @return R
	 */
	@ApiOperation(value = "通过id删除采购信息", notes = "通过id删除采购信息")
	@SysLog("通过id删除采购信息" )
	@DeleteMapping("/{id}" )
//	@PreAuthorize("@pms.hasPermission('ware_wmspurchase_del')" )
	public R removeById(@PathVariable Long id) {
		return R.ok(purchaseService.removeById(id));
	}


	@ApiOperation(value = "批量删除采购信息", notes = "批量删除采购信息")
	@SysLog("批量删除采购信息" )
	@DeleteMapping("/deleteBatch" )
//	@PreAuthorize("@pms.hasPermission('ware_wmspurchase_del')" )
	public R removeBatchById(@RequestBody Long[] Ids) {
		return R.ok(purchaseService.removeByIds(Arrays.asList(Ids)));
	}
}
