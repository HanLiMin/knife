package com.moontwon.knife.util;

public class StringUtils {
    public final static String EXCLAMATION_MARK = "!";
    public final static String DOT = ".";
    private int[] a;
    public static void main(String[] args) throws NoSuchFieldException, SecurityException {
		System.err.println(StringUtils.class.getDeclaredField("a").getType());
	}
}
