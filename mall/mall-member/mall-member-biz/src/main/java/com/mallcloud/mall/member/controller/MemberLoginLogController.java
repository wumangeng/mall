package com.mallcloud.mall.member.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mallcloud.mall.member.api.entity.MemberLoginLog;
import com.mallcloud.mall.member.service.MemberLoginLogService;
import com.mallcloud.mall.common.core.util.R;
import com.mallcloud.mall.common.log.annotation.SysLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 会员登录记录
 *
 * @author painter
 * @date 2021-08-29 10:53:23
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/memberLoginLog" )
@Api(value = "memberLoginLog", tags = "会员登录记录管理")
public class MemberLoginLogController {

    private final MemberLoginLogService memberLoginLogService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param memberLoginLog 会员登录记录
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
//    @PreAuthorize("@pms.hasPermission('member_umsmemberloginlog_get')" )
    public R getUmsMemberLoginLogPage(Page page, MemberLoginLog memberLoginLog) {
        return R.ok(memberLoginLogService.page(page, Wrappers.query(memberLoginLog)));
    }


    /**
     * 通过id查询会员登录记录
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
//    @PreAuthorize("@pms.hasPermission('member_umsmemberloginlog_get')" )
    public R getById(@PathVariable("id" ) Long id) {
        return R.ok(memberLoginLogService.getById(id));
    }

    /**
     * 新增会员登录记录
     * @param memberLoginLog 会员登录记录
     * @return R
     */
    @ApiOperation(value = "新增会员登录记录", notes = "新增会员登录记录")
    @SysLog("新增会员登录记录" )
    @PostMapping
//    @PreAuthorize("@pms.hasPermission('member_umsmemberloginlog_add')" )
    public R save(@RequestBody MemberLoginLog memberLoginLog) {
        return R.ok(memberLoginLogService.save(memberLoginLog));
    }

    /**
     * 修改会员登录记录
     * @param memberLoginLog 会员登录记录
     * @return R
     */
    @ApiOperation(value = "修改会员登录记录", notes = "修改会员登录记录")
    @SysLog("修改会员登录记录" )
    @PutMapping
//    @PreAuthorize("@pms.hasPermission('member_umsmemberloginlog_edit')" )
    public R updateById(@RequestBody MemberLoginLog memberLoginLog) {
        return R.ok(memberLoginLogService.updateById(memberLoginLog));
    }

    /**
     * 通过id删除会员登录记录
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除会员登录记录", notes = "通过id删除会员登录记录")
    @SysLog("通过id删除会员登录记录" )
    @DeleteMapping("/{id}" )
//    @PreAuthorize("@pms.hasPermission('member_umsmemberloginlog_del')" )
    public R removeById(@PathVariable Long id) {
        return R.ok(memberLoginLogService.removeById(id));
    }

}
