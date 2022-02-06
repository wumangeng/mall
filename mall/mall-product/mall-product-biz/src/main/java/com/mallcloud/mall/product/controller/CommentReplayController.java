package com.mallcloud.mall.product.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mallcloud.mall.common.core.util.R;
import com.mallcloud.mall.common.log.annotation.SysLog;
import com.mallcloud.mall.product.api.entity.CommentReplay;
import com.mallcloud.mall.product.service.CommentReplayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 商品评价回复关系
 *
 * @author painter
 * @date 2021-08-27 17:09:10
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/commentreplay" )
@Api(value = "commentreplay", tags = "商品评价回复关系管理")
public class CommentReplayController {

	private final  CommentReplayService commentReplayService;

	/**
	 * 分页查询
	 * @param page 分页对象
	 * @param pmsCommentReplay 商品评价回复关系
	 * @return
	 */
	@ApiOperation(value = "分页查询", notes = "分页查询")
	@GetMapping("/page" )
//	@PreAuthorize("@pms.hasPermission('product_pmscommentreplay_get')" )
	public R getPmsCommentReplayPage(Page page, CommentReplay pmsCommentReplay) {
		return R.ok(commentReplayService.page(page, Wrappers.query(pmsCommentReplay)));
	}


	/**
	 * 通过id查询商品评价回复关系
	 * @param id id
	 * @return R
	 */
	@ApiOperation(value = "通过id查询", notes = "通过id查询")
	@GetMapping("/{id}" )
//	@PreAuthorize("@pms.hasPermission('product_pmscommentreplay_get')" )
	public R getById(@PathVariable("id" ) Long id) {
		return R.ok(commentReplayService.getById(id));
	}

	/**
	 * 新增商品评价回复关系
	 * @param pmsCommentReplay 商品评价回复关系
	 * @return R
	 */
	@ApiOperation(value = "新增商品评价回复关系", notes = "新增商品评价回复关系")
	@SysLog("新增商品评价回复关系" )
	@PostMapping
//	@PreAuthorize("@pms.hasPermission('product_pmscommentreplay_add')" )
	public R save(@RequestBody CommentReplay pmsCommentReplay) {
		return R.ok(commentReplayService.save(pmsCommentReplay));
	}

	/**
	 * 修改商品评价回复关系
	 * @param pmsCommentReplay 商品评价回复关系
	 * @return R
	 */
	@ApiOperation(value = "修改商品评价回复关系", notes = "修改商品评价回复关系")
	@SysLog("修改商品评价回复关系" )
	@PutMapping
//	@PreAuthorize("@pms.hasPermission('product_pmscommentreplay_edit')" )
	public R updateById(@RequestBody CommentReplay pmsCommentReplay) {
		return R.ok(commentReplayService.updateById(pmsCommentReplay));
	}

	/**
	 * 通过id删除商品评价回复关系
	 * @param id id
	 * @return R
	 */
	@ApiOperation(value = "通过id删除商品评价回复关系", notes = "通过id删除商品评价回复关系")
	@SysLog("通过id删除商品评价回复关系" )
	@DeleteMapping("/{id}" )
//	@PreAuthorize("@pms.hasPermission('product_pmscommentreplay_del')" )
	public R removeById(@PathVariable Long id) {
		return R.ok(commentReplayService.removeById(id));
	}

}
