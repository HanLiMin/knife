package com.moontwon.knife.util;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
/**
 * 可以在类、字段上注解
 * 在类上注解时可指定子节点的名字，若采用默认值代表在根节点
 * 在字段注解时代表该字段是可配置字段，可指定取值的节点名字名字，若采用默认值则说明取与字段名字相同的节点
 * 
 * 
 * @author hanlimin<br>
 *         hanlimin.code@foxmail.com<br>
 *         2017年9月13日
 */
@Documented
@Retention(RUNTIME)
@Target({FIELD, TYPE})
public @interface Config {
	/**
	 * 该字段对应节点名字,若采用默认值则最终使用字段的名字
	 * 
	 * @return String
	 */
	String value() default "";
}
