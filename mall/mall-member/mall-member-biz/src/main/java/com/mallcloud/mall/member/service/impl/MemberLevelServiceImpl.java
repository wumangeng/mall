package com.mallcloud.mall.member.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mallcloud.mall.member.api.entity.MemberLevel;
import com.mallcloud.mall.member.entity.vo.SearchVO;
import com.mallcloud.mall.member.mapper.MemberLevelMapper;
import com.mallcloud.mall.member.service.MemberLevelService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mallcloud.mall.common.mybatis.util.PageUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 会员等级 服务实现类
 * </p>
 *
 * @author Painter
 * @since 2021-07-10
 */
@Service
public class MemberLevelServiceImpl extends ServiceImpl<MemberLevelMapper, MemberLevel> implements MemberLevelService {

	@Override
	public PageUtils querySearchPage(SearchVO searchVO) {
		String param = searchVO.getSearchParam();
		QueryWrapper<MemberLevel> wrapper = new QueryWrapper<>();
		if (StrUtil.isNotEmpty(param)){
			wrapper.like("name",param);
		}
		return new PageUtils(baseMapper.selectPage(new Page<>(searchVO.getCurrent(),searchVO.getSize()),wrapper));
	}
}
