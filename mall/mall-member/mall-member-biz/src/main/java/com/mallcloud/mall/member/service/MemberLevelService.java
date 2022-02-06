package com.mallcloud.mall.member.service;

import com.mallcloud.mall.member.api.entity.MemberLevel;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mallcloud.mall.member.entity.vo.SearchVO;
import com.mallcloud.mall.common.mybatis.util.PageUtils;

/**
 * <p>
 * 会员等级 服务类
 * </p>
 *
 * @author Painter
 * @since 2021-07-10
 */
public interface MemberLevelService extends IService<MemberLevel> {

	PageUtils querySearchPage(SearchVO searchVO);

}
