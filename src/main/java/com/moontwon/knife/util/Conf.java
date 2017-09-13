package com.moontwon.knife.util;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
/**
 * 
 * 标记字段，该字段从配置文件获取值
 * 
 * @author hanlimin<br>
 *         hanlimin.code@foxmail.com<br>
 *         2017年9月13日
 */
@Documented
@Retention(RUNTIME)
@Target(FIELD)
public @interface Conf {
	/**
	 * 该字段对应节点名字,若采用默认值则最终使用字段的名字
	 * 
	 * @return String
	 */
	String value() default "";
}
