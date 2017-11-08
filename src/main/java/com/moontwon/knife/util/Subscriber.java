package com.moontwon.knife.util;
/**
 * 订阅者
 * 
 * 
 * @author hanlimin<br>
 * hanlimin.code@foxmail.com<br>
 * 2017年11月6日
 * @param <T>
 */
public interface Subscriber<T>{
	/**
	 * 
	 * @param value
	 * void
	 */
	void onNext(T value);
}
