package com.moontwon.knife.crypto;
/**
 * 分组加密，工作模式
 * 
 * 
 * @author hanlimin<br>
 * hanlimin.code@foxmail.com<br>
 * 2017年8月14日
 */
public enum OperationMode {
	ECB("ECB"),CBC("CBC"),PCBC("PCBC"),CFB("CFB"),OFB("OFB"),CTR("CTR");
	
	private String name;
	private OperationMode(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return name;
	}
}
