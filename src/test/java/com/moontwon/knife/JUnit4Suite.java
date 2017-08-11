package com.moontwon.knife;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.moontwon.knife.crypto.AESTest;
import com.moontwon.knife.crypto.RSATest;
import com.moontwon.knife.crypto.TripleDESTest;
import com.moontwon.knife.util.TreeIntervalTest;

@RunWith(Suite.class)
@SuiteClasses({TreeIntervalTest.class,RSATest.class,TripleDESTest.class,AESTest.class})
public class JUnit4Suite {

}
