package com.mallcloud.mall.product.controller;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mallcloud.mall.common.core.util.R;
import com.mallcloud.mall.common.log.annotation.SysLog;
import com.mallcloud.mall.product.api.entity.Attr;
import com.mallcloud.mall.product.api.entity.AttrAttrgroupRelation;
import com.mallcloud.mall.product.api.entity.ProductAttrValue;
import com.mallcloud.mall.product.api.vo.AttrVO;
import com.mallcloud.mall.product.api.vo.SearchVO;
import com.mallcloud.mall.product.service.AttrAttrgroupRelationService;
import com.mallcloud.mall.product.service.AttrService;
import com.mallcloud.mall.product.service.ProductAttrValueService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 商品属性 前端控制器
 * </p>
 *
 * @author Painter
 * @since 2021-07-10
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/attr" )
@Api(value = "attr", tags = "商品属性管理")
public class AttrController {

	private final AttrService attrService;
	private final AttrAttrgroupRelationService relationService;
	private final ProductAttrValueService productAttrValueService;

	@ApiOperation(value = "分页查询", notes = "分页查询")
	@GetMapping("/page" )
	public R getAttrPage(Page page,Attr attr) {
		return R.ok(attrService.pageAll(page, Wrappers.query(attr)));
	}

	@ApiOperation(value = "根据分类id分页查询", notes = "根据分类id分页查询")
	@PostMapping("/pageByCatelogId" )
	public R getAttrPageByCatelogId(@RequestBody SearchVO searchVO) {
		return R.ok(attrService.pageByCatelogId(searchVO));
	}

	@ApiOperation(value = "通过attrGroupId查询", notes = "通过attrGroupId查询")
	@GetMapping("getAttrByAttrGroupId/{attrGroupId}")
	public R getAttrByAttrGroupId(@PathVariable("attrGroupId") Long attrGroupId) {
		return R.ok().setData( attrService.getRelationAttr(attrGroupId));
	}

	@ApiOperation(value = "条件分页查询", notes = "条件分页查询")
	@PostMapping("/page/search")
	public R getAttrPageSearch(@RequestBody SearchVO searchVO){
		return R.ok(attrService.queryPage(searchVO));
	}

	/**
	 * 通过id查询商品属性
	 * @param attrId id
	 * @return R
	 */
	@ApiOperation(value = "通过id查询", notes = "通过id查询")
	@GetMapping("/{attrId}" )
	public R getAttrById(@PathVariable("attrId" ) Long attrId) {
		return R.ok(attrService.getAttrById(attrId));
	}

	/**
	 * 通过id查询商品属性
	 *  attrType base/sale
	 *  attrId id
	 * @return R
	 */
	@ApiOperation(value = "通过attrType和catelogId查询", notes = "通过attrType和catelogId查询")
	@GetMapping("/{attrType}/list/{catelogId}")
	public R getBSAttrList(SearchVO searchVO,
						  @PathVariable("catelogId") Long catelogId,
						  @PathVariable("attrType")String type){
		return R.ok().setData(attrService.queryBaseAttrPage(searchVO,catelogId,type));
	}

	@ApiOperation(value = "删除商品属性与分组参数关联", notes = "删除商品属性与分组参数关联")
	@SysLog("删除商品属性与分组参数关联" )
	@DeleteMapping("deleteRelation/{attrId}/{attrGroupId}" )
	public R removeAttrRelation(@PathVariable Long attrId,@PathVariable Long attrGroupId) {
		return R.ok(relationService.remove(Wrappers.<AttrAttrgroupRelation>lambdaQuery()
					.eq(AttrAttrgroupRelation::getAttrId,attrId)
					.eq(AttrAttrgroupRelation::getAttrGroupId,attrGroupId)));
	}


	@ApiOperation(value = "新增商品属性", notes = "新增商品属性")
	@SysLog("新增商品属性" )
	@PostMapping
	public R save(@RequestBody AttrVO attrVO) {
		return R.ok(attrService.saveAttr(attrVO));
	}

	@ApiOperation(value = "修改spu属性", notes = "修改spu属性")
	@SysLog("修改spu属性" )
	@PutMapping("/update/{spuId}")
	public R updateSpuAttr(@PathVariable("spuId") Long spuId,
						   @RequestBody List<ProductAttrValue> entities) {
		return R.ok(productAttrValueService.updateSpuAttr(spuId,entities));
	}

	@ApiOperation(value = "修改商品属性", notes = "修改商品属性")
	@SysLog("修改商品属性" )
	@PutMapping
	public R updateById(@RequestBody AttrVO attrVO) {
		return R.ok(attrService.updateAttrById(attrVO));
	}


	@ApiOperation(value = "通过id删除商品属性", notes = "通过id删除商品属性")
	@SysLog("通过id删除商品属性" )
	@DeleteMapping("/{attrId}" )
	public R removeById(@PathVariable Long attrId) {
		return R.ok(attrService.removeById(attrId));
	}


	@ApiOperation(value = "批量删除商品属性", notes = "批量删除商品属性")
	@SysLog("批量删除商品属性" )
	@DeleteMapping("/deleteBatch")
	public R removeBatch(@RequestBody Long[] ids) {
		return R.ok(attrService.removeByIds(Arrays.asList(ids)));
	}

}
