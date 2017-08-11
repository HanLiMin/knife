package com.moontwon.knife.crypto;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

/**
 * AES加解密工具
 * 
 * @author hanlimin<br>
 *         hanlimin.code@foxmail.com<br>
 *         2017年8月10日
 */
public class AES extends AbstraceCipher {
	private CipherName cipherName = CipherName.AES;
	private OperationMode operationMode;
	private Padding padding;
	private SecretKey secretKey;
	private Cipher cipher;

	/**
	 * 构造AES加解密工具
	 * 
	 * @param operationMode
	 *            分组模式
	 * @param padding
	 *            填充方式
	 * @param secretKey
	 *            密钥
	 */
	public AES(OperationMode operationMode, Padding padding, SecretKey secretKey) {
		this.operationMode = operationMode;
		this.padding = padding;
		this.secretKey = secretKey;
		try {
			cipher = Cipher.getInstance(transformation(cipherName, operationMode, padding));
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * 加密数据
	 * 
	 * @param data
	 *            待加密的数据
	 * @return byte[] 已加密的数据
	 */
	public byte[] encrypt(byte[] data) {
		try {
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			return cipher.doFinal(data);
		} catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * 解密数据
	 * 
	 * @param data
	 *            待解密的数据
	 * @return byte[] 解密完成的明文
	 */
	public byte[] decrypt(byte[] data) {
		try {
			cipher.init(Cipher.DECRYPT_MODE, secretKey);
			return cipher.doFinal(data);
		} catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	String name() {
		return cipherName.name();
	}
	public SecretKey getSecretKey() {
		return secretKey;
	}
	/**
	 * 根据指定密钥长度生成AES密钥
	 * 
	 * @param length
	 *            密钥长度 128、192、256
	 * @return SecretKey AES密钥
	 */
	public static SecretKey generateKey(int length) {
		KeyGenerator kgen;
		try {
			kgen = KeyGenerator.getInstance(CipherName.AES.name());
			SecureRandom secureRandom = new SecureRandom();
			kgen.init(length, secureRandom);
			return kgen.generateKey();
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public String toString() {
		return "AES[name=" + cipherName.name() + ",operationMode=" + operationMode + ",padding=" + padding + "]";
	}
}
