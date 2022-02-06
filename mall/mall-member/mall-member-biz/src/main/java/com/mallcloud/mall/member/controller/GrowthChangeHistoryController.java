
package com.mallcloud.mall.member.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mallcloud.mall.common.log.annotation.SysLog;
import com.mallcloud.mall.member.api.entity.GrowthChangeHistory;
import com.mallcloud.mall.common.core.util.R;
import com.mallcloud.mall.member.service.GrowthChangeHistoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 成长值变化历史记录
 *
 * @author painter
 * @date 2021-08-29 10:53:24
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/growthChangeHistory" )
@Api(value = "growthChangeHistory", tags = "成长值变化历史记录管理")
public class GrowthChangeHistoryController {

    private final  GrowthChangeHistoryService growthChangeHistoryService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param growthChangeHistory 成长值变化历史记录
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
//    @PreAuthorize("@pms.hasPermission('member_umsgrowthchangehistory_get')" )
    public R getUmsGrowthChangeHistoryPage(Page page, GrowthChangeHistory growthChangeHistory) {
        return R.ok(growthChangeHistoryService.page(page, Wrappers.query(growthChangeHistory)));
    }


    /**
     * 通过id查询成长值变化历史记录
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
//    @PreAuthorize("@pms.hasPermission('member_umsgrowthchangehistory_get')" )
    public R getById(@PathVariable("id" ) Long id) {
        return R.ok(growthChangeHistoryService.getById(id));
    }

    /**
     * 新增成长值变化历史记录
     * @param growthChangeHistory 成长值变化历史记录
     * @return R
     */
    @ApiOperation(value = "新增成长值变化历史记录", notes = "新增成长值变化历史记录")
    @SysLog("新增成长值变化历史记录" )
    @PostMapping
//    @PreAuthorize("@pms.hasPermission('member_umsgrowthchangehistory_add')" )
    public R save(@RequestBody GrowthChangeHistory growthChangeHistory) {
        return R.ok(growthChangeHistoryService.save(growthChangeHistory));
    }

    /**
     * 修改成长值变化历史记录
     * @param growthChangeHistory 成长值变化历史记录
     * @return R
     */
    @ApiOperation(value = "修改成长值变化历史记录", notes = "修改成长值变化历史记录")
    @SysLog("修改成长值变化历史记录" )
    @PutMapping
//    @PreAuthorize("@pms.hasPermission('member_umsgrowthchangehistory_edit')" )
    public R updateById(@RequestBody GrowthChangeHistory growthChangeHistory) {
        return R.ok(growthChangeHistoryService.updateById(growthChangeHistory));
    }

    /**
     * 通过id删除成长值变化历史记录
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除成长值变化历史记录", notes = "通过id删除成长值变化历史记录")
    @SysLog("通过id删除成长值变化历史记录" )
    @DeleteMapping("/{id}" )
//    @PreAuthorize("@pms.hasPermission('member_umsgrowthchangehistory_del')" )
    public R removeById(@PathVariable Long id) {
        return R.ok(growthChangeHistoryService.removeById(id));
    }

}
