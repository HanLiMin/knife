package com.moontwon.knife.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

public class ArrayCircleTest {


	private static Circle<Integer> circle;
	private static int size = 16;
	@BeforeClass
	public static void init() {
		circle = new ArrayCircle<>(size);
	}
	@Test
	public void capacityTest() {
		assertEquals(circle.capacity(), size);
	}
	@Test
	public void sizeTest() {
		assertEquals(circle.size(), 0);
	}
	@Test
	public void putTest() {
		circle.put(0, 100);
		assertEquals(circle.get(0), Integer.valueOf(100));
		assertEquals(circle.get(0 + size), Integer.valueOf(100));
		assertEquals(circle.size(), 1);
		circle.clear();
		assertEquals(circle.size(), 0);

		circle.put(17, 100000);
		assertEquals(circle.get(17 - size), Integer.valueOf(100000));
		assertEquals(circle.get(17 + size), Integer.valueOf(100000));
		assertEquals(circle.size(), 1);
		circle.clear();
		for (int i = 0; i < circle.capacity(); i++) {
			circle.put(i, i);
		}
		assertEquals(circle.size(), circle.capacity());
		for (int i = 0; i < circle.capacity(); i++) {
			Object object = circle.get(i);
			assertEquals(object, Integer.valueOf(i));
		}

		circle.clear();
	}
	@Test
	public void removeTest() {
		circle.put(100, 100);
		circle.remove(100);
		circle.put(1654524521, 97);
		assertEquals(circle.get(1654524521), valueOf(97));
		assertEquals(circle.get(1654524521 + size), valueOf(97));
		circle.remove(1654524521 + size + size);
		assertTrue(circle.isEmpty());
		assertEquals(circle.size(), 0);
	}
	public static Integer valueOf(int i) {
		return Integer.valueOf(i);
	}
}
