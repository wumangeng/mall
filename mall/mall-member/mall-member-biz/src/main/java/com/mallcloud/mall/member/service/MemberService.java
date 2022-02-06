package com.mallcloud.mall.member.service;

import com.mallcloud.mall.member.api.entity.Member;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mallcloud.mall.member.entity.vo.SearchVO;
import com.mallcloud.mall.common.mybatis.util.PageUtils;

/**
 * <p>
 * 会员 服务类
 * </p>
 *
 * @author Painter
 * @since 2021-07-10
 */
public interface MemberService extends IService<Member> {

	PageUtils querySearchPage(SearchVO searchVO);

	/**
	 * 校验用户密码
	 * */
	boolean checkPassword(String username,String password);
}
