package com.mallcloud.mall.product.controller;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.mallcloud.mall.product.api.entity.AttrGroup;
import com.mallcloud.mall.product.vo.AttrGroupSearchVO;
import com.mallcloud.mall.product.api.vo.SearchVO;
import com.mallcloud.mall.product.service.AttrGroupService;
import com.mallcloud.mall.product.service.AttrService;
import com.mallcloud.mall.product.service.CategoryService;
import com.mallcloud.mall.common.core.util.R;
import com.mallcloud.mall.common.log.annotation.SysLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 属性分组 前端控制器
 * </p>
 *
 * @author Painter
 * @since 2021-07-10
 */
@RestController
@RequestMapping("/attrGroup")
@Api(tags = "属性分组管理")
public class AttrGroupController {

	@Autowired
	private AttrGroupService attrGroupService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private AttrService attrService;


	@ApiOperation(value = "通过catelogId查询", notes = "通过catelogId查询")
	@GetMapping("/getAttrGroupByCatelogId/{catelogId}")
	public R getAttrGroupByCatelogId(@PathVariable("catelogId") Long catelogId) {
		return R.ok().setData( attrGroupService.list(Wrappers.<AttrGroup>lambdaQuery().eq(AttrGroup::getCatelogId,catelogId)));
	}

	@ApiOperation(value = "条件分页查询", notes = "条件分页查询")
	@GetMapping("/search")
	public R getAttrGroupPage(AttrGroupSearchVO searchVO){
		return R.ok(attrGroupService.queryPage(searchVO));
	}

	@ApiOperation(value = "通过id查询", notes = "通过id查询")
	@GetMapping("/{attrGroupId}" )
	public R getAttrGroupById(@PathVariable("attrGroupId" ) Long attrGroupId) {
		AttrGroup attrGroup = attrGroupService.getById(attrGroupId);
		Long[] catelogPath=categoryService.getCatelogPath( attrGroup.getCatelogId());
		attrGroup.setCatelogIdPath(catelogPath);
		attrGroup.setCatelogName(categoryService.getById(attrGroup.getCatelogId()).getName());
		return R.ok(attrGroup);
	}

	@ApiOperation(value = "通过attrGroupId查询 未关联的分组", notes = "通过attrGroupId查询 未关联的分组")
	@GetMapping("/{attrGroupId}/noattr/relation")
	public R attrNoRelation(@PathVariable("attrGroupId") Long attrGroupId,
							@RequestParam SearchVO searchVO) {
		return R.ok().setData(attrService.getNoRelationAttr(searchVO, attrGroupId));
	}

	@ApiOperation(value = "查出当前分类下的所有属性分组 及属性分组下的所有属性", notes = "查出当前分类下的所有属性分组 及属性分组下的所有属性")
	@GetMapping("/{catelogId}/withattr")
	public R getAttrGroupWithAttrs(@PathVariable("catelogId") Long catelogId) {
		return R.ok(attrGroupService.getAttrGroupWithAttrsByCatelogId(catelogId));
	}


	@ApiOperation(value = "新增属性分组", notes = "新增属性分组")
	@SysLog("新增属性分组" )
	@PostMapping
	public R save(@Valid @RequestBody AttrGroup attrGroup, BindingResult result) {
		//校验参数
		if (result.hasErrors()){
			Map<String, String> map = new HashMap<>();
			result.getFieldErrors().forEach((item)->{
				String field = item.getField();
				String message = item.getDefaultMessage();
				map.put(field,message);
			});
			return R.failed().setCode(400).setData(map).setMsg("参数不合法！");
		}
		return R.ok(attrGroupService.save(attrGroup));
	}

	@ApiOperation(value = "修改属性分组", notes = "修改属性分组")
	@SysLog("修改属性分组" )
	@PutMapping
	public R updateById(@RequestBody AttrGroup attrGroup) {
		return R.ok(attrGroupService.updateById(attrGroup));
	}

	@ApiOperation(value = "通过id删除属性分组", notes = "通过id删除属性分组")
	@SysLog("通过id删除属性分组" )
	@DeleteMapping("/{attrGroupId}" )
	public R removeById(@PathVariable Long attrGroupId) {
		return R.ok(attrGroupService.removeById(attrGroupId));
	}

}
