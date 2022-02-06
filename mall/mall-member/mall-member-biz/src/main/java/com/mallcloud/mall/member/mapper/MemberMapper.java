package com.mallcloud.mall.member.mapper;

import com.mallcloud.mall.member.api.entity.Member;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 会员 Mapper 接口
 * </p>
 *
 * @author Painter
 * @since 2021-07-10
 */
@Mapper
public interface MemberMapper extends BaseMapper<Member> {

}
