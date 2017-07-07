/**
 * 
 */
package com.moontwon.knife.util;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.moontwon.knife.util.Interval;
import com.moontwon.knife.util.TreeInterval;

/**
 * @author midzoon<br>
 * magicsli@outlook.com<br>
 * 2017年5月25日
 */
public class TreeIntervalTest {

	private  Interval interval;
	@Before
	public  void init(){
		interval = new TreeInterval();
	}
	@Test
	public void createTest(){
		assertNotNull(interval);
		assertEquals("[]", interval.toString());
	}
	@Test
	public void addOpenTest(){
		interval.addOpen(1, 9);
		assertEquals("[2,8]", interval.toString());
		interval.addOpen(1, 9);
		assertEquals("[2,8]", interval.toString());
		interval.addOpen(20, 90);
		assertEquals("[2,8]U[21,89]", interval.toString());
		interval.addOpen(91, 93);
		assertEquals("[2,8]U[21,89]U[92,92]", interval.toString());
		interval.addOpen(89, 92);
		assertEquals("[2,8]U[21,89]U[90,91]U[92,92]", interval.toString());
		interval.addOpen(80, 93);
		assertEquals("[2,8]U[21,92]", interval.toString());
		interval.addOpen(1, 93);
		assertEquals("[2,92]", interval.toString());
		assertEquals(91, interval.length());
	}
}
