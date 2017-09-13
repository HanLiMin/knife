package com.moontwon.knife.util;
/**
 * 获取指定的节点的名字
 * 
 * 用于自动从xml的指定节点内获取{@code Conf}标记字段对应的值
 * 
 * 
 * @author hanlimin<br>
 * hanlimin.code@foxmail.com<br>
 * 2017年9月15日
 */
public interface Config {
	/**
	 * 获取指定节点名字
	 * @return 
	 * String  节点名字
	 */
	String nodeName();
}
