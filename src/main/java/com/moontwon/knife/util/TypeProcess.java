package com.moontwon.knife.util;

import java.lang.reflect.Field;

/**
 * 自定义字段类型赋值处理
 * 
 * @author hanlimin<br>
 *         hanlimin.code@foxmail.com<br>
 *         2017年9月15日
 */
public interface TypeProcess {
	/**
	 * 处理字段赋值
	 * 
	 * @param config
	 *            待配置对象
	 * @param field
	 *            待配置字段
	 * @param type
	 *            待配置字段的类型
	 * @param value
	 *            待赋值的值
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	void process(Config config, Field field, Class<?> type, String node) throws IllegalArgumentException, IllegalAccessException;
}