package com.moontwon.knife.util;

import java.io.FileInputStream;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;

import org.junit.Test;

public class CrtTest {
	@Test
	public void test() {
		try {
			FileInputStream fileInputStream = new FileInputStream("C:\\Users\\yj\\Desktop\\server.crt");
			CertificateFactory factory = CertificateFactory.getInstance("X.509");
			Certificate certificate= factory.generateCertificate(fileInputStream);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
