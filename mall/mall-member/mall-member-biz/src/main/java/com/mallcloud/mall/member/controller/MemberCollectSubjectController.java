package com.mallcloud.mall.member.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mallcloud.mall.member.api.entity.MemberCollectSubject;
import com.mallcloud.mall.member.service.MemberCollectSubjectService;
import com.mallcloud.mall.common.core.util.R;
import com.mallcloud.mall.common.log.annotation.SysLog;

import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 会员收藏的专题活动
 *
 * @author painter
 * @date 2021-08-29 10:53:23
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/memberCollectSubject" )
@Api(value = "memberCollectSubject", tags = "会员收藏的专题活动管理")
public class MemberCollectSubjectController {

    private final MemberCollectSubjectService memberCollectSubjectService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param MemberCollectSubject 会员收藏的专题活动
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
//    @PreAuthorize("@pms.hasPermission('member_umsmembercollectsubject_get')" )
    public R getUmsMemberCollectSubjectPage(Page page, MemberCollectSubject MemberCollectSubject) {
        return R.ok(memberCollectSubjectService.page(page, Wrappers.query(MemberCollectSubject)));
    }


    /**
     * 通过id查询会员收藏的专题活动
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
//    @PreAuthorize("@pms.hasPermission('member_umsmembercollectsubject_get')" )
    public R getById(@PathVariable("id" ) Long id) {
        return R.ok(memberCollectSubjectService.getById(id));
    }

    /**
     * 新增会员收藏的专题活动
     * @param MemberCollectSubject 会员收藏的专题活动
     * @return R
     */
    @ApiOperation(value = "新增会员收藏的专题活动", notes = "新增会员收藏的专题活动")
    @SysLog("新增会员收藏的专题活动" )
    @PostMapping
//    @PreAuthorize("@pms.hasPermission('member_umsmembercollectsubject_add')" )
    public R save(@RequestBody MemberCollectSubject MemberCollectSubject) {
        return R.ok(memberCollectSubjectService.save(MemberCollectSubject));
    }

    /**
     * 修改会员收藏的专题活动
     * @param MemberCollectSubject 会员收藏的专题活动
     * @return R
     */
    @ApiOperation(value = "修改会员收藏的专题活动", notes = "修改会员收藏的专题活动")
    @SysLog("修改会员收藏的专题活动" )
    @PutMapping
//    @PreAuthorize("@pms.hasPermission('member_umsmembercollectsubject_edit')" )
    public R updateById(@RequestBody MemberCollectSubject MemberCollectSubject) {
        return R.ok(memberCollectSubjectService.updateById(MemberCollectSubject));
    }

    /**
     * 通过id删除会员收藏的专题活动
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除会员收藏的专题活动", notes = "通过id删除会员收藏的专题活动")
    @SysLog("通过id删除会员收藏的专题活动" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('member_umsmembercollectsubject_del')" )
    public R removeById(@PathVariable Long id) {
        return R.ok(memberCollectSubjectService.removeById(id));
    }

}
