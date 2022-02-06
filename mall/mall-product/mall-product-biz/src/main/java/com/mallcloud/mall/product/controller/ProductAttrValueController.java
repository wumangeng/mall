package com.mallcloud.mall.product.controller;


import com.mallcloud.mall.product.api.entity.ProductAttrValue;
import com.mallcloud.mall.product.api.vo.SearchVO;
import com.mallcloud.mall.product.service.AttrService;
import com.mallcloud.mall.product.service.ProductAttrValueService;
import com.mallcloud.mall.common.core.util.R;
import com.mallcloud.mall.common.log.annotation.SysLog;
import com.mallcloud.mall.product.api.vo.AttrResVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * <p>
 * spu属性值 前端控制器
 * </p>
 *
 * @author Painter
 * @since 2021-07-10
 */
@RestController
@RequestMapping("/attrValue")
@RequiredArgsConstructor
@Api(value = "ProductAttrValue", tags = "商品(spu)属性值")
public class ProductAttrValueController {

	private final ProductAttrValueService productAttrValueService;
	private final AttrService attrService;

	@ApiOperation(value = "通过spuId查询", notes = "通过spuId查询")
	@SysLog("通过spuId查询" )
	@GetMapping("/getProductAttrValueBySpuId/{spuId}")
	public R getProductAttrValueBySpuId(@PathVariable("spuId") Long spuId){
		return R.ok().setData(productAttrValueService.getProductAttrValueBySpuId(spuId));
	}

	@ApiOperation(value = "条件分页查询", notes = "条件分页查询")
	@SysLog("条件分页查询" )
	@RequestMapping("/list/search")
	public R searchList(@RequestParam SearchVO searchVO){
		return R.ok().setData(productAttrValueService.queryPage(searchVO));
	}


	@ApiOperation(value = "通过id查询spu属性值", notes = "通过id查询spu属性值")
	@SysLog("通过id查询spu属性值" )
	@GetMapping("/{id}")
	public R getById(@PathVariable("id") Long id){
		return R.ok().setData(productAttrValueService.getById(id));
	}

	@ApiOperation(value = "添加spu属性值", notes = "添加spu属性值")
	@SysLog("添加spu属性值" )
	@PostMapping("/save")
	public R save(@RequestBody ProductAttrValue productAttrValue){
		if (productAttrValue.getAttrId()!=null){
			AttrResVO attr = attrService.getAttrById(productAttrValue.getAttrId());
			productAttrValue.setAttrName(attr.getAttrName());
		}
		return R.ok(productAttrValueService.save(productAttrValue));
	}


	@ApiOperation(value = "修改spu属性值", notes = "修改spu属性值")
	@SysLog("修改spu属性值" )
	@PutMapping
	public R update(@RequestBody ProductAttrValue productAttrValue){
		return R.ok(productAttrValueService.updateById(productAttrValue));
	}


	@ApiOperation(value = "通过id删除spu属性值", notes = "通过id删除spu属性值")
	@SysLog("通过id删除spu属性值" )
	@DeleteMapping("/delete")
	public R delete(@RequestBody Long[] ids){
		return R.ok(productAttrValueService.removeByIds(Arrays.asList(ids)));
	}
}
