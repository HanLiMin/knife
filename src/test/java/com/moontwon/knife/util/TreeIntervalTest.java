/**
 * 
 */
package com.moontwon.knife.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

/**
 * @author midzoon<br>
 * magicsli@outlook.com<br>
 * 2017年5月25日
 */
public class TreeIntervalTest {

	private  IntInterval intInterval;
	@Before
	public  void init(){
		intInterval = new IntTreeInterval();
	}
	@Test
	public void createTest(){
		assertNotNull(intInterval);
		assertEquals("[]", intInterval.toString());
	}
	@Test
	public void addOpenTest(){
		intInterval.addOpen(1, 9);
		assertEquals("[2,8]", intInterval.toString());
		intInterval.addOpen(1, 9);
		assertEquals("[2,8]", intInterval.toString());
		intInterval.addOpen(20, 90);
		assertEquals("[2,8]U[21,89]", intInterval.toString());
		intInterval.addOpen(91, 93);
		assertEquals("[2,8]U[21,89]U[92,92]", intInterval.toString());
		intInterval.addOpen(89, 92);
		assertEquals("[2,8]U[21,89]U[90,91]U[92,92]", intInterval.toString());
		intInterval.addOpen(80, 93);
		assertEquals("[2,8]U[21,92]", intInterval.toString());
		intInterval.addOpen(1, 93);
		assertEquals("[2,92]", intInterval.toString());
		assertEquals(91, intInterval.length());
	}
}
