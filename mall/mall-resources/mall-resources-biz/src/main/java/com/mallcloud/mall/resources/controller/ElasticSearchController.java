package com.mallcloud.mall.resources.controller;

import com.mallcloud.mall.common.core.util.R;
import com.mallcloud.mall.resources.api.entity.SkuEsModel;
import com.mallcloud.mall.resources.api.vo.ProSearchParam;
import com.mallcloud.mall.resources.service.ElasticSearchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/elasticSearch")
@Api(tags ="ES数据管理接口")
public class ElasticSearchController {

	@Autowired
	private ElasticSearchService searchService;

	@ApiOperation(value = "ES添加商品上架信息", notes = "ES添加商品上架信息")
	@PostMapping("/addUpSpu")
	public R saveUpSpu(@RequestBody List<SkuEsModel> skuEsModelList) {
		try {
			boolean addUpSpu = searchService.addUpSpu(skuEsModelList);
			return R.ok(addUpSpu);
		} catch (IOException e) {
			return R.failed(false);
		}
	}

	@ApiOperation(value = "ES检索商品", notes = "ES检索商品")
	@GetMapping("/search")
	public R searchProduct(ProSearchParam param) {
		return R.ok( searchService.searchPro(param));
	}



}
