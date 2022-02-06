package com.mallcloud.mall.member.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.BCrypt;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mallcloud.mall.member.api.entity.Member;
import com.mallcloud.mall.member.api.entity.MemberLevel;
import com.mallcloud.mall.member.entity.vo.SearchVO;
import com.mallcloud.mall.member.mapper.MemberMapper;
import com.mallcloud.mall.member.service.MemberLevelService;
import com.mallcloud.mall.member.service.MemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mallcloud.mall.common.mybatis.util.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

/**
 * <p>
 * 会员 服务实现类
 * </p>
 *
 * @author Painter
 * @since 2021-07-10
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {

	@Autowired
	private MemberLevelService levelService;

	@Override
	public PageUtils querySearchPage(SearchVO searchVO) {
		String param = searchVO.getSearchParam();
		QueryWrapper<Member> wrapper = new QueryWrapper<>();
		if (StrUtil.isNotEmpty(param)){
			wrapper.eq("level_id",param)
					.or().like("username",param)
					.or().like("nickname",param)
					.or().like("mobile",param)
					.or().like("email",param)
					.or().eq("gender",param)
					.or().like("city",param)
					.or().like("job",param);
		}
		wrapper.orderByDesc("create_time");
		Page<Member> memberPage = baseMapper.selectPage(new Page<>(searchVO.getCurrent(), searchVO.getSize()), wrapper);
		List<Member> records = memberPage.getRecords();
		records.forEach(members->{
			MemberLevel level = levelService.getById(members.getLevelId());
			if (level!=null) members.setLevelName(level.getName());
		});
		memberPage.setRecords(records);
		return new PageUtils(memberPage);

	}

	@Override
	public boolean checkPassword(String username, String password) {
		Member member = baseMapper.selectOne(Wrappers.<Member>lambdaQuery().eq(Member::getUsername, username));
		Assert.isTrue(ObjectUtil.isNotEmpty(member),"用户名不存在！");
		return 	BCrypt.checkpw(password,member.getPassword());
	}
}
