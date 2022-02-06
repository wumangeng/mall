package com.mallcloud.mall.product.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mallcloud.mall.common.core.util.RedisUtils;
import com.mallcloud.mall.product.api.entity.Category;
import com.mallcloud.mall.product.mapper.CategoryMapper;
import com.mallcloud.mall.product.service.CategoryBrandRelationService;
import com.mallcloud.mall.product.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 商品三级分类 服务实现类
 * </p>
 *
 * @author Painter
 * @since 2021-07-10
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

	@Autowired
	private CategoryBrandRelationService categoryBrandRelationService;
	@Autowired
	private RedisUtils redisUtils;


	private final String categoryTree="product";


	/**
	 * @CacheEvict:清除缓存
	 *    1.key:指定要清除缓存中的某条数据
	 *    2.allEntries=true:删除缓存中的所有数据
	 *    beforeInvocation=false:默认是在方法之后执行清除缓存
	 *    3.beforeInvocation=true:现在是在方法执行之前执行清除缓存，
	 *                          作用是：只清除缓存、不删除数据库数据
	 */
	@CacheEvict(cacheNames = categoryTree,allEntries=true)
	@Override
	public boolean saveCategory(Category category) {
		return baseMapper.insert(category)>0;
	}

	/*1. @Cacheable的几个属性详解：
	 *       cacheNames/value：指定缓存组件的名字
	 *       key：缓存数据使用的key,可以用它来指定。默认使用方法参数的值，一般不需要指定
	 *       keyGenerator：作用和key一样，二选一
	 *       cacheManager和cacheResolver作用相同：指定缓存管理器，二选一
	 *       condition：指定符合条件才缓存，比如：condition="#id>3"
	 *                   也就是说传入的参数id>3才缓存数据
	 *      unless：否定缓存，当unless为true时不缓存，可以获取方法结果进行判断
	 *      sync：是否使用异步模式
	 * */
	@Cacheable(cacheNames = {categoryTree},key = "'category_tree'",sync = true)
	@Override
	public List<Category> listWithTree() {
		List<Category> categoryList = baseMapper.selectList(null);

		return categoryList.stream()
				//筛选出一级分类
				.filter(category -> category.getParentCid() == 0)
				//设置一级分类下的所有子分类
				.map(menu->{ return menu.setChildren(getChildrens(menu,categoryList)); })
				//排序(判断是否空指针)
				.sorted((menu1,menu2)-> {
					return ((menu1.getSort())==null ? 0:menu1.getSort() )-((menu2.getSort())==null ? 0:menu2.getSort()) ;
				} )
				.collect(Collectors.toList());
	}

	//传入当前分类和所有分类，递归返回当前分类下所有分类
	private List<Category> getChildrens(Category current,List<Category> all){
		List<Category> childrens = all.stream()
				//筛选出当前分类下的子分类
				.filter(category -> { return current.getCatId() == category.getParentCid(); })
				//递归查询
				.map(category -> { return category.setChildren(getChildrens(category, all)); })
				.sorted((menu1, menu2) -> { return ((menu1.getSort())==null ? 0:menu1.getSort() )-((menu2.getSort())==null ? 0:menu2.getSort()) ; })
				.collect(Collectors.toList());
		return childrens;
	}

	@CacheEvict(cacheNames = categoryTree,allEntries=true)
	@Override
	public boolean removeMenusBatchByIds(List<Long> asList) {
		// TODO 检查是否被引用
		return baseMapper.deleteBatchIds(asList)>0;
	}

	@Override
	public Long[] getCatelogPath(Long catelogId) {
		List<Long> path = new ArrayList<>();
		List<Long> catelogPath = this.findCatelogPath(catelogId, path);
		Collections.reverse(catelogPath);
		return catelogPath.toArray(new Long[catelogPath.size()]);
	}

	private List<Long> findCatelogPath(Long catelogId,List<Long> catelogPath){
		catelogPath.add(catelogId);
		Category category = baseMapper.selectById(catelogId);
		if (category.getParentCid()!=null && category.getParentCid()!=0){
			this.findCatelogPath(category.getParentCid(),catelogPath);
		}
		return catelogPath;
	}

	@CacheEvict(cacheNames = categoryTree,allEntries=true)
	@Transactional
	@Override
	public boolean updateDetail(Category category) {
		if (StrUtil.isNotEmpty(category.getName())){
			categoryBrandRelationService.updateCategoryName(category.getCatId(),category.getName());
		}
		return baseMapper.updateById(category)>0;
	}




}
