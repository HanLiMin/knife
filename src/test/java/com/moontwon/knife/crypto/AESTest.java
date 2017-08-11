package com.moontwon.knife.crypto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.crypto.SecretKey;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

public class AESTest {
	@Test
	public void generateKeyTest() {
		SecretKey secretKey_128 = AES.generateKey(128);
		assertNotNull(secretKey_128);
		SecretKey secretKey_192 = AES.generateKey(192);
		assertNotNull(secretKey_192);
		SecretKey secretKey_256 = AES.generateKey(256);
		assertNotNull(secretKey_256);
	}

	@Test
	public void encryptTest() {
		SecretKey secretKey = AES.generateKey(256);
		AES aes = new AES(OperationMode.ECB, Padding.PKCS5PADDING, secretKey);
		System.err.println(aes);
		String test_str1 = RandomStringUtils.random(1);
		String test_str2 = new String(aes.decrypt(aes.encrypt(test_str1.getBytes())));
		assertEquals(test_str1, test_str2);

		String test_str3 = RandomStringUtils.random(244);
		String test_str4 = new String(aes.decrypt(aes.encrypt(test_str3.getBytes())));
		assertEquals(test_str3, test_str4);

		String test_str5 = RandomStringUtils.random(127);
		String test_str6 = new String(aes.decrypt(aes.encrypt(test_str5.getBytes())));
		assertEquals(test_str5, test_str6);

		String test_str7 = RandomStringUtils.random(1021);
		String test_str8 = new String(aes.decrypt(aes.encrypt(test_str7.getBytes())));
		assertEquals(test_str7, test_str8);

		String test_str9 = RandomStringUtils.random(3001);
		String test_str10 = new String(aes.decrypt(aes.encrypt(test_str9.getBytes())));
		assertEquals(test_str9, test_str10);
	}
}
