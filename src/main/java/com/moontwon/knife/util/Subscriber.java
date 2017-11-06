package com.moontwon.knife.util;

public interface Subscriber<T>{
	void onNext(T value);
}
