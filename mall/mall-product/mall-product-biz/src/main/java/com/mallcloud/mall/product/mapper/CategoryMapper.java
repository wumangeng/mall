package com.mallcloud.mall.product.mapper;

import com.mallcloud.mall.product.api.entity.Category;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 商品三级分类 Mapper 接口
 * </p>
 *
 * @author Painter
 * @since 2021-07-10
 */
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {

}
