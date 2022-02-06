/*
 *    Copyright (c) 2018-2025, lengleng All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * Neither the name of the pig4cloud.com developer nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 * Author: lengleng (wangiegie@gmail.com)
 */

package com.mallcloud.mall.member.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mallcloud.mall.member.api.entity.IntegrationChangeHistory;
import com.mallcloud.mall.member.service.IntegrationChangeHistoryService;
import com.mallcloud.mall.common.core.util.R;
import com.mallcloud.mall.common.log.annotation.SysLog;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 积分变化历史记录
 *
 * @author painter
 * @date 2021-08-29 10:53:24
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/integrationChangeHistory" )
@Api(value = "integrationChangeHistory", tags = "积分变化历史记录管理")
public class IntegrationChangeHistoryController {

    private final IntegrationChangeHistoryService integrationChangeHistoryService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param IntegrationChangeHistory 积分变化历史记录
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    @PreAuthorize("@pms.hasPermission('member_umsintegrationchangehistory_get')" )
    public R getUmsIntegrationChangeHistoryPage(Page page, IntegrationChangeHistory IntegrationChangeHistory) {
        return R.ok(integrationChangeHistoryService.page(page, Wrappers.query(IntegrationChangeHistory)));
    }


    /**
     * 通过id查询积分变化历史记录
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('member_umsintegrationchangehistory_get')" )
    public R getById(@PathVariable("id" ) Long id) {
        return R.ok(integrationChangeHistoryService.getById(id));
    }

    /**
     * 新增积分变化历史记录
     * @param IntegrationChangeHistory 积分变化历史记录
     * @return R
     */
    @ApiOperation(value = "新增积分变化历史记录", notes = "新增积分变化历史记录")
    @SysLog("新增积分变化历史记录" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('member_umsintegrationchangehistory_add')" )
    public R save(@RequestBody IntegrationChangeHistory IntegrationChangeHistory) {
        return R.ok(integrationChangeHistoryService.save(IntegrationChangeHistory));
    }

    /**
     * 修改积分变化历史记录
     * @param IntegrationChangeHistory 积分变化历史记录
     * @return R
     */
    @ApiOperation(value = "修改积分变化历史记录", notes = "修改积分变化历史记录")
    @SysLog("修改积分变化历史记录" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('member_umsintegrationchangehistory_edit')" )
    public R updateById(@RequestBody IntegrationChangeHistory IntegrationChangeHistory) {
        return R.ok(integrationChangeHistoryService.updateById(IntegrationChangeHistory));
    }

    /**
     * 通过id删除积分变化历史记录
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除积分变化历史记录", notes = "通过id删除积分变化历史记录")
    @SysLog("通过id删除积分变化历史记录" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('member_umsintegrationchangehistory_del')" )
    public R removeById(@PathVariable Long id) {
        return R.ok(integrationChangeHistoryService.removeById(id));
    }

}
