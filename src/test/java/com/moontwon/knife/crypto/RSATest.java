package com.moontwon.knife.crypto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.InputStream;
import java.security.KeyPair;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

import com.moontwon.knife.crypto.OperationMode;
import com.moontwon.knife.crypto.Padding;
import com.moontwon.knife.crypto.RSA;
import com.moontwon.knife.util.ClassUtils;

public class RSATest {

	@Test
	public void genTest() {
		KeyPair keyPair_1024 = RSA.generateKey(1024);
		assertNotNull(keyPair_1024);
		assertNotNull(keyPair_1024.getPrivate());
		assertNotNull(keyPair_1024.getPublic());
		KeyPair keyPair_2048 = RSA.generateKey(2048);
		assertNotNull(keyPair_2048);
		assertNotNull(keyPair_2048.getPrivate());
		assertNotNull(keyPair_2048.getPublic());
	}
	@Test
	public void encryptByPrivateKeyTest() {
		KeyPair keyPair_1024 = RSA.generateKey(1024);
		RSA rsaCipher = new RSA(OperationMode.ECB, Padding.PKCS1PADDING, keyPair_1024);
		String test_str1 = RandomStringUtils.random(1);
		String test_str2 = new String(rsaCipher.decryptByPublicKey(rsaCipher.encryptByPrivateKey(test_str1.getBytes())));
		assertEquals(test_str1, test_str2);

		String test_str3 = RandomStringUtils.random(244);
		String test_str4 = new String(rsaCipher.decryptByPublicKey(rsaCipher.encryptByPrivateKey(test_str3.getBytes())));
		assertEquals(test_str3, test_str4);

		String test_str5 = RandomStringUtils.random(127);
		String test_str6 = new String(rsaCipher.decryptByPublicKey(rsaCipher.encryptByPrivateKey(test_str5.getBytes())));
		assertEquals(test_str5, test_str6);

		String test_str7 = RandomStringUtils.random(1021);
		String test_str8 = new String(rsaCipher.decryptByPublicKey(rsaCipher.encryptByPrivateKey(test_str7.getBytes())));
		assertEquals(test_str7, test_str8);

		String test_str9 = RandomStringUtils.random(3001);
		String test_str10 = new String(rsaCipher.decryptByPublicKey(rsaCipher.encryptByPrivateKey(test_str9.getBytes())));
		assertEquals(test_str9, test_str10);
	}
	@Test
	public void encryptByPublicKeyTest() {
		KeyPair keyPair_1024 = RSA.generateKey(1024);
		RSA rsaCipher = new RSA(OperationMode.ECB, Padding.PKCS1PADDING, keyPair_1024);
		String test_str1 = RandomStringUtils.random(1);
		String test_str2 = new String(rsaCipher.decryptPrivateKey(rsaCipher.encryptByPublicKey(test_str1.getBytes())));
		assertEquals(test_str1, test_str2);

		String test_str3 = RandomStringUtils.random(244);
		String test_str4 = new String(rsaCipher.decryptPrivateKey(rsaCipher.encryptByPublicKey(test_str3.getBytes())));
		assertEquals(test_str3, test_str4);

		String test_str5 = RandomStringUtils.random(127);
		String test_str6 = new String(rsaCipher.decryptPrivateKey(rsaCipher.encryptByPublicKey(test_str5.getBytes())));
		assertEquals(test_str5, test_str6);

		String test_str7 = RandomStringUtils.random(1021);
		String test_str8 = new String(rsaCipher.decryptPrivateKey(rsaCipher.encryptByPublicKey(test_str7.getBytes())));
		assertEquals(test_str7, test_str8);

		String test_str9 = RandomStringUtils.random(3001);
		String test_str10 = new String(rsaCipher.decryptPrivateKey(rsaCipher.encryptByPublicKey(test_str9.getBytes())));
		assertEquals(test_str9, test_str10);
	}
	@Test
	public void pemTest() {
		try {
			String pkcs8 = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAKyqXaZTR2OCsGbXiRHQyiAZG7BrKSIsHIYFCoFdkM6Poyq9GFBQUg8vGN3zAoAXYtgofQ/1HuwcvlnQicEayyAzeZKDL4sdEWfwmB7RSq3Z6oUyPHcBmm/L68U+NmWopgvR1Y2X09ZrCLZM51VlwEqZWVP3nY+UKBgvGauP7TCjAgMBAAECgYAodGYqwRpZ01X3UoqX9P5WlOtq40Co9ALe17GFa+Gws/C748AdmSQQ0K0X24b7cf9DvADJS1oqYfWrX3PXHar3htwGPcR4h8GQmVCKs2Yb5hWfdWRG2ZI12LEYLVvNr6jq2jq8puGn2t4ZAYxQmc7IRcbskgILDEb8vvfGK6xSuQJBANtmVN038uxBnj7+J6JmmluE41D1tanNnF7RibBxI6thhv9GDYAjTLarcEGsmY8iJkqcbyCrIyDdy8qRc9sK3C8CQQDJeDSQw9skWyTb2J540XLODC1xHGdzF2Bj7w3ZGGrckEjzWEBC7o//dMzPcB2EonmozBfS871jVZ5NrsLccFHNAkEAv+9c7I6GmMKq4qbLG5qxDMFLb8cUnrD+TED5OH8x2jses3GgW+oFsYR3Vyzcxcf1Qa5q1WSB/b2rC9KFTSnKBwJAI8k3RROVBGMwK+DRXVKO2uys0a+i7H2VgIrA4fQFX0I9wwPE/l6Ts2OS0bHvfFTD2WjPPohO9qsJoHDPagqFbQJAe3wjkQKzPYR95KbJDhMD/xJpvQlyYKh81Swqr49ew/z1o81u5dyYmVe7NuU3SusDuIUFSRcqIpLKPHMl7VgjHA==";
			String pk = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCsql2mU0djgrBm14kR0MogGRuwaykiLByGBQqBXZDOj6MqvRhQUFIPLxjd8wKAF2LYKH0P9R7sHL5Z0InBGssgM3mSgy+LHRFn8Jge0Uqt2eqFMjx3AZpvy+vFPjZlqKYL0dWNl9PWawi2TOdVZcBKmVlT952PlCgYLxmrj+0wowIDAQAB";
			RSAPrivateKey rsaPrivateKey = RSA.pkcs8(pkcs8);
			RSAPublicKey rsaPublicKey = RSA.x509(pk);
			assertNotNull(rsaPrivateKey);
			assertNotNull(rsaPublicKey);
		} catch (Exception e) {
			assertNull(e);
		}
	}
	@Test
	public void crtTest(){
		InputStream inputStream = ClassUtils.CLASS_LOADER.getResourceAsStream("test.crt");
		try {
			PublicKey publicKey = RSA.crt(inputStream);
			assertNotNull(publicKey);
		} catch (Exception e) {
			assertNull(e);
		}
	}
}
