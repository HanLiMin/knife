package com.moontwon.knife.util;

import java.nio.channels.IllegalSelectorException;
import java.util.Iterator;
import java.util.TreeSet;

import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;

/**
 * 长整数区间 线程不安全
 * 
 * @author midzoon<br>
 *         magicsli@outlook.com<br>
 *         2017年5月24日
 */
public class LongTreeInterval implements LongInterval {
	/**
	 * 为{@code true}表示区间缓存已经经过修改
	 */
	private boolean mod;
	/**
	 * 区间所含值的数量
	 */
	private long length;
	/**
	 * 区间缓存，各个区间交集为空，多个区间以在坐标系上的位置排序
	 */
	private TreeSet<IntervalEntity> entities;

	public LongTreeInterval() {
		entities = Sets.newTreeSet();
	}
	@Override
	public long leftEnd() {
		return entities.first().leftEnd;
	}
	@Override
	public long rightEnd() {
		return entities.last().rightEnd;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public LongTreeInterval addOpen(long leftEnd, long rightEnd) {
		Preconditions.checkArgument(rightEnd - 1 >= leftEnd + 1, "添加一个开区间，右端值至少比左端值大2[leftEnd=%s,rightEnd=%s]", leftEnd, rightEnd);
		add(new IntervalEntity(leftEnd + 1, rightEnd - 1));
		return this;
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public LongTreeInterval addClose(long leftEnd, long rightEnd) {
		add(new IntervalEntity(leftEnd, rightEnd));
		return this;
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public LongTreeInterval addOpenClose(long leftEnd, long rightEnd) {
		Preconditions.checkArgument(rightEnd >= leftEnd + 1, "添加一个左开右闭区间，右端值至少比左端值大1[leftEnd=%s,rightEnd=%s]", leftEnd, rightEnd);
		add(new IntervalEntity(leftEnd + 1, rightEnd));
		return this;
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public LongTreeInterval addCloseOpen(long leftEnd, long rightEnd) {
		Preconditions.checkArgument(rightEnd >= leftEnd + 1, "添加一个左闭右开区间，右端值至少比左端值大1[leftEnd=%s,rightEnd=%s]", leftEnd, rightEnd);
		add(new IntervalEntity(leftEnd, rightEnd - 1));
		return this;
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean contains(long value) {
		Iterator<IntervalEntity> iterator = entities.iterator();
		while (iterator.hasNext()) {
			IntervalEntity entity = iterator.next();
			if (entity.contains(value)) {
				return true;
			}
		}
		return false;
	}
	/**
	 * UnsupportedOperation
	 */
	@Deprecated
	@Override
	public LongTreeInterval removeClose(long leftEnd, long rightEnd) {
		throw new UnsupportedOperationException();
	}
	/**
	 * UnsupportedOperation
	 */
	@Deprecated
	@Override
	public LongTreeInterval removeOpenClose(long leftEnd, long rightEnd) {
		throw new UnsupportedOperationException();
	}
	/**
	 * UnsupportedOperation
	 */
	@Deprecated
	@Override
	public LongTreeInterval removeOpen(long leftEnd, long rightEnd) {
		throw new UnsupportedOperationException();
	}
	/**
	 * UnsupportedOperation
	 */
	@Deprecated
	@Override
	public LongTreeInterval removeCloseOpen(long leftEnd, long rightEnd) {
		throw new UnsupportedOperationException();
	}
	@Override
	public void clear() {
		entities.clear();
	}
	/**
	 * UnsupportedOperation
	 */
	@Deprecated
	@Override
	public boolean isEmpty() {
		throw new UnsupportedOperationException();
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public long length() {
		if (mod) {
			int len = 0;
			Iterator<IntervalEntity> iterator = entities.iterator();
			while (iterator.hasNext()) {
				LongTreeInterval.IntervalEntity intervalEntity = iterator.next();
				len += intervalEntity.length();
			}
			length = len;
			mod = false;
		}
		return length;
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public long valueOfIndex(long index) {
		Preconditions.checkArgument(index < length() && index >= 0, "index值过大超出索引范围[length:%s,index=%s]", length(), index);
		Iterator<IntervalEntity> iterator = entities.iterator();
		while (iterator.hasNext()) {
			LongTreeInterval.IntervalEntity intervalEntity = iterator.next();
			long i = intervalEntity.length();
			if (index < i) {
				return intervalEntity.leftEnd + index;
			}
			index -= i;
		}
		throw new IllegalSelectorException();
	}
	/**
	 * {@inheritDoc}
	 */
	private void add(IntervalEntity intervalEntity) {
		mod = true;
		if (entities.contains(intervalEntity)) {
			return;
		}
		entities.add(intervalEntity);
		add0();
	}
	/**
	 * 清理区间缓存，合并有交集的区间 递归调用直到缓存中不存在有交集的区间
	 * 
	 * void
	 */
	private void add0() {
		IntervalEntity entity = entities.first();
		for (;;) {
			IntervalEntity next = entities.higher(entity);
			if (next == null) {
				return;
			} else if (entity.contains(next)) {
				entities.remove(next);
				continue;
			} else if (entity.intersected(next)) {
				entities.remove(entity);
				entities.remove(next);
				IntervalEntity unionEntity = entity.union(next);
				entities.add(unionEntity);
				add0();
				return;
			}
			entity = next;
		}

	}
	@Override
	public String toString() {
		if (entities.isEmpty()) {
			return "[]";
		}
		StringBuilder sb = new StringBuilder();
		Iterator<IntervalEntity> iterator = entities.iterator();
		while (iterator.hasNext()) {
			sb.append(iterator.next());
			if (iterator.hasNext()) {
				sb.append("U");
			}
		}
		return sb.toString();
	}
	/**
	 * 单一区间
	 * 
	 * @author midzoon<br>
	 *         magicsli@outlook.com<br>
	 *         2017年5月24日
	 */
	private class IntervalEntity implements Comparable<IntervalEntity> {
		private long leftEnd;
		private long rightEnd;

		public IntervalEntity(long leftEnd, long rightEnd) {
			Preconditions.checkArgument(rightEnd >= leftEnd, "右端值应大于等于左端值[leftEnd=%s,rightEnd=%s]", leftEnd, rightEnd);
			this.leftEnd = leftEnd;
			this.rightEnd = rightEnd;
		}

		/**
		 * 判断指定整数是否在区间内
		 * 
		 * @param value
		 * @return boolean
		 */
		public boolean contains(long value) {
			return value >= leftEnd && value <= rightEnd;
		}

		/**
		 * 判断本区间是否包含指定区间
		 * 
		 * @param entity
		 * @return boolean
		 */
		public boolean contains(IntervalEntity entity) {
			return entity.leftEnd >= leftEnd && entity.rightEnd <= rightEnd;
		}

		/**
		 * 判断与指定区间是否存在交集
		 * 
		 * @param entity
		 * @return boolean
		 */
		public boolean intersected(IntervalEntity entity) {
			return !(entity.leftEnd > rightEnd) || (entity.rightEnd < rightEnd);
		}

		/**
		 * 获取本区间长度
		 * 
		 * @return int
		 */
		public long length() {
			return rightEnd - leftEnd + 1;
		}

		/**
		 * 求本区与指定有交集区间的并集
		 * 
		 * @param entity
		 * @return IntervalEntity[] 一个区间数组，返回计算出并集
		 */
		public IntervalEntity union(IntervalEntity entity) {
			final long otherLeftEnd = entity.leftEnd;
			final long otherRightEnd = entity.rightEnd;
			if (otherLeftEnd < leftEnd) {
				if (otherRightEnd > rightEnd) {
					return entity;
				} else {
					return new IntervalEntity(otherLeftEnd, rightEnd);
				}
			} else {
				if (otherRightEnd > rightEnd) {
					return new IntervalEntity(leftEnd, otherRightEnd);
				} else {
					return this;
				}
			}

		}

		@Override
		public int compareTo(IntervalEntity o) {
			if (this.leftEnd > o.leftEnd) {
				return 1;
			} else if (this.leftEnd < o.leftEnd) {
				return -1;
			} else if (this.rightEnd > o.rightEnd) {
				return 1;
			} else if (this.rightEnd < o.rightEnd) {
				return -1;
			} else {
				return 0;
			}

		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + (int) (leftEnd ^ (leftEnd >>> 32));
			result = prime * result + (int) (rightEnd ^ (rightEnd >>> 32));
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			IntervalEntity other = (IntervalEntity) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (leftEnd != other.leftEnd)
				return false;
			if (rightEnd != other.rightEnd)
				return false;
			return true;
		}

		private LongTreeInterval getOuterType() {
			return LongTreeInterval.this;
		}
		@Override
		public String toString() {
			return new StringBuilder().append("[").append(Long.toString(leftEnd)).append(",").append(Long.toString(rightEnd)).append("]").toString();
		}
	}

}
