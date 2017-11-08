package com.moontwon.knife.util;

/**
 * 被观察者
 * 
 * 
 * @author hanlimin<br>
 * hanlimin.code@foxmail.com<br>
 * 2017年11月6日
 * @param <T>
 */
public interface Observable<T> {
	/**
	 * 订阅
	 * @param subscriber 订阅者
	 */
	void subscribe(Subscriber<T> subscriber);
}
