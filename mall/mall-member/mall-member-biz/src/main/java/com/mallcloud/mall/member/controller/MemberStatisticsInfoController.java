package com.mallcloud.mall.member.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mallcloud.mall.member.api.entity.MemberStatisticsInfo;
import com.mallcloud.mall.member.service.MemberStatisticsInfoService;
import com.mallcloud.mall.common.core.util.R;
import com.mallcloud.mall.common.log.annotation.SysLog;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 会员统计信息
 *
 * @author painter
 * @date 2021-08-29 10:53:24
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/memberStatisticsInfo" )
@Api(value = "memberStatisticsInfo", tags = "会员统计信息管理")
public class MemberStatisticsInfoController {

    private final MemberStatisticsInfoService memberStatisticsInfoService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param memberStatisticsInfo 会员统计信息
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
//    @PreAuthorize("@pms.hasPermission('member_umsmemberstatisticsinfo_get')" )
    public R getUmsMemberStatisticsInfoPage(Page page, MemberStatisticsInfo memberStatisticsInfo) {
        return R.ok(memberStatisticsInfoService.page(page, Wrappers.query(memberStatisticsInfo)));
    }


    /**
     * 通过id查询会员统计信息
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
//    @PreAuthorize("@pms.hasPermission('member_umsmemberstatisticsinfo_get')" )
    public R getById(@PathVariable("id" ) Long id) {
        return R.ok(memberStatisticsInfoService.getById(id));
    }

    /**
     * 新增会员统计信息
     * @param memberStatisticsInfo 会员统计信息
     * @return R
     */
    @ApiOperation(value = "新增会员统计信息", notes = "新增会员统计信息")
    @SysLog("新增会员统计信息" )
    @PostMapping
//    @PreAuthorize("@pms.hasPermission('member_umsmemberstatisticsinfo_add')" )
    public R save(@RequestBody MemberStatisticsInfo memberStatisticsInfo) {
        return R.ok(memberStatisticsInfoService.save(memberStatisticsInfo));
    }

    /**
     * 修改会员统计信息
     * @param memberStatisticsInfo 会员统计信息
     * @return R
     */
    @ApiOperation(value = "修改会员统计信息", notes = "修改会员统计信息")
    @SysLog("修改会员统计信息" )
    @PutMapping
//    @PreAuthorize("@pms.hasPermission('member_umsmemberstatisticsinfo_edit')" )
    public R updateById(@RequestBody MemberStatisticsInfo memberStatisticsInfo) {
        return R.ok(memberStatisticsInfoService.updateById(memberStatisticsInfo));
    }

    /**
     * 通过id删除会员统计信息
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除会员统计信息", notes = "通过id删除会员统计信息")
    @SysLog("通过id删除会员统计信息" )
    @DeleteMapping("/{id}" )
//    @PreAuthorize("@pms.hasPermission('member_umsmemberstatisticsinfo_del')" )
    public R removeById(@PathVariable Long id) {
        return R.ok(memberStatisticsInfoService.removeById(id));
    }

}
