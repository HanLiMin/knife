package com.moontwon.knife.crypto;

public abstract class AbstraceCipher implements Cipher {
	abstract String name();
	/**
	 * 指定算法名字、工作模式、填充方式获取
	 * @param cipherName 算法名字
	 * @param operationMode 工作模式
	 * @param padding 填充方式
	 * @return
	 * String
	 */
	protected String transformation(CipherName cipherName, OperationMode operationMode, Padding padding) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(cipherName).append("/").append(operationMode).append("/").append(padding);
		return stringBuilder.toString();
	}
}
