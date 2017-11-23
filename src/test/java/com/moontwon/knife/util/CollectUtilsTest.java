package com.moontwon.knife.util;

import java.util.List;

import org.junit.Test;

import com.google.common.collect.Lists;

public class CollectUtilsTest {
	@Test
	public void unzipTest() {
		List<String> list1 = Lists.newArrayList("1", "2", "3");
		List<String> list2 = Lists.newArrayList("11", "22", "33");
		List<String> list3 = Lists.newArrayList("111", "222", "333");
		List<String> list4 = Lists.newArrayList("1111", "2222", "3333");
		@SuppressWarnings("unchecked")
		List<List<String>> list5 = Lists.newArrayList(list1, list2, list3, list4);
		List<Object> list6 = Lists.newArrayList(list5, "666", "|");
		System.err.println(CollectUtils.unzip(list6, String.class, false));
	}
}
