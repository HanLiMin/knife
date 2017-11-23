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
 * code com.moontwon.knife.crypto.RSA} {@code int[]}
 * 
 * 整数数字支持{@code `...`}和{@code `,`}语法,{@code `12...15` = [12,13,14,15] 即指定12到15内的所有整数组成的数组, `12,13,14,15,1`=[12, 13, 14, 15, 1] 即有指定数字组成的数组}
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
			public void process(Object configable, Field field, Class<?> type, String node) throws IllegalArgumentException, IllegalAccessException {
				field.setInt(configable, Integer.parseInt(node));
			}
		});
		add(int[].class, new TypeProcess() {
			@Override
			public void process(Object configable, Field field, Class<?> type, String node) throws IllegalArgumentException, IllegalAccessException {

				if (node.contains(",")) {
					field.set(configable, ArrayUtils.fromString(node.split(",")));
				} else if (node.contains("...")) {
					int[] d = ArrayUtils.fromString(node.split("\\.\\.\\."));
					field.set(configable, ArrayUtils.fromInt(d[0], d[1]));
				} else {
					throw new IllegalArgumentException("无效配置信息 " + node);
				}
			}
		});
		add(long.class, new TypeProcess() {
			@Override
			public void process(Object configable, Field field, Class<?> type, String node) throws IllegalArgumentException, IllegalAccessException {
				field.setLong(configable, Long.parseLong(node));
			}
		});
		add(float.class, new TypeProcess() {
			@Override
			public void process(Object configable, Field field, Class<?> type, String node) throws IllegalArgumentException, IllegalAccessException {
				field.setFloat(configable, Float.parseFloat(node));
			}
		});
		add(double.class, new TypeProcess() {
			@Override
			public void process(Object configable, Field field, Class<?> type, String node) throws IllegalArgumentException, IllegalAccessException {
				field.setDouble(configable, Double.parseDouble(node));
			}
		});
		add(boolean.class, new TypeProcess() {
			@Override
			public void process(Object configable, Field field, Class<?> type, String node) throws IllegalArgumentException, IllegalAccessException {
				field.setBoolean(configable, Boolean.parseBoolean(node));
			}
		});
		add(byte[].class, new TypeProcess() {
			@Override
			public void process(Object configable, Field field, Class<?> type, String node) throws IllegalArgumentException, IllegalAccessException {
				field.set(configable, Base64.decodeBase64(node));
			}
		});
		add(String.class, new TypeProcess() {
			@Override
			public void process(Object configable, Field field, Class<?> type, String node) throws IllegalArgumentException, IllegalAccessException {
				field.set(configable, node);
			}
		});
		add(RSA.class, new TypeProcess() {
			@Override
			public void process(Object configable, Field field, Class<?> type, String node) throws IllegalArgumentException, IllegalAccessException {
				field.set(configable, new RSA(OperationMode.ECB, Padding.PKCS1PADDING, RSA.pkcs8(node)));
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
	public XmlAutoConfig(Document document) {
		this.document = document;
		this.root = document.getRootElement().getName();
	}
	/**
	 * 对指定的对象的对应字段赋值
	 * 
	 * @param config
	 *            要配置的对象
	 * @return boolean {@code true}配置成功,{@code false}配置失败
	 */
	public boolean config(Object configable) {
		Class<?> clz = configable.getClass();
		Config typeConfig = clz.getAnnotation(Config.class);
		if (typeConfig == null) {
			LOGGER.error("该待配置对象,类上无Config注解");
			return false;
		}
		String currentNodeName = typeConfig.value();
		if ("".equals(currentNodeName)) {
			currentNodeName = root;
		}
		Field[] fields = clz.getDeclaredFields();

		for (Field field : fields) {
			Config config = field.getAnnotation(Config.class);
			if (config == null) {
				continue;
			}
			field.setAccessible(true);
			String name = config.value();
			if ("".equals(name)) {
				name = field.getName();
			}
			Node node = document.selectSingleNode("//" + currentNodeName + "//" + name);
			if (node == null) {
				LOGGER.error("字段对应的值不存在[class name = {}, field name = {}]", config.getClass().getSimpleName(), name);
				return false;
			}
			String string = node.getStringValue();
			if ("".equals(string)) {
				LOGGER.warn("{} 字段配置信息为空", name);
				continue;
			}

			Class<?> type = field.getType();
			TypeProcess typeProcess = t.get(type);
			if (typeProcess != null) {
				try {
					typeProcess.process(configable, field, type, string);
				} catch (IllegalArgumentException | IllegalAccessException e) {
					LOGGER.error("字段赋值出现异常[type = {},string = {} {}]", type, string, e);
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
