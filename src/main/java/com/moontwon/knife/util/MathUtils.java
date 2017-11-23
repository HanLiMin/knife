package com.moontwon.knife.util;

import com.google.common.base.Preconditions;
/**
 * 
 * 数字计算工具包
 * 
 * @author hanlimin<br>
 *         hanlimin.code@foxmail.com<br>
 *         2017年11月13日
 */
public class MathUtils {
	/**
	 * 对多个int数字或int数组求和
	 * 
	 * @param ints
	 *            多个int数字或int数组
	 * @return int 和
	 */
	public static int sum(int... ints) {
		Preconditions.checkNotNull(ints);
		int s = 0;
		for (int i : ints) {
			s += i;
		}
		return s;
	}
	/**
	 * 对多个long数字或long数组求和
	 * 
	 * @param longs
	 *            多个long数字或long数组求和
	 * @return long 和
	 */
	public static long sum(Long... longs) {
		Preconditions.checkNotNull(longs);
		long s = 0;
		for (long i : longs) {
			s += i;
		}
		return s;
	}
	/**
	 * 对多个float或float数组求和
	 * 
	 * @param floats
	 *            多个float或float数组
	 * @return float 和
	 */
	public static float sum(float... floats) {
		Preconditions.checkNotNull(floats);
		float s = 0;
		for (float i : floats) {
			s += i;
		}
		return s;
	}
	/**
	 * 对多个doule或doule数组求和
	 * 
	 * @param doubles
	 * @return double 和
	 */
	public static double sum(double... doubles) {
		Preconditions.checkNotNull(doubles);
		double s = 0;
		for (double i : doubles) {
			s += i;
		}
		return s;
	}
	/**
	 * 对指定数组的数字取反
	 * 
	 * @param ints
	 *            指定的数组 void
	 */
	public static void negate(int[] ints) {
		for (int i = 0; i < ints.length; i++) {
			ints[i] = -ints[i];
		}
	}
	/**
	 * 对指定数组的数字取反
	 * 
	 * @param ints
	 *            指定的数组 void
	 */
	public static void negate(long[] longs) {
		for (int i = 0; i < longs.length; i++) {
			longs[i] = -longs[i];
		}
	}
	/**
	 * 对指定数组的数字取反
	 * 
	 * @param ints
	 *            指定的数组 void
	 */
	public static void negate(float[] floats) {
		for (int i = 0; i < floats.length; i++) {
			floats[i] = -floats[i];
		}
	}
	/**
	 * 对指定数组的数字取反
	 * 
	 * @param ints
	 *            指定的数组 void
	 */
	public static void negate(double[] doubles) {
		for (int i = 0; i < doubles.length; i++) {
			doubles[i] = -doubles[i];
		}
	}
}
