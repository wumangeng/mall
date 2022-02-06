package com.mallcloud.mall.coupon.service.impl;

import com.mallcloud.mall.coupon.api.entity.HomeSubject;
import com.mallcloud.mall.coupon.mapper.HomeSubjectMapper;
import com.mallcloud.mall.coupon.service.HomeSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 首页专题表【jd首页下面很多专题，每个专题链接新的页面，展示专题商品信息】 服务实现类
 * </p>
 *
 * @author Painter
 * @since 2021-07-10
 */
@Service
public class HomeSubjectServiceImpl extends ServiceImpl<HomeSubjectMapper, HomeSubject> implements HomeSubjectService {

}
