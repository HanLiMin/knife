package com.moontwon.knife.crypto;
/**
 * 
 * @author hanlimin<br>
 *         hanlimin.code@foxmail.com<br>
 *         2017年8月2日
 */
public enum CipherName {
	DESEDE("DESede"), AES("AES"), RSA("RSA");
	/**
	 * 
	 */
	private String name;

	private CipherName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return name;
	}
}
