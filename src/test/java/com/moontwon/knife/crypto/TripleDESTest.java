package com.moontwon.knife.crypto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

public class TripleDESTest {
	@Test
	public void generateKeyTest() {
		SecretKey secretKey_112 = TripleDES.generateKey(112);
		assertNotNull(secretKey_112);
		SecretKey secretKey_168 = TripleDES.generateKey(168);
		assertNotNull(secretKey_168);
	}
	@Test
	public void generateIvTset() {
		IvParameterSpec iv = TripleDES.generateIv();
		assertNotNull(iv);
	}
	@Test
	public void encryptEBCTest(){
		SecretKey secretKey = TripleDES.generateKey(168);
		TripleDES tripleDES = TripleDES.newTripleDESWithEBC(Padding.PKCS5PADDING, secretKey);
		String test_str1 = RandomStringUtils.random(1);
		String test_str2 = new String(tripleDES.decrypt(tripleDES.encrypt(test_str1.getBytes())));
		assertEquals(test_str1, test_str2);
		
		String test_str3 = RandomStringUtils.random(168);
		String test_str4 = new String(tripleDES.decrypt(tripleDES.encrypt(test_str3.getBytes())));
		assertEquals(test_str3, test_str4);
		
		String test_str5 = RandomStringUtils.random(1004);
		String test_str6 = new String(tripleDES.decrypt(tripleDES.encrypt(test_str5.getBytes())));
		assertEquals(test_str5, test_str6);
		
		String test_str7 = RandomStringUtils.random(3001);
		String test_str8 = new String(tripleDES.decrypt(tripleDES.encrypt(test_str7.getBytes())));
		assertEquals(test_str7, test_str8);
	}
	@Test
	public void encryptCBCTest(){
		SecretKey secretKey = TripleDES.generateKey(168);
		IvParameterSpec ivParameterSpec = TripleDES.generateIv();
		TripleDES tripleDES = TripleDES.newTripleDESWithCBC(Padding.PKCS5PADDING, secretKey,ivParameterSpec);
		String test_str1 = RandomStringUtils.random(1);
		String test_str2 = new String(tripleDES.decrypt(tripleDES.encrypt(test_str1.getBytes())));
		assertEquals(test_str1, test_str2);
		
		String test_str3 = RandomStringUtils.random(168);
		String test_str4 = new String(tripleDES.decrypt(tripleDES.encrypt(test_str3.getBytes())));
		assertEquals(test_str3, test_str4);
		String test_str5 = RandomStringUtils.random(1004);
		String test_str6 = new String(tripleDES.decrypt(tripleDES.encrypt(test_str5.getBytes())));
		assertEquals(test_str5, test_str6);
		
		String test_str7 = RandomStringUtils.random(3001);
		String test_str8 = new String(tripleDES.decrypt(tripleDES.encrypt(test_str7.getBytes())));
		assertEquals(test_str7, test_str8);
	}
}
