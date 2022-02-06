package com.mallcloud.mall.member.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mallcloud.mall.member.api.entity.MemberLevel;
import com.mallcloud.mall.member.entity.vo.SearchVO;
import com.mallcloud.mall.member.service.MemberLevelService;
import com.mallcloud.mall.common.core.util.R;
import com.mallcloud.mall.common.log.annotation.SysLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;


/**
 * 会员等级
 *
 * @author painter
 * @date 2021-08-29 10:53:23
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/memberLevel" )
@Api(value = "memberLevel", tags = "会员等级管理")
public class MemberLevelController {

    private final MemberLevelService memberLevelService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param memberLevel 会员等级
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
//    @PreAuthorize("@pms.hasPermission('member_umsmemberlevel_get')" )
    public R getUmsMemberLevelPage(Page page, MemberLevel memberLevel) {
        return R.ok(memberLevelService.page(page, Wrappers.query(memberLevel)));
    }

	@ApiOperation(value = "条件分页查询", notes = "条件分页查询")
	@GetMapping("/page/search")
	public R getMemberPageSearch(SearchVO searchVO){
		return R.ok(memberLevelService.querySearchPage(searchVO));
	}


    /**
     * 通过id查询会员等级
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
//    @PreAuthorize("@pms.hasPermission('member_umsmemberlevel_get')" )
    public R getById(@PathVariable("id" ) Long id) {
        return R.ok(memberLevelService.getById(id));
    }

    /**
     * 新增会员等级
     * @param memberLevel 会员等级
     * @return R
     */
    @ApiOperation(value = "新增会员等级", notes = "新增会员等级")
    @SysLog("新增会员等级" )
    @PostMapping
//    @PreAuthorize("@pms.hasPermission('member_umsmemberlevel_add')" )
    public R save(@RequestBody MemberLevel memberLevel) {
        return R.ok(memberLevelService.save(memberLevel));
    }

    /**
     * 修改会员等级
     * @param memberLevel 会员等级
     * @return R
     */
    @ApiOperation(value = "修改会员等级", notes = "修改会员等级")
    @SysLog("修改会员等级" )
    @PutMapping
//    @PreAuthorize("@pms.hasPermission('member_umsmemberlevel_edit')" )
    public R updateById(@RequestBody MemberLevel memberLevel) {
        return R.ok(memberLevelService.updateById(memberLevel));
    }

    /**
     * 通过id删除会员等级
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除会员等级", notes = "通过id删除会员等级")
    @SysLog("通过id删除会员等级" )
    @DeleteMapping("/{id}" )
//    @PreAuthorize("@pms.hasPermission('member_umsmemberlevel_del')" )
    public R removeById(@PathVariable Long id) {
        return R.ok(memberLevelService.removeById(id));
    }

	@ApiOperation(value = "通过id批量删除会员等级", notes = "通过id批量删除会员等级")
	@SysLog("通过id批量删除会员等级" )
	@DeleteMapping("/deleteBatch")
//	  @PreAuthorize("@pms.hasPermission('member_umsmemberlevel_delbatch')" )
	public R deleteBatch(@RequestBody Long[] ids){
		return R.ok(memberLevelService.removeByIds(Arrays.asList(ids)));
	}

}
