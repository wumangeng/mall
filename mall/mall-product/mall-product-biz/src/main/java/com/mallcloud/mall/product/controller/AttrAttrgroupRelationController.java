package com.mallcloud.mall.product.controller;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mallcloud.mall.product.api.entity.AttrAttrgroupRelation;
import com.mallcloud.mall.product.service.AttrAttrgroupRelationService;
import com.mallcloud.mall.common.core.util.R;
import com.mallcloud.mall.common.log.annotation.SysLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 属性&属性分组关联 前端控制器
 * </p>
 *
 * @author Painter
 * @since 2021-07-10
 */
@RestController
@RequestMapping("/attrattrgrouprelation")
@Api(value = "attrattrgrouprelation", tags = "属性&属性分组关联管理")
public class AttrAttrgroupRelationController {

	@Autowired
	private AttrAttrgroupRelationService attrAttrgroupRelationService;

	/**
	 * 分页查询
	 * @param page 分页对象
	 * @param attrAttrgroupRelation 属性&属性分组关联
	 * @return
	 */
	@ApiOperation(value = "分页查询", notes = "分页查询")
	@GetMapping("/page" )
	public R getAttrAttrgroupRelationPage(Page page, AttrAttrgroupRelation attrAttrgroupRelation) {
		return R.ok(attrAttrgroupRelationService.page(page, Wrappers.query(attrAttrgroupRelation)));
	}


	/**
	 * 通过id查询属性&属性分组关联
	 * @param id id
	 * @return R
	 */
	@ApiOperation(value = "通过id查询", notes = "通过id查询")
	@GetMapping("/{id}" )
	public R getById(@PathVariable("id" ) Long id) {
		return R.ok(attrAttrgroupRelationService.getById(id));
	}

	/**
	 * 新增属性&属性分组关联
	 * @param attrAttrgroupRelation 属性&属性分组关联
	 * @return R
	 */
	@ApiOperation(value = "新增属性&属性分组关联", notes = "新增属性&属性分组关联")
	@SysLog("新增属性&属性分组关联" )
	@PostMapping
	public R save(@RequestBody AttrAttrgroupRelation attrAttrgroupRelation) {
		return R.ok(attrAttrgroupRelationService.save(attrAttrgroupRelation));
	}

	/**
	 * 修改属性&属性分组关联
	 * @param attrAttrgroupRelation 属性&属性分组关联
	 * @return R
	 */
	@ApiOperation(value = "修改属性&属性分组关联", notes = "修改属性&属性分组关联")
	@SysLog("修改属性&属性分组关联" )
	@PutMapping
	public R updateById(@RequestBody AttrAttrgroupRelation attrAttrgroupRelation) {
		return R.ok(attrAttrgroupRelationService.updateById(attrAttrgroupRelation));
	}

	/**
	 * 通过id删除属性&属性分组关联
	 * @param id id
	 * @return R
	 */
	@ApiOperation(value = "通过id删除属性&属性分组关联", notes = "通过id删除属性&属性分组关联")
	@SysLog("通过id删除属性&属性分组关联" )
	@DeleteMapping("/{id}" )
	public R removeById(@PathVariable Long id) {
		return R.ok(attrAttrgroupRelationService.removeById(id));
	}

}
