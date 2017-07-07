package com.moontwon.knife.util;

import java.util.concurrent.locks.ReentrantLock;

/**
 * <p>
 * 圆状储存数据元素
 * </p>
 * <p>
 * 数据模型为一个圆，在圆上取一个点作为起点p， 在指定周长C的圆上根据指定的索引序号和数据元素进行储存。
 * </p>
 * <p>
 * 索引序号在圆上周长的映射方法为：以p的顺时针方向移动，在p点时对应序号0，每移动一点对应序号加1，共移动C次，回到点p，此时对应的序号为C,再次移动一次则对应序号为C+1，如此无限循环。
 * </p>
 * 
 * <p>
 * 周长C或称之为容量最大为{@code Integer.MAX_VALUE}
 * </p>
 * 
 * 数组实现、线程安全
 * 
 * @author hanlimin<br>
 *         hanlimin.code@foxmail.com<br>
 *         2017年7月6日
 */
public class ArrayCircle<E> implements Circle<E> {
	private transient final ReentrantLock lock = new ReentrantLock();

	private volatile Object[] array;
	private int capacity;
	private volatile int size;

	/**
	 * 
	 * @param initialCapacity
	 *            容量
	 */
	public ArrayCircle(int initialCapacity) {
		if (initialCapacity < 0) {
			throw new IllegalArgumentException("initialCapacity参数不能小于0");
		}
		array = new Object[initialCapacity];
		capacity = initialCapacity;
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int capacity() {
		return capacity;
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int size() {
		lock.lock();
		try {
			return size;
		} finally {
			lock.unlock();
		}

	}
	/**
	 * {@inheritDoc}
	 * 
	 * @throws IllegalArgumentException
	 *             当索引值是负数时
	 */
	@SuppressWarnings("unchecked")
	@Override
	public E get(long index) {
		checkRange(index);
		return (E) array[(int) (index % capacity)];
	}
	/**
	 * {@inheritDoc}
	 * 
	 * @throws IllegalArgumentException
	 *             当索引值是负数时
	 */
	@Override
	public boolean put(int index, E e) {
		lock.lock();
		try {
			checkRange(index);
			E o = get(index);
			if (eq(o, e)) {
				return false;
			} else {
				array[index % capacity] = e;
				++size;
				return false;
			}
		} finally {
			lock.unlock();
		}
	}
	/**
	 * {@inheritDoc}
	 * 
	 * @throws IllegalArgumentException
	 *             当索引值是负数时
	 */
	@Override
	public boolean put(long index, E e) {
		lock.lock();
		try {
			checkRange(index);
			E o = get(index);
			if (eq(o, e)) {
				return false;
			} else {
				array[(int) (index % capacity)] = e;
				++size;
				return false;
			}
		} finally {
			lock.unlock();
		}

	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public E remove(long index) {
		lock.lock();
		try {
			checkRange(index);
			E e = get(index);
			if (e == null) {
				return null;
			} else {
				array[(int) (index % capacity)] = null;
				--size;
				return e;
			}
		} finally {
			lock.unlock();
		}
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public E remove(int index) {
		lock.lock();
		try {
			checkRange(index);
			E e = get(index);
			if (e == null) {
				return null;
			} else {
				array[index % capacity] = null;
				--size;
				return e;
			}
		} finally {
			lock.unlock();
		}
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isEmpty() {
		lock.lock();
		try {
			if (size > 0) {
				return false;
			}
			return true;
		} finally {
			lock.unlock();
		}
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void clear() {
		lock.lock();
		try {
			array = new Object[capacity];
			size = 0;
		} finally {
			lock.unlock();
		}
	}
	/**
	 * 检测给定的索引值是否正确
	 * 
	 * @param index
	 */
	private void checkRange(long index) {
		if (index < 0) {
			throw new IllegalArgumentException("index应该是一个正数");
		}
	}

	/**
	 * 判断两个对象是否相等
	 * 
	 * @param o1
	 * @param o2
	 * @return boolean 是否相等
	 */
	private static boolean eq(Object o1, Object o2) {
		return (o1 == null ? o2 == null : o1.equals(o2));
	}

}
