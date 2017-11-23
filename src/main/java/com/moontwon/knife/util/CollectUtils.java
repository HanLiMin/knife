package com.moontwon.knife.util;

import java.util.Collection;
import java.util.List;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
/**
 * 
 * 集合工具
 * 
 * @author hanlimin<br>
 *         hanlimin.code@foxmail.com<br>
 *         2017年11月9日
 */
public class CollectUtils {
	/**
	 * 将多个嵌套的集合拆解成包含指定元素的列表
	 * 
	 * 
	 * @param collection
	 *            多个嵌套的集合
	 * @param clz
	 *            指定元素类型
	 * @param allow
	 *            是否允许其它类型元素
	 * @return List<T> 包含指定类型元素的列表
	 */
	public static <T> List<T> unzip(Collection<?> collection, Class<T> clz, boolean allow) {
		Preconditions.checkNotNull(collection, "collection is null");
		Preconditions.checkNotNull(clz, "clz is null");
		Preconditions.checkArgument(!collection.isEmpty(), "collection is empty");

		List<T> list = Lists.newArrayList();
		unzip(list, collection, clz, allow);

		return list;
	}
	/**
	 * 
	 * 递归取出集合中元素
	 * 
	 * @param list
	 *            储存元素的列表
	 * @param collection
	 *            要解包的结合
	 * @param clz
	 *            指定的元素类型
	 * @param allow
	 *            是否允许其它类型元素
	 */
	@SuppressWarnings("unchecked")
	private static <T> void unzip(List<T> list, Collection<?> collection, Class<T> clz, boolean allow) {
		collection.forEach(e -> {
			if (e instanceof Collection<?>) {
				Collection<?> c = (Collection<?>) e;
				unzip(list, c, clz, allow);
			} else if (clz.isInstance(e)) {
				list.add((T) e);
			} else {
				if (!allow) {
					throw new IllegalArgumentException("集合中的元素不是指定类型的子类 e: " + e.getClass().getName() + ", 指定类型：" + clz.getName());
				}
			}
		});

	}
}
