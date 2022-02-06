
package com.mallcloud.mall.member.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mallcloud.mall.member.api.entity.MemberCollectSpu;
import com.mallcloud.mall.member.service.MemberCollectSpuService;
import com.mallcloud.mall.common.core.util.R;
import com.mallcloud.mall.common.log.annotation.SysLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 会员收藏的商品
 *
 * @author painter
 * @date 2021-08-29 10:53:23
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/memberCollectSpu" )
@Api(value = "memberCollectSpu", tags = "会员收藏的商品管理")
public class MemberCollectSpuController {

    private final MemberCollectSpuService memberCollectSpuService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param memberCollectSpu 会员收藏的商品
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
//    @PreAuthorize("@pms.hasPermission('member_umsmembercollectspu_get')" )
    public R getUmsMemberCollectSpuPage(Page page, MemberCollectSpu memberCollectSpu) {
        return R.ok(memberCollectSpuService.page(page, Wrappers.query(memberCollectSpu)));
    }


    /**
     * 通过id查询会员收藏的商品
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
//    @PreAuthorize("@pms.hasPermission('member_umsmembercollectspu_get')" )
    public R getById(@PathVariable("id" ) Long id) {
        return R.ok(memberCollectSpuService.getById(id));
    }

    /**
     * 新增会员收藏的商品
     * @param memberCollectSpu 会员收藏的商品
     * @return R
     */
    @ApiOperation(value = "新增会员收藏的商品", notes = "新增会员收藏的商品")
    @SysLog("新增会员收藏的商品" )
    @PostMapping
//    @PreAuthorize("@pms.hasPermission('member_umsmembercollectspu_add')" )
    public R save(@RequestBody MemberCollectSpu memberCollectSpu) {
        return R.ok(memberCollectSpuService.save(memberCollectSpu));
    }

    /**
     * 修改会员收藏的商品
     * @param memberCollectSpu 会员收藏的商品
     * @return R
     */
    @ApiOperation(value = "修改会员收藏的商品", notes = "修改会员收藏的商品")
    @SysLog("修改会员收藏的商品" )
    @PutMapping
//    @PreAuthorize("@pms.hasPermission('member_umsmembercollectspu_edit')" )
    public R updateById(@RequestBody MemberCollectSpu memberCollectSpu) {
        return R.ok(memberCollectSpuService.updateById(memberCollectSpu));
    }

    /**
     * 通过id删除会员收藏的商品
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除会员收藏的商品", notes = "通过id删除会员收藏的商品")
    @SysLog("通过id删除会员收藏的商品" )
    @DeleteMapping("/{id}" )
//    @PreAuthorize("@pms.hasPermission('member_umsmembercollectspu_del')" )
    public R removeById(@PathVariable Long id) {
        return R.ok(memberCollectSpuService.removeById(id));
    }

}
