package com.moontwon.knife.util;

/**
 * 一个区间
 * @author midzoon<br>
 *         magicsli@outlook.com<br>
 *         2017年5月25日
 */
public interface LongInterval {
    
    /**
     * 获取区间左值
     * @return 区间左值
     */
	long leftEnd();
    /**
     * 获取区间右值
     * @return 区间右值
     */
	long rightEnd();
	/**
	 * 向区间内添加一个开区间
	 * @param leftEnd 左端点
	 * @param rightEnd 右端点
	 */
	LongTreeInterval addOpen(long leftEnd, long rightEnd);
	/**
	 * 向区间内添加一个闭区间
	 * @param leftEnd 左端点
	 * @param rightEnd 右端点
	 * void
	 */
	LongTreeInterval addClose(long leftEnd, long rightEnd);
	/**
	 * 向区间内添加一个左开右闭区间
	 * @param leftEnd 左端点
	 * @param rightEnd 右端点
	 * void
	 */
	LongTreeInterval addOpenClose(long leftEnd, long rightEnd);
	/**
	 * 向区间内添加一个左闭右开区间
	 * @param leftEnd 左端点
	 * @param rightEnd 右端点
	 * void
	 */
	LongTreeInterval addCloseOpen(long leftEnd, long rightEnd);
	/**
	 * 从区间内移除一个闭区间
	 * @param leftEnd 左端点
	 * @param rightEnd 右端点
	 * void
	 */
	LongTreeInterval removeClose(long leftEnd, long rightEnd);
	/**
	 * 从区间内移除一个左开右闭区间
	 * @param leftEnd 左端点
	 * @param rightEnd 右端点
	 * void
	 */
	LongTreeInterval removeOpenClose(long leftEnd, long rightEnd);
	/**
	 * 从区间内移除一个开区间
	 * @param leftEnd 左端点
	 * @param rightEnd 右端点
	 * void
	 */
	LongTreeInterval removeOpen(long leftEnd, long rightEnd);
	/**
	 * 从区间内移除一个左闭右开区间
	 * @param leftEnd 左端点
	 * @param rightEnd 右端点
	 * void
	 */
	LongTreeInterval removeCloseOpen(long leftEnd, long rightEnd);
	/**
	 * 将所有区间按顺序组合在一起形成一个数组，获取在这个数组中指定索引{@code index}的值
	 * @param index 索引值
	 * @return
	 * int
	 */
	long valueOfIndex(long index);
	/**
	 * 判断指定值是否在区间内
	 * @param value 指定值
	 * @return 是否在区间内
	 */
	boolean contains(long value);
	/**
	 * 获取区间所含值的数量
	 * @return 区间值的数量
	 */
	long length();
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
