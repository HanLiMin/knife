package com.moontwon.knife.crypto;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.RandomUtils;

/**
 * 3DES加解密工具
 * 
 * @author hanlimin<br>
 *         hanlimin.code@foxmail.com<br>
 *         2017年8月10日
 */
public class TripleDES extends AbstraceCipher {
	private CipherName cipherName = CipherName.DESEDE;
	private OperationMode operationMode;
	private Padding padding;
	private SecretKey secretKey;
	private IvParameterSpec ivParameterSpec;
	private Cipher cipher;
	/**
	 * 构造3DES加密工具
	 * 
	 * @param operationMode
	 *            分组模式
	 * @param padding
	 *            填充方式
	 * @param secretKey
	 *            密钥
	 * @param ivParameterSpec
	 *            向量
	 */
	private TripleDES(OperationMode operationMode, Padding padding, SecretKey secretKey, IvParameterSpec ivParameterSpec) {
		this.operationMode = operationMode;
		this.padding = padding;
		this.secretKey = secretKey;
		switch (operationMode) {
			case CBC :
				this.ivParameterSpec = ivParameterSpec;
				break;
			case ECB :
				break;
			default :
				throw new IllegalArgumentException("不支持的分组模式" + padding.name());
		}
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
			switch (operationMode) {
				case CBC :
					cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec);
					break;
				case ECB :
					cipher.init(Cipher.ENCRYPT_MODE, secretKey);
					break;
				default :
					break;
			}
			return cipher.doFinal(data);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * 解密数据
	 * @param data 待解密的数据
	 * @return
	 * byte[]  解密完成的明文
	 */
	public byte[] decrypt(byte[] data) {
		try {
			switch (operationMode) {
				case CBC :
					cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);
					break;
				case ECB :
					cipher.init(Cipher.DECRYPT_MODE, secretKey);
					break;
				default :
					break;
			}
			return cipher.doFinal(data);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * 创建CBC分组模式的3DES加密工具
	 * 
	 * @param padding
	 *            填充方式
	 * @param secretKey
	 *            密钥
	 * @param ivParameterSpec
	 *            向量
	 * @return TripleDES 3DES加密工具
	 */
	public static TripleDES newTripleDESWithCBC(Padding padding, SecretKey secretKey, IvParameterSpec ivParameterSpec) {
		return new TripleDES(OperationMode.CBC, padding, secretKey, ivParameterSpec);
	}
	/**
	 * 创建EBC分组模式的3DES加密工具
	 * 
	 * @param padding
	 *            填充方式
	 * @param secretKey
	 *            密钥
	 * @return TripleDES 3DES加密工具
	 */
	public static TripleDES newTripleDESWithEBC(Padding padding, SecretKey secretKey) {
		return new TripleDES(OperationMode.ECB, padding, secretKey, null);
	}
	/**
	 * 
	 * 生成密钥
	 * 
	 * @param length
	 *            112或168
	 * @return SecretKey
	 */
	public static SecretKey generateKey(int length) {
		SecureRandom secureRandom = new SecureRandom();
		KeyGenerator keyGenerator = null;
		try {
			keyGenerator = KeyGenerator.getInstance(CipherName.DESEDE.name());
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
		keyGenerator.init(length, secureRandom);
		SecretKey secretKey = keyGenerator.generateKey();
		return secretKey;
	}
	/**
	 * 生成一个8位随机向量
	 * 
	 * @return IvParameterSpec 随机向量
	 */
	public static IvParameterSpec generateIv() {
		return new IvParameterSpec(RandomUtils.nextBytes(8));
	}
	
	/**
	 * 
	 * @param base64
	 * @return
	 * SecretKey
	 */
	public static SecretKey key(String base64) {
		try {
			SecretKeyFactory keyfactory = SecretKeyFactory.getInstance(CipherName.DESEDE.name());
			byte[] key = Base64.decodeBase64(base64);
			DESedeKeySpec deSedeKeySpec = new DESedeKeySpec(key);
			return keyfactory.generateSecret(deSedeKeySpec);
		} catch (NoSuchAlgorithmException | InvalidKeyException | InvalidKeySpecException e) {
			throw new RuntimeException(e);
		}
	}
	@Override
	String name() {
		return cipherName.name();
	}
	@Override
	public String toString() {
		return "TripleDES[name=" + cipherName.name() + ",operationMode=" + operationMode + ",padding=" + padding + "]";
	}
}
