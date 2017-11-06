package com.moontwon.knife.util;

import java.lang.reflect.Field;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.dom4j.Document;
import org.dom4j.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;
import com.moontwon.knife.crypto.OperationMode;
import com.moontwon.knife.crypto.Padding;
import com.moontwon.knife.crypto.RSA;

/**
 * 根据配置类中标有{@code Conf}注解的字段名字和节点名字，自动读取{@code Document}的对应的节点，自动读取转型设值
 * 支持{@code int}、{@code long}、{@code float}、{@code doule}、{@code boolean}、{@code java.lang.String}、{@code byte[]}(base64解码),{@
 * code com.moontwon.knife.crypto.RSA}
 * 
 * @author midzoon<br>
 *         magicsli@outlook.com<br>
 *         2017年5月12日
 */
public final class XmlAutoConfig {

	private final static Logger LOGGER = LoggerFactory.getLogger(XmlAutoConfig.class);
	/**
	 * 字段类型与赋值处理映射
	 */
	private static Map<Class<?>, TypeProcess> t = Maps.newConcurrentMap();
	/**
	 * 添加默认的字段赋值处理
	 */
	static {
		add(int.class, new TypeProcess() {
			@Override
			public void process(Config config, Field field, Class<?> type, String node) throws IllegalArgumentException, IllegalAccessException {
				field.setInt(config, Integer.parseInt(node));
			}
		});
		add(long.class, new TypeProcess() {
			@Override
			public void process(Config config, Field field, Class<?> type, String node) throws IllegalArgumentException, IllegalAccessException {
				field.setLong(config, Long.parseLong(node));
			}
		});
		add(float.class, new TypeProcess() {
			@Override
			public void process(Config config, Field field, Class<?> type, String node) throws IllegalArgumentException, IllegalAccessException {
				field.setFloat(config, Float.parseFloat(node));
			}
		});
		add(double.class, new TypeProcess() {
			@Override
			public void process(Config config, Field field, Class<?> type, String node) throws IllegalArgumentException, IllegalAccessException {
				field.setDouble(config, Double.parseDouble(node));
			}
		});
		add(boolean.class, new TypeProcess() {
			@Override
			public void process(Config config, Field field, Class<?> type, String node) throws IllegalArgumentException, IllegalAccessException {
				field.setBoolean(config, Boolean.parseBoolean(node));
			}
		});
		add(byte[].class, new TypeProcess() {
			@Override
			public void process(Config config, Field field, Class<?> type, String node) throws IllegalArgumentException, IllegalAccessException {
				field.set(config, Base64.decodeBase64(node));
			}
		});
		add(String.class, new TypeProcess() {
			@Override
			public void process(Config config, Field field, Class<?> type, String node) throws IllegalArgumentException, IllegalAccessException {
				field.set(config, node);
			}
		});
		add(RSA.class, new TypeProcess() {
			@Override
			public void process(Config config, Field field, Class<?> type, String node) throws IllegalArgumentException, IllegalAccessException {
				field.set(config, new RSA(OperationMode.ECB, Padding.PKCS1PADDING, RSA.pkcs8(node)));
			}
		});
	}
	/**
	 * dom4j文档
	 */
	private Document document;
	/**
	 * 根节点名字
	 */
	private String root;

	/**
	 * 
	 * @param document
	 *            dom4j文档
	 * @param root
	 *            根节点名字
	 */
	public XmlAutoConfig(Document document, String root) {
		this.document = document;
		this.root = root;
	}
	/**
	 * 对指定的对象的对应字段赋值
	 * 
	 * @param config
	 *            要配置的对象
	 * @return boolean {@code true}配置成功,{@code false}配置失败
	 */
	public boolean config(Config config) {
		Field[] fields = config.getClass().getDeclaredFields();
		final String nodeName = config.nodeName();
		for (Field field : fields) {
			Conf conf = field.getAnnotation(Conf.class);
			if (conf == null) {
				continue;
			}
			field.setAccessible(true);
			String name = conf.value();
			if ("".equals(name)) {
				name = field.getName();
			}
			Node node = document.selectSingleNode("/" + root + "//" + nodeName + "//" + name);
			if (node == null) {
				LOGGER.error("字段对应的值不存在[class name = {}, field name = {}]", config.getClass().getSimpleName(), name);
				return false;
			}
			Class<?> type = field.getType();
			String string = node.getStringValue();
			TypeProcess typeProcess = t.get(type);
			if (typeProcess != null) {
				try {
					typeProcess.process(config, field, type, string);
				} catch (IllegalArgumentException | IllegalAccessException e) {
					LOGGER.error("字段赋值出现异常[type = {},string = {} ]", type, string);
					return false;

				}
			} else {
				LOGGER.error("不存在该字段类型的赋值处理[class = {}]", type);
				return false;
			}
		}
		return true;
	}
	/**
	 * 添加赋值赋值,若指定字段类型已存在对应赋值处理则直接覆盖
	 * 
	 * @param clz
	 *            对应字段类型
	 * @param typeProcess
	 *            处理处理
	 */
	public static void add(Class<?> clz, TypeProcess typeProcess) {
		t.put(clz, typeProcess);
	}
	/**
	 * 判断是否指定字段类型的赋值处理
	 * 
	 * @param cls
	 *            字段类型
	 * @return boolean {@code true}存在，{@code false}不存在
	 */
	public static boolean contains(Class<?> cls) {
		return t.containsKey(cls);
	}
}
