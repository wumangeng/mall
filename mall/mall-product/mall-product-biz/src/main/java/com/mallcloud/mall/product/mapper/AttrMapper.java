package com.mallcloud.mall.product.mapper;

import com.mallcloud.mall.product.api.entity.Attr;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 商品属性 Mapper 接口
 * </p>
 *
 * @author Painter
 * @since 2021-07-10
 */
@Mapper
public interface AttrMapper extends BaseMapper<Attr> {

}
