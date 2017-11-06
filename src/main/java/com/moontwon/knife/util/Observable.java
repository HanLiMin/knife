package com.moontwon.knife.util;

import java.util.concurrent.ConcurrentSkipListSet;

import com.google.common.base.Preconditions;

/**
 * 被观察者
 * 
 * 
 * @author hanlimin<br>
 * hanlimin.code@foxmail.com<br>
 * 2017年11月6日
 * @param <T>
 */
public class Observable<T> {
	private ConcurrentSkipListSet<Subscriber<T>> subscribers;
	/**
	 * 订阅
	 * @param subscriber 订阅者
	 */
	void subscribe(Subscriber<T> subscriber){
		Preconditions.checkNotNull(subscriber);
		subscribers.add(subscriber);
	}
	/**
	 * 向订阅发送消息
	 * @param value
	 * void
	 */
	void next(T value){
		Preconditions.checkNotNull(value);
		subscribers.forEach(e->e.onNext(value));
	}
}
