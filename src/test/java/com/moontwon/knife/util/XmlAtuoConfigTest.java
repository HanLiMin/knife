package com.moontwon.knife.util;

import java.io.InputStream;
import java.util.Arrays;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.junit.Test;

@Config
public class XmlAtuoConfigTest {
	@Config
	private String url;
	@Config
	private String username;
	@Config
	private String password;
	@Config
	private String lip;
	@Config
	private int lport;
	@Config
	private String rip;
	@Config
	private int rport;
	@Config
	private int open;
	@Config
	private int close;
	@Config
	private int[] dddd;
	@Test
	public void test(){
		String string="12...23";
		System.err.println(Arrays.toString(string.split("\\.\\.\\.")) );
	}
	public static void main(String[] args) throws DocumentException, InterruptedException {
		XmlAtuoConfigTest xmlAtuoConfigTest = new XmlAtuoConfigTest();
		InputStream inputStream = XmlAtuoConfigTest.class.getClassLoader().getResourceAsStream("config.xml");
		System.err.println(inputStream);
		SAXReader saxReader = new SAXReader();
		Document document= saxReader.read(inputStream);
		System.err.println(document.asXML());
		XmlAutoConfig xmlAutoConfig = new XmlAutoConfig(document);
		System.err.println(xmlAutoConfig.config(xmlAtuoConfigTest));
		System.err.println(xmlAtuoConfigTest);
	}
	@Override
	public String toString() {
		return "XmlAtuoConfigTest [url=" + url + ", username=" + username + ", password=" + password + ", lip=" + lip + ", lport=" + lport + ", rip="
				+ rip + ", rport=" + rport + ", open=" + open + ", close=" + close + ", ddd=" + Arrays.toString(dddd) + "]";
	}
	
	
	
}
