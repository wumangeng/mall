package com.mallcloud.mall.product.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mallcloud.mall.common.core.util.R;
import com.mallcloud.mall.common.log.annotation.SysLog;
import com.mallcloud.mall.product.api.entity.Brand;
import com.mallcloud.mall.product.api.vo.BrandSearchVO;
import com.mallcloud.mall.product.service.BrandService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 品牌 前端控制器
 * </p>
 *
 * @author Painter
 * @since 2021-07-10
 */
@RestController
@RequestMapping("/brand")
@Api(tags = "品牌管理")
public class BrandController {

	@Autowired
	private BrandService brandService;

	/**
	 * 分页查询
	 * @param page 分页对象
	 * @param brand 品牌
	 * @return
	 */
	@ApiOperation(value = "分页查询", notes = "分页查询")
	@GetMapping("/page" )
	public R getBrandPage(Page page, Brand brand) {
		return R.ok(brandService.page(page, Wrappers.query(brand)));
	}

	@ApiOperation(value = "条件分页查询", notes = "分页查询")
	@PostMapping("/searchPage" )
	public R getBrandSearchPage(@RequestBody BrandSearchVO brandSearchVO) {
		Page<Brand> brandPage;
		if (brandSearchVO!=null){
			brandPage = brandService.page(new Page<>(brandSearchVO.getCurrent(), brandSearchVO.getSize()),
					new LambdaQueryWrapper<Brand>().eq(Brand::getName, brandSearchVO.getName()));
		}else {
			brandPage = brandService.page(new Page<>(brandSearchVO.getCurrent(), brandSearchVO.getSize()));
		}
		return R.ok(brandPage);
	}


	/**
	 * 通过id查询品牌
	 * @param brandId id
	 * @return R
	 */
	@ApiOperation(value = "通过id查询", notes = "通过id查询")
	@GetMapping("/{brandId}" )
	public R getById(@PathVariable("brandId" ) Long brandId) {
		return R.ok(brandService.getById(brandId));
	}

	/**
	 * 新增品牌
	 * @param brand 品牌
	 * @return R
	 */
	@ApiOperation(value = "新增品牌", notes = "新增品牌")
	@SysLog("新增品牌" )
	@PostMapping
	public R save(@RequestBody Brand brand) {
		return R.ok(brandService.save(brand));
	}

	/**
	 * 修改品牌
	 * @param brand 品牌
	 * @return R
	 */
	@ApiOperation(value = "修改品牌", notes = "修改品牌")
	@SysLog("修改品牌" )
	@PutMapping
	public R updateById(@RequestBody Brand brand) {
		boolean result = brandService.updateDetail(brand);
		return R.ok(result);
	}

	/**
	 * 通过id删除品牌
	 * @param brandId id
	 * @return R
	 */
	@ApiOperation(value = "通过id删除品牌", notes = "通过id删除品牌")
	@SysLog("通过id删除品牌" )
	@DeleteMapping("/{brandId}" )
	public R removeById(@PathVariable Long brandId) {
		return R.ok(brandService.removeById(brandId));
	}


}
