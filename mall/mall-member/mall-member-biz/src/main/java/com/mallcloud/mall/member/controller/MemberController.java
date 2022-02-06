
package com.mallcloud.mall.member.controller;

import cn.hutool.crypto.digest.BCrypt;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mallcloud.mall.member.api.entity.Member;
import com.mallcloud.mall.member.api.entity.MemberLevel;
import com.mallcloud.mall.member.entity.vo.SearchVO;
import com.mallcloud.mall.member.service.MemberLevelService;
import com.mallcloud.mall.member.service.MemberService;
import com.mallcloud.mall.common.core.util.R;
import com.mallcloud.mall.common.log.annotation.SysLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 会员
 *
 * @author painter
 * @date 2021-08-29 10:53:24
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/member" )
@Api(value = "member", tags = "会员管理")
public class MemberController {

    private final MemberService memberService;
    private final MemberLevelService levelService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param member 会员
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
//    @PreAuthorize("@pms.hasPermission('member_umsmember_get')" )
    public R getMemberPage(Page page, Member member) {
		Page memberPage = memberService.page(page, Wrappers.query(member));
		List<Member> records = memberPage.getRecords();
		records.forEach(members->{
			MemberLevel level = levelService.getById(members.getLevelId());
			if (level!=null) members.setLevelName(level.getName());
		});
		return R.ok(memberPage.setRecords(records) );
    }

	@ApiOperation(value = "条件分页查询", notes = "条件分页查询")
	@GetMapping("/page/search")
	public R getMemberPageSearch(SearchVO searchVO){
		return R.ok(memberService.querySearchPage(searchVO));
	}

	/**
     * 通过id查询会员
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
//    @PreAuthorize("@pms.hasPermission('member_umsmember_get')" )
    public R getById(@PathVariable("id" ) Long id) {
		Member member = memberService.getById(id);
		member.setPassword(null);
		MemberLevel level = levelService.getById(member.getLevelId());
		if (level!=null) member.setLevelName(level.getName());
		return R.ok(member);
    }

    /**
     * 新增会员
     * @param member 会员
     * @return R
     */
    @ApiOperation(value = "新增会员", notes = "新增会员")
    @SysLog("新增会员" )
    @PostMapping
//    @PreAuthorize("@pms.hasPermission('member_umsmember_add')" )
    public R save(@RequestBody Member member) {
		member.setPassword(BCrypt.hashpw(member.getPassword()));
        return R.ok(memberService.save(member));
    }

    /**
     * 修改会员
     * @param member 会员
     * @return R
     */
    @ApiOperation(value = "修改会员", notes = "修改会员")
    @SysLog("修改会员" )
    @PutMapping
//    @PreAuthorize("@pms.hasPermission('member_umsmember_edit')" )
    public R updateById(@RequestBody Member member) {
    	if (member.getPassword()!=null) member.setPassword(BCrypt.hashpw(member.getPassword()));
        return R.ok(memberService.updateById(member));
    }

    /**
     * 通过id删除会员
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除会员", notes = "通过id删除会员")
    @SysLog("通过id删除会员" )
    @DeleteMapping("/{id}" )
//    @PreAuthorize("@pms.hasPermission('member_umsmember_del')" )
    public R removeById(@PathVariable Long id) {
        return R.ok(memberService.removeById(id));
    }

}
