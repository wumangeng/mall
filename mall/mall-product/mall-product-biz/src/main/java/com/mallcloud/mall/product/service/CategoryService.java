package com.mallcloud.mall.product.service;

import com.mallcloud.mall.common.core.util.R;
import com.mallcloud.mall.product.api.entity.Category;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 商品三级分类 服务类
 * </p>
 *
 * @author Painter
 * @since 2021-07-10
 */
public interface CategoryService extends IService<Category> {
	boolean saveCategory(Category category);

	List<Category> listWithTree();

	boolean removeMenusBatchByIds(List<Long> asList);

	/**
	 * 查询catelogId的完整路径
	 * [父路径/子路径/孙]
	 * */
    Long[] getCatelogPath(Long catelogId);

    boolean updateDetail(Category category);

}
