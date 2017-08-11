package com.moontwon.knife.crypto;
/**
 * 填充方式
 * 
 * 
 * 
 * @author hanlimin<br>
 *         hanlimin.code@foxmail.com<br>
 *         2017年8月2日
 */
public enum Padding {
	NOPADDING("NoPadding", 0), PKCS1PADDING("PKCS1Padding", 11), PKCS5PADDING("PKCS5Padding", 0);
	/**
	 * 名字
	 */
	private String name;
	/**
	 * 长度
	 */
	private int length;
	private Padding(String name, int length) {
		this.name = name;
		this.length = length;
	}
	@Override
	public String toString() {
		return name;
	}
	public int length() {
		return length;
	}
}
