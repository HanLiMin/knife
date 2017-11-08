package com.moontwon.knife.util;

import java.security.SecureRandom;

import com.google.common.base.Preconditions;
/**
 * 
 * 随机工具包
 * 
 * @author hanlimin<br>
 *         hanlimin.code@foxmail.com<br>
 *         2017年11月8日
 */
public class RandomUtils {
	/**
	 * 在包含该指定左值，右值区间内随机取一个数值
	 * 
	 * @param left
	 *            左值
	 * @param right
	 *            右值
	 * @param secureRandom
	 *            随机器
	 * @return long 随机数
	 */
	public static long nextLong(SecureRandom secureRandom, long left, long right) {
		return (long) nextDouble(secureRandom, left, right);
	}
	/**
	 * 
	 * 在多个区间内取个随机值 LongInterval区间不得并发使用
	 * 
	 * @param secureRandom
	 *            随机器
	 * @param longInterval
	 *            long型整数区间
	 * @param longs
	 *            区间数组
	 * @return long 随机值
	 */
	public static long nextLong(SecureRandom secureRandom, LongInterval longInterval, long... longs) {
		Preconditions.checkNotNull(secureRandom, "secureRandom不能为null");
		Preconditions.checkNotNull(longInterval, "interval不能为null");
		int len = longs.length;
		Preconditions.checkArgument((len & 1) == 0, "longs不能是奇数个");
		for (int i = 0; i < len; i += 2) {
			long left = longs[i];
			long right = longs[i + 1];
			if (left > right) {
				throw new IllegalArgumentException("left值应小于等于right,left=" + left + ",right=" + right);
			}
			longInterval.addClose(left, right);
		}
		long index = nextLong(secureRandom, 0, longInterval.length());
		long value = longInterval.valueOfIndex(index);
		longInterval.clear();
		return value;
	}
	/**
	 * 在包含该指定左值，右值区间内随机取一个数值
	 * 
	 * @param left
	 *            左值
	 * @param right
	 *            右值
	 * @param secureRandom
	 *            随机器
	 * @return int 随机数
	 */
	public static int nextInt(SecureRandom secureRandom, int left, int right) {
		return (int) nextDouble(secureRandom, left, right);
	}
	/**
	 * 
	 * 在多个区间内取个随机值 IntInterval区间不得并发使用
	 * 
	 * @param secureRandom
	 *            随机器
	 * @param IntInterval
	 *            long型整数区间
	 * @param ints
	 *            区间数组
	 * @return int 随机值
	 */
	public static int nextInt(SecureRandom secureRandom, IntInterval intInterval, int... ints) {
		Preconditions.checkNotNull(secureRandom, "secureRandom不能为null");
		Preconditions.checkNotNull(intInterval, "interval不能为null");
		int len = ints.length;
		Preconditions.checkArgument((len & 1) == 0, "longs不能是奇数个");
		for (int i = 0; i < len; i += 2) {
			int left = ints[i];
			int right = ints[i + 1];
			if (left > right) {
				throw new IllegalArgumentException("left值应小于等于right,left=" + left + ",right=" + right);
			}
			intInterval.addClose(left, right);
		}
		int index = nextInt(secureRandom, 0, intInterval.length());
		int value = intInterval.valueOfIndex(index);
		intInterval.clear();
		return value;
	}
	/**
	 * 在包含该指定左值，右值区间内随机取一个数值
	 * 
	 * @param left
	 *            左值
	 * @param right
	 *            右值
	 * @param secureRandom
	 *            随机器
	 * @return double 随机数
	 */
	public static double nextDouble(SecureRandom secureRandom, double left, double right) {
		Preconditions.checkNotNull(secureRandom, "secureRandom不能为null");
		Preconditions.checkArgument(left <= right, "right必须大于left,left=%s,right=%s", left, right);
		if (left == right) {
			return left;
		}
		/*
		 * 3中情况 1）0<L<R 2)L<0,R>0 3)0>R>L
		 */
		if (left > 0 && right > 0) {
			return left + (right - left + 1.0) * secureRandom.nextDouble();
		} else if (left < 0 && right > 0) {
			return left - 1.0 + (right - left + 2.0) * secureRandom.nextDouble();
		} else {
			return left - 1.0 + (right - left + 1.0) * secureRandom.nextDouble();
		}
	}
}
