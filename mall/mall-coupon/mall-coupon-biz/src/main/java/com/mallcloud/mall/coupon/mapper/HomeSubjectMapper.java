package com.mallcloud.mall.coupon.mapper;

import com.mallcloud.mall.coupon.api.entity.HomeSubject;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 首页专题表【jd首页下面很多专题，每个专题链接新的页面，展示专题商品信息】 Mapper 接口
 * </p>
 *
 * @author Painter
 * @since 2021-07-10
 */
@Mapper
public interface HomeSubjectMapper extends BaseMapper<HomeSubject> {

}
