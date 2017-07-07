package com.moontwon.knife.util;

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
 * @author hanlimin<br>
 *         hanlimin.code@foxmail.com<br>
 *         2017年7月6日
 */
public interface Circle<E> {
	/**
	 * 返回容量
	 * 
	 * @return 容量
	 */
	int capacity();
	/**
	 * 返回以储存的元素的数量
	 * 
	 * @return 元素的数量
	 */
	int size();
	/**
	 * 返回指定序号对应的数据元素
	 * 
	 * @param index
	 *            要获取的序号
	 * @return 指定序号对应的数据元素
	 */
	E get(long index);
	/**
	 * 将数据元素储存到索引指定的位置上
	 * @param index 索引
	 * @param e 数据元素
	 * @return <tt>ture</tt> 添加成功,<tt>false</tt>添加失败表明已存在
	 */
	boolean put(long index, E e);
	/**
	 * 将数据元素储存到索引指定的位置上
	 * @param index 索引
	 * @param e 数据元素
	 * @return <tt>ture</tt> 添加成功,<tt>false</tt>添加失败表明已存在
	 */
	boolean put(int index, E e);
	/**
	 * 删除指定索引位置上的数据元素，并返回被删除的数据元素。如果要删除指定索引位置上的没有数据元素则返回{@code null}
	 * @param index 索引
	 * @return 被删除的数据元素或因为要删除的索引位置上的没有数据元素而返回{@code null}
	 */
	E remove(long index);
	/**
	 * 删除指定索引位置上的数据元素，并返回被删除的数据元素。如果要删除指定索引位置上的没有数据元素则返回{@code null}
	 * @param index 索引
	 * @return 被删除的数据元素或因为要删除的索引位置上的没有数据元素而返回{@code null}
	 */
	E remove(int index);
	/**
	 * 判断容器是否为空
	 * @return <tt>true</tt> 当容器为空时
	 */
	boolean isEmpty();
	/**
	 * 清空储存的所有数据元素
	 */
	void clear();
}
