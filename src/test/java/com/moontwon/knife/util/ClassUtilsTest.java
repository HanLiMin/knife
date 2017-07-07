package com.moontwon.knife.util;

import java.io.IOException;
import java.util.Arrays;
import java.util.Set;

import org.junit.Test;

public class ClassUtilsTest {
//    @Test
    public void getPackageAllClassesTest() throws IOException {
        Set<Class<?>> set = ClassUtils.getPackageAllClasses("com.google.common.io", true);
        System.err.println(Arrays.toString(set.toArray()));
    }

    @Test
    public void test01() throws Exception {
        Set<Class<?>> set = ClassUtils.getPackageAllClasses("com.moontwon.knife.util", false);
        System.err.println(Arrays.toString(set.toArray()));
    }
}
