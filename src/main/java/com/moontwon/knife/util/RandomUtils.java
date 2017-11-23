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
	/**
	 * 
	 * 讲一个数字随机拆解成指定数量的多个数字，且生成的这些数字之和等于指定数字
	 * 
	 * 生成数字的方差大
	 * 
	 * @param secureRandom
	 *            随机器
	 * @param min
	 *            最小值，应大于等于0
	 * @param sum
	 *            要拆分的数字，应大于等于0
	 * @param num
	 *            数量 应大于等于0
	 * @return int[] 包含生成数字的数组
	 */
	public static int[] fierceDecompose(SecureRandom secureRandom, int min, int sum, int num) {
		Preconditions.checkArgument(min > 0 && sum > 0 && num > 0, "min,sun,num都应大于0");
		int multiple = 1;
		while (sum * multiple / num < min) {
			multiple++;
		}
		int[] oneplus = gentleDecompose(secureRandom, min, sum * (multiple + 1), num);
		int[] one = gentleDecompose(secureRandom, min, sum * multiple, num);
		int[] result = new int[num];
		while (--num >= 0) {
			result[num] = oneplus[num] - one[num];
		}
		return result;
	}
	/**
	 * 讲一个数字随机拆解成指定数量的多个数字，且生成的这些数字之和等于指定数字
	 * 
	 * 生成数字的方差小
	 * 
	 * @param secureRandom
	 *            随机器
	 * @param min
	 *            最小值 应大于等于0
	 * @param sum
	 *            要拆分的数字 应大于等于0
	 * @param num
	 *            数量 应大于等于0
	 * @return int[] 包含生成数字的数组
	 */
	public static int[] gentleDecompose(SecureRandom secureRandom, int min, int sum, int num) {
		Preconditions.checkArgument(min >= 0 && sum >= 0 && num >= 0, "min,sun,num都应大于等于0");
		int[] result = new int[num];
		while (num != 1) {
			int max = sum / num * 2;
			int value = RandomUtils.nextInt(secureRandom, 1, max);
			value = value < min ? min : value;
			--num;
			sum -= value;
			result[num] = value;
		}
		result[0] = sum;
		return result;
	}
	/**
	 * 消去数组的峰值并保持和不变 生成的数字都小于等于最大值，数组后一个数字与前一个数字之差的绝对值小于等于最大值
	 * 
	 * @param raw
	 *            包含数字的数组
	 * @param max
	 *            最大值 void
	 */
	public static void soften(int[] raw, int max) {
		Preconditions.checkArgument(max >= 0, "max应大于等于0");
		final int len = raw.length;
		Preconditions.checkArgument(MathUtils.sum(raw) / len <= max, "max过大");

		int pool = eliminate(raw, max);
		while (pool != 0) {
			for (int i = 0; i < len - 1; i++) {
				if (i == 0 && pool != 0) {
					int l = raw[0];
					if (pool > 0 && !(l == max)) {
						raw[0] = ++l;
						--pool;
					} else if (pool < 0 && !(l == -max)) {
						raw[0] = --l;
						++pool;
					}

				}
				int l = raw[i];
				int r = raw[i + 1];
				int d = r - l;
				if (Math.abs(d) > max) {
					raw[i + 1] = d > 0 ? l + max : l - max;
					pool = d > 0 ? pool - max + d : pool + d + max;
				} else {
					if (pool != 0) {
						if (pool > 0 && !(r == max)) {
							raw[i + 1] = ++r;
							--pool;
						} else if (pool < 0 && !(l == -max)) {
							raw[i + 1] = --r;
							++pool;
						}
					}
				}
			}
			if (pool == 0) {
				pool = eliminate(raw, max);
			}
		}
	}
	/**
	 * 消去峰值并返回，消去的值的和
	 * 
	 * @param raw
	 *            包含数字的数组
	 * @param max
	 *            最大值，应大于等于0
	 * @return int 消去的值的和
	 */
	private static int eliminate(int[] raw, int max) {
		Preconditions.checkArgument(max >= 0, "max应大于等于0");
		int pool = 0;
		// 消峰
		for (int i = 0; i < raw.length; i++) {
			int value = raw[i];
			if (Math.abs(value) > max) {
				raw[i] = value > 0 ? max : -max;
				pool += value - raw[i];
			}
		}
		return pool;
	}
	/**
	 * 检查数组内的数字是否符合规则 规则： I)数组内的数字都小于等于最大值 II)数组后一个数字与前一个数字之差的绝对值小于等于最大值
	 * III)数组内的数字的和为定值
	 * 
	 * @param raw
	 *            包含数字的数组
	 * @param max
	 *            最大值
	 * @param sum
	 * @return boolean {@code true}符合规则,{@code false}不符合规则
	 */
	public static boolean discover(int[] raw, int max, int sum) {
		Preconditions.checkArgument(max > 0 && sum >= 0, "sum与max都应大于等于0");
		int s = 0;
		for (int i = 0; i < raw.length - 1; i++) {
			int r = raw[i];
			int l = raw[i + 1];
			if (i == 0 && Math.abs(r) > max) {
				return false;
			}
			if (Math.abs(r) <= max && Math.abs(l) <= max && Math.abs(l - r) <= max) {
				s += r;
			} else {
				return false;
			}
		}
		s += raw[raw.length - 1];
		return s == sum;
	}

}
