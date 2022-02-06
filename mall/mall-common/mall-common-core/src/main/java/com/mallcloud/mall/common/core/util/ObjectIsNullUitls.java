package com.mallcloud.mall.common.core.util;

import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Map;

/**
 * 对象及对象属性值判空工具类
 * 可判断某个属性也可判断全部
 * 为空返回 true
 * */
@Component
public class ObjectIsNullUitls {
	public static boolean checkFieldAllNull(Object object) throws IllegalAccessException {
		for (Field f : object.getClass().getDeclaredFields()) {
//			System.out.println("name:" + f.getName());
			f.setAccessible(true);
			if (Modifier.isFinal(f.getModifiers()) && Modifier.isStatic(f.getModifiers())) {
				continue;
			}
			if (!isEmpty(f.get(object))) {
				return false;
			}
			f.setAccessible(false);
		}
		//父类public属性
		for (Field f : object.getClass().getFields()) {
			System.out.println("name:" + f.getName());
			f.setAccessible(true);
			if (Modifier.isFinal(f.getModifiers()) && Modifier.isStatic(f.getModifiers())) {
				continue;
			}
			if (!isEmpty(f.get(object))) {
				return false;
			}
			f.setAccessible(false);
		}
		return true;
	}

	private static boolean isEmpty(Object object) {
		if (object == null) {
			return true;
		}
		if (object instanceof String && (object.toString().equals(""))) {
			return true;
		}
		if (object instanceof Collection && ((Collection) object).isEmpty()) {
			return true;
		}
		if (object instanceof Map && ((Map) object).isEmpty()) {
			return true;
		}
		if (object instanceof Object[] && ((Object[]) object).length == 0) {
			return true;
		}
		return false;
	}
}

