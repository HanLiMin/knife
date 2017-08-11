package com.moontwon.knife.crypto;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.SignatureException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;

import com.google.common.base.Preconditions;

/**
 * RSA加解密工具，是对JDK内的api的封装
 * 
 * 
 * @author hanlimin<br>
 *         hanlimin.code@foxmail.com<br>
 *         2017年8月2日
 */
public class RSA extends AbstraceCipher {

	private CipherName cipherName = CipherName.RSA;
	private OperationMode operationMode;
	private Padding padding;
	/**
	 * 密钥字节数
	 */
	private int length;
	private RSAPublicKey rsaPublicKey;
	private RSAPrivateKey rsaPrivateKey;
	private Cipher cipher;
	/**
	 * <p>
	 * 构造一个RSA加解密工具
	 * </p>
	 * <p>
	 * {@code operationMode}指定了分组加密的工作模式,{@code padding}指定了填充方式,{@code keyPair}指定了RSA的密钥对
	 * </p>
	 * 
	 * @param operationMode
	 *            分组加密的工作模式
	 * @param padding
	 *            填充方式
	 * @param keyPair
	 *            RSA密钥对
	 * @exception NoSuchAlgorithmException
	 */
	public RSA(OperationMode operationMode, Padding padding, KeyPair keyPair) {
		this(operationMode, padding, (RSAPublicKey) keyPair.getPublic(), (RSAPrivateKey) keyPair.getPrivate());
	}
	/**
	 * <p>
	 * 构造一个只包含RSA公钥的RSA加解密工具
	 * </p>
	 * <p>
	 * {@code operationMode}指定了分组加密的工作模式,{@code padding}指定了填充方式,{@code rsaPublicKey}指定了RSA的公钥
	 * </p>
	 * 
	 * @param operationMode
	 *            分组加密的工作模式
	 * @param padding
	 *            填充方式
	 * @param rsaPublicKey
	 *            RSA的私钥
	 * @exception NoSuchAlgorithmException
	 */
	public RSA(OperationMode operationMode, Padding padding, RSAPublicKey rsaPublicKey) {
		this(operationMode, padding, rsaPublicKey, null);
	}
	/**
	 * <p>
	 * 构造一个只包含RSA私钥的RSA加解密工具
	 * </p>
	 * <p>
	 * {@code operationMode}指定了分组加密的工作模式,{@code padding}指定了填充方式,{@code rsaPrivateKey}指定了RSA的私钥
	 * </p>
	 * 
	 * @param operationMode
	 *            分组加密的工作模式
	 * @param padding
	 *            填充方式
	 * @param rsaPrivateKey
	 *            RSA的私钥
	 * @exception NoSuchAlgorithmException
	 */
	public RSA(OperationMode operationMode, Padding padding, RSAPrivateKey rsaPrivateKey) {
		this(operationMode, padding, null, rsaPrivateKey);
	}
	/**
	 * <p>
	 * 构造一个RSA加解密工具
	 * </p>
	 * <p>
	 * {@code operationMode}指定了分组加密的工作模式,{@code padding}指定了填充方式,{@code rsaPublicKey}指定了RSA的公钥,{@code rsaPrivateKey}指定了RSA的私钥
	 * </p>
	 * 
	 * @param operationMode
	 *            分组加密的工作模式
	 * @param padding
	 *            填充方式
	 * @param rsaPublicKey
	 *            RSA的公钥
	 * @param rsaPrivateKey
	 *            RSA的私钥
	 * @exception NoSuchAlgorithmException
	 *
	 */
	public RSA(OperationMode operationMode, Padding padding, RSAPublicKey rsaPublicKey, RSAPrivateKey rsaPrivateKey) {
		this.operationMode = operationMode;
		this.padding = padding;
		this.rsaPublicKey = rsaPublicKey;
		this.rsaPrivateKey = rsaPrivateKey;
		try {
			cipher = Cipher.getInstance(transformation(cipherName, operationMode, padding));
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
			throw new RuntimeException(e);
		}
		if (rsaPublicKey != null) {
			length = rsaPublicKey.getModulus().bitLength() / 8 - padding.length();
		} else {
			length = rsaPrivateKey.getModulus().bitLength() / 8 - padding.length();

		}
	}
	/**
	 * 使用私钥加密数据
	 * 
	 * @param data
	 *            要进行加密的明文
	 * @return byte[] 加密过后的密文
	 * @exception IllegalStateException
	 *                没有私钥
	 * 
	 */
	public byte[] encryptByPrivateKey(byte[] data) {
		Preconditions.checkState(rsaPublicKey != null, "rsaPrivateKey为空");
		if (data.length <= length) {
			try {
				cipher.init(Cipher.ENCRYPT_MODE, rsaPrivateKey);
				return cipher.doFinal(data);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		} else {
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
			int j = data.length / length;
			j = j % length == 0 ? j : j + 1;
			try {
				cipher.init(Cipher.ENCRYPT_MODE, rsaPrivateKey);
				for (int i = 0; i < j; i++) {
					int to = (i + 1) * length;
					to = to > data.length ? data.length : to;
					byte[] text = Arrays.copyOfRange(data, i * length, to);
					cipher.update(text);
					outputStream.write(cipher.doFinal());
				}

			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			return outputStream.toByteArray();
		}
	}
	/**
	 * 使用公钥解密数据
	 * 
	 * @param data
	 *            要解密的数据
	 * @return byte[] 解密后的数据
	 * @exception IllegalStateException
	 *                没有公钥
	 */
	public byte[] decryptByPublicKey(byte[] data) {
		Preconditions.checkState(rsaPrivateKey != null, "rsaPublicKey为空");
		final int l = length + padding.length();
		if (data.length <= l) {
			try {
				cipher.init(Cipher.DECRYPT_MODE, rsaPublicKey);
				return cipher.doFinal(data);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		} else {
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream(l);
			int j = data.length / l;
			try {
				cipher.init(Cipher.DECRYPT_MODE, rsaPublicKey);
				for (int i = 0; i < j; i++) {
					byte[] sign = Arrays.copyOfRange(data, i * l, (i + 1) * l);
					cipher.update(sign);
					outputStream.write(cipher.doFinal());
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			return outputStream.toByteArray();
		}

	}

	/**
	 * 使用公钥加密数据
	 * 
	 * @param data
	 *            要进行加密的数据
	 * @return byte[] 已经加密后的数据
	 * @exception IllegalStateException
	 *                没有公钥
	 */
	public byte[] encryptByPublicKey(byte[] data) {
		Preconditions.checkState(rsaPublicKey != null, "rsaPrivateKey为空");
		if (data.length <= length) {
			try {
				cipher.init(Cipher.ENCRYPT_MODE, rsaPublicKey);
				return cipher.doFinal(data);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		} else {
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
			int j = data.length / length;
			j = j % length == 0 ? j : j + 1;
			try {
				cipher.init(Cipher.ENCRYPT_MODE, rsaPublicKey);
				for (int i = 0; i < j; i++) {
					int to = (i + 1) * length;
					to = to > data.length ? data.length : to;
					byte[] text = Arrays.copyOfRange(data, i * length, to);
					cipher.update(text);
					outputStream.write(cipher.doFinal());
				}

			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			return outputStream.toByteArray();
		}
	}

	/**
	 * 使用私钥解密数据
	 * 
	 * @param data
	 *            要进行解密的数据
	 * @return byte[] 解密后的数据
	 * @exception IllegalStateException
	 *                没有私钥
	 */
	public byte[] decryptPrivateKey(byte[] data) {
		Preconditions.checkState(rsaPrivateKey != null, "rsaPublicKey为空");
		final int l = length + padding.length();
		if (data.length <= l) {
			try {
				cipher.init(Cipher.DECRYPT_MODE, rsaPrivateKey);
				return cipher.doFinal(data);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		} else {
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream(l);
			int j = data.length / l;
			try {
				cipher.init(Cipher.DECRYPT_MODE, rsaPrivateKey);
				for (int i = 0; i < j; i++) {
					byte[] sign = Arrays.copyOfRange(data, i * l, (i + 1) * l);
					cipher.update(sign);
					outputStream.write(cipher.doFinal());
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			return outputStream.toByteArray();
		}

	}

	/**
	 * 根据指定长度生成RSA的公钥和私钥对
	 * 
	 * @param length
	 * @return KeyPair 储存着生成的公钥和私钥
	 */
	public static KeyPair generateKey(int length) {
		KeyPairGenerator keyPairGen = null;
		SecureRandom secureRandom = new SecureRandom();
		try {
			keyPairGen = KeyPairGenerator.getInstance(CipherName.RSA.name());

		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
		keyPairGen.initialize(length, secureRandom);
		KeyPair keyPair = keyPairGen.generateKeyPair();
		return keyPair;
	}
	/**
	 * 由pkcs8编码的base64字符串生成RSA私钥
	 * 
	 * @param base64
	 *            pkcs8编码的base64字符串
	 * @return RSAPrivateKey RSA私钥，若出现错误则为{@code null}
	 */
	public static RSAPrivateKey pkcs8(String base64) {

		byte[] pkcs8 = Base64.decodeBase64(base64);
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(pkcs8);
		KeyFactory kf = null;
		PrivateKey privKey = null;
		try {
			kf = KeyFactory.getInstance(CipherName.RSA.name());
			privKey = kf.generatePrivate(keySpec);
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			throw new RuntimeException(e);
		}
		return (RSAPrivateKey) privKey;
	}
	/**
	 * 由符合x509编码的base64字符串生成RSA公钥
	 * 
	 * @param base64
	 *            x509编码的base64字符串
	 * @return RSAPublicKey RSA公钥，若出现错误则为{@code null}
	 */
	public static RSAPublicKey x509(String base64) {
		byte[] x509 = Base64.decodeBase64(base64);
		X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(x509);
		KeyFactory kf = null;
		PublicKey publicKey = null;
		try {
			kf = KeyFactory.getInstance(CipherName.RSA.name());
			publicKey = kf.generatePublic(x509EncodedKeySpec);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return (RSAPublicKey) publicKey;
	}
	/**
	 * 获取自签名证书中的RSA公钥,如果文件不存在、证书过期、签名验证失败则抛出异常
	 * 
	 * @param file
	 *            指向证书的文件
	 * @return RSAPublicKey
	 * @throws FileNotFoundException
	 * @throws CertificateException
	 * @throws SignatureException
	 * @throws NoSuchProviderException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 */
	public static RSAPublicKey crt(File file) throws FileNotFoundException, CertificateException, InvalidKeyException, NoSuchAlgorithmException,
			NoSuchProviderException, SignatureException {
		Preconditions.checkArgument(file != null, "file为null");
		Preconditions.checkArgument(!file.exists(), "file不存在");
		FileInputStream fileInputStream = new FileInputStream(file);
		return crt(fileInputStream);
	}
	/**
	 * 获取自签名证书中的RSA公钥,如果证书过期、签名验证失败则抛出异常
	 * 
	 * @param inputStream
	 *            来自证书的输入流
	 * @return RSAPublicKey RSA公钥
	 * @throws CertificateException
	 * @throws InvalidKeyException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchProviderException
	 * @throws SignatureException
	 *             RSAPublicKey
	 */
	public static RSAPublicKey crt(InputStream inputStream)
			throws CertificateException, InvalidKeyException, NoSuchAlgorithmException, NoSuchProviderException, SignatureException {
		Preconditions.checkArgument(inputStream != null, "inputStream为null");
		CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
		X509Certificate x509Certificate = (X509Certificate) certificateFactory.generateCertificate(inputStream);
		x509Certificate.checkValidity();
		PublicKey publicKey = x509Certificate.getPublicKey();
		x509Certificate.verify(publicKey);
		IOUtils.closeQuietly(inputStream);
		return (RSAPublicKey) publicKey;
	}

	@Override
	public String toString() {
		return "RSACipher[name=" + cipherName.name() + ",operationMode=" + operationMode + ",padding=" + padding + ",length="
				+ (length + padding.length()) + "]";
	}
	@Override
	String name() {
		return cipherName.name();
	}
}
