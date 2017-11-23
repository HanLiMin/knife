package com.moontwon.knife.util;

/**
 * 
 * 数组工具
 * 
 * @author hanlimin<br>
 *         hanlimin.code@foxmail.com<br>
 *         2017年11月8日
 */
public class ArrayUtils {
	/**
	 * 生成包含置顶区间内所有值的一个数组
	 * 
	 * @param start
	 *            起点
	 * @param end
	 *            终点
	 * @return int[] 包含所有值的数组
	 * @exception IllegalArgumentException
	 *                end小于start时
	 */
	public static int[] fromInt(int start, int end) {
		if (start > end) {
			throw new IllegalArgumentException("start必须小于等于end,start=" + start + ",end=" + end);
		}
		if (start == end) {
			return new int[]{start};
		}
		int len = end - start + 1;
		int[] ints = new int[len];
		while (--len >= 0) {
			ints[len] = end--;
		}
		return ints;
	}
	/**
	 * 将字符串数组转换成整数数组
	 * @param strings 字符串数组
	 * @return
	 * int[] 整数数组
	 * @throws NumberFormatException 
	 */
	public static int[] fromString(String[] strings) {
		int[] ints = new int[strings.length];
		for (int i = 0; i < strings.length; i++) {
			ints[i] = Integer.parseInt(strings[i]);
		}
		return ints;
	}
	/**
	 * 生成包含置顶区间内所有值的一个数组
	 * 
	 * @param start
	 *            起点
	 * @param end
	 *            终点
	 * @return long[] 包含所有值的数组
	 * @exception IllegalArgumentException
	 *                end小于start时
	 * @exception UnsupportedOperationException
	 *                指定的区间过大时
	 */
	public static long[] fromLong(long start, long end) {

		if (start > end) {
			throw new IllegalArgumentException("start必须小于等于end,start=" + start + ",end=" + end);
		}

		if ((end - start) > Integer.MAX_VALUE) {
			throw new UnsupportedOperationException("区间长度超出所有允许的最大数组长度");
		}
		if (start == end) {
			return new long[]{start};
		}

		int len = (int) (end - start + 1);
		long[] longs = new long[len];
		while (--len >= 0) {
			longs[len] = end--;
		}
		return longs;
	}
}
