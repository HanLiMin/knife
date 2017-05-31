package com.moontwon.knife.codec;


import java.security.Key;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

/**
 * 加密解密工具
 * @author hanlimin<br>
 * magicsli@outlook.com<br>
 * 2017年1月16日
 */
public class Crypt {
	/**
	 * 3des加密
	 * <p>参数:DESede/CBC/PKCS5Padding</p>
	 * @param key 密钥
	 * @param iv 向量
	 * @param data 明文
	 * @return
	 * @throws Exception
	 * byte[]
	 */
	public static byte[] des3Encrypt(byte[] key,byte[] iv,byte[] data) throws Exception{
        DESedeKeySpec spec = new DESedeKeySpec(key);
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("DESede");
        Key deskey = keyfactory.generateSecret(spec);
        Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
        IvParameterSpec ips = new IvParameterSpec(iv);
        cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);
        return cipher.doFinal(data);
	}
	/**
	 * 3des解密
	 * <p>参数:DESede/CBC/PKCS5Padding</p>
	 * @param key 密钥
	 * @param iv 向量
	 * @param data 密文
	 * @return
	 * @throws Exception
	 * byte[]
	 */
	public static byte[] des3Decrypt(byte[] key,byte[] iv,byte[] data) throws Exception{
		DESedeKeySpec spec = new DESedeKeySpec(key);
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("DESede");
        Key deskey = keyfactory.generateSecret(spec);
        Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
        IvParameterSpec ips = new IvParameterSpec(iv);
        cipher.init(Cipher.DECRYPT_MODE, deskey, ips);
        return cipher.doFinal(data);
	}
	/**
	 * RSA公钥加密
	 * <p>参数:RSA/ECB/PKCS1Padding</p>
	 * @param key 公钥
	 * @param data 明文
	 * @return
	 * @throws Exception
	 * byte[]
	 */
	public static byte[] rsaEncryptByPublicKey(byte[] key,byte[] data) throws Exception{
		X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(key);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		Key publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(data);
	}
	/**
	 * RSA公钥解密
	 * @param key
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static byte[] rsaDecrytByPublicKey(byte[] key,byte[] data) throws Exception{
	    X509EncodedKeySpec x509KeySpec=new X509EncodedKeySpec(key);
        KeyFactory keyFactory=KeyFactory.getInstance("RSA");
        PublicKey publicKey=keyFactory.generatePublic(x509KeySpec);
        Cipher cipher=Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        return cipher.doFinal(data);
	}
	/**
	 * RSA私钥加密
	 * <p>参数:RSA/ECB/PKCS1Padding</p>
	 * @param key
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static byte[] rsaEncryptByPrivateKey(byte[] key,byte[] data) throws Exception{
        PKCS8EncodedKeySpec pkcs8KeySpec=new PKCS8EncodedKeySpec(key);
        KeyFactory keyFactory=KeyFactory.getInstance("RSA");
        PrivateKey privateKey=keyFactory.generatePrivate(pkcs8KeySpec);
        Cipher cipher=Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        return cipher.doFinal(data);
	}
	/**
	 * RSA私钥解密
	 * <p>参数:RSA/ECB/PKCS1Padding</p>
	 * @param key 私钥
	 * @param data 明文
	 * @return
	 * @throws Exception
	 * byte[]
	 */
	public static byte[] rsaDecrytByPrivateKey(byte[] key,byte[] data) throws Exception{
	    PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(key);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        Key privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(data);
	}
}
