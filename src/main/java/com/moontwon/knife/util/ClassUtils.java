package com.moontwon.knife.util;

import java.io.File;
import java.io.FileFilter;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 类工具包
 * 
 * @author hanlimin magicsli@outlook.com 2017年6月11日
 */
public class ClassUtils {

    private final static Logger LOGGER = LoggerFactory.getLogger(ClassUtils.class);
    public final static ClassLoader CLASS_LOADER = Thread.currentThread().getContextClassLoader();

    /**
     * 获取指定包下的所有类
     * 
     * @param packageName
     *            包的名字
     * @param recursive
     *            是否递归查找
     * @return 包含了指定包下的所有类
     */
    public static Set<Class<?>> getPackageAllClasses(String packageName, final boolean recursive) {
        Set<Class<?>> classes = new LinkedHashSet<>();
        String packagePath = packageName.replace(".", "/");
        try {
            URL url = CLASS_LOADER.getResource(packagePath);
            String protocl = url.getProtocol();
            if ("jar".equals(protocl)) {
                JarURLConnection connection = (JarURLConnection) url.openConnection();
                JarFile jarFile = connection.getJarFile();
                Enumeration<JarEntry> jarEntrys = jarFile.entries();
                while (jarEntrys.hasMoreElements()) {
                    JarEntry jarEntry = jarEntrys.nextElement();
                    String name = jarEntry.getName();
                    if (name.startsWith(packagePath)) {
                        // 去除文件夹
                        if (name.charAt(name.length() - 1) != '/') {
                            String className = classPahtToClassName(name);
                            classes.add(CLASS_LOADER.loadClass(className));
                        }
                    }
                }
            } else if ("file".equals(protocl)) {
                findAndAddClassesInPackageByFile(url.getFile(), packageName,recursive, classes);
            }

        } catch (Exception e) {
            LOGGER.debug("出现错误", e);
        }
        return classes;
    }

    private static void findAndAddClassesInPackageByFile(String packagePath,String pacakgeName, final boolean recursive,
            Set<Class<?>> classes) {
        // 获取此包的目录 建立一个File
        File dir = new File(packagePath);
        if (!dir.exists() || !dir.isDirectory()) {
            return;
        }
        File[] dirfiles = dir.listFiles(new FileFilter() {
            public boolean accept(File file) {
                return (recursive && file.isDirectory()) || (file.getName().endsWith(".class"));
            }
        });
        // 循环所有文件
        for (File file : dirfiles) {
            String fileName = file.getName();
            // 如果是目录 则继续扫描
            if (file.isDirectory()) {
                findAndAddClassesInPackageByFile(packagePath + "/" + fileName,pacakgeName, recursive, classes);
            } else {
                String classPath = packagePath+'/'+fileName;
                String prefix = classPath.substring(0, classPath.indexOf(pacakgeName.replace('.', '/')));
                classPath = classPath.substring(prefix.length(),classPath.length());
                try {
                    classes.add(CLASS_LOADER.loadClass(classPahtToClassName(classPath)));
                } catch (ClassNotFoundException e) {
                    LOGGER.debug("出现错误", e);
                }
            }
        }
    }

    /**
     * 类的文件路径转化成类的二进制名字
     * 
     * @param classPath
     *            类的文件路径
     * @return 类的二进制名字
     */
    public static String classPahtToClassName(String classPath) {
        // 文件路径转换成类的二进制路径
        String className = classPath.replace('/', '.');
        // 去除.class
        className = className.substring(0, className.length() - 6);
        return className;
    }
}
