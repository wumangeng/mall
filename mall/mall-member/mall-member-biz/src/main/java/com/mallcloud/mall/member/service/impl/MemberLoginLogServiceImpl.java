package com.mallcloud.mall.member.service.impl;

import com.mallcloud.mall.member.api.entity.MemberLoginLog;
import com.mallcloud.mall.member.mapper.MemberLoginLogMapper;
import com.mallcloud.mall.member.service.MemberLoginLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 会员登录记录 服务实现类
 * </p>
 *
 * @author Painter
 * @since 2021-07-10
 */
@Service
public class MemberLoginLogServiceImpl extends ServiceImpl<MemberLoginLogMapper, MemberLoginLog> implements MemberLoginLogService {

}
