package com.moontwon.knife.collect;

/**
 * 一个区间
 * @author midzoon<br>
 *         magicsli@outlook.com<br>
 *         2017年5月25日
 */
public interface Interval {
	/**
	 * 向区间内添加一个开区间
	 * @param leftEnd 左端点
	 * @param rightEnd 右端点
	 */
	void addOpen(int leftEnd, int rightEnd);
	/**
	 * 向区间内添加一个闭区间
	 * @param leftEnd 左端点
	 * @param rightEnd 右端点
	 * void
	 */
	void addClose(int leftEnd, int rightEnd);
	/**
	 * 向区间内添加一个左开右闭区间
	 * @param leftEnd 左端点
	 * @param rightEnd 右端点
	 * void
	 */
	void addOpenClose(int leftEnd, int rightEnd);
	/**
	 * 向区间内添加一个左闭右开区间
	 * @param leftEnd 左端点
	 * @param rightEnd 右端点
	 * void
	 */
	void addCloseOpen(int leftEnd, int rightEnd);
	/**
	 * 从区间内移除一个闭区间
	 * @param leftEnd 左端点
	 * @param rightEnd 右端点
	 * void
	 */
	void removeClose(int leftEnd, int rightEnd);
	/**
	 * 从区间内移除一个左开右闭区间
	 * @param leftEnd 左端点
	 * @param rightEnd 右端点
	 * void
	 */
	void removeOpenClose(int leftEnd, int rightEnd);
	/**
	 * 从区间内移除一个开区间
	 * @param leftEnd 左端点
	 * @param rightEnd 右端点
	 * void
	 */
	void removeOpen(int leftEnd, int rightEnd);
	/**
	 * 从区间内移除一个左闭右开区间
	 * @param leftEnd 左端点
	 * @param rightEnd 右端点
	 * void
	 */
	void removeCloseOpen(int leftEnd, int rightEnd);
	/**
	 * 将所有区间按顺序组合在一起形成一个数组，获取在这个数组中指定索引{@code index}的值
	 * @param index 索引值
	 * @return
	 * int
	 */
	int valueOfIndex(int index);
	/**
	 * 判断指定值是否在区间内
	 * @param value 指定值
	 * @return 是否在区间内
	 */
	boolean contains(int value);
	/**
	 * 获取区间所含值的数量
	 * @return 区间值的数量
	 */
	int length();
	/**
	 * 将区间置空
	 * 
	 */
	void clear();
	/**
	 * 区间是否为空
	 * @return 是否为空
	 */
	boolean isEmpty();
}
