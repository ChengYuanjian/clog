package com.cyj.clog.util;

import java.util.Properties;

public class PropertyUtil {

	private static Properties properties = null;

	private static String propertyFile = "/clog.properties";

	static {
		try {
			properties = new Properties();
			properties.load(PropertyUtil.class
					.getResourceAsStream(propertyFile));
		} catch (Exception e) {
			System.err.println("Failed to load clog.properties->"
					+ e.getMessage());
			e.printStackTrace();
		}
	}
	
	private PropertyUtil(){}

	public static String getProperty(String key, String defaultValue) {
		if (properties == null)
			return defaultValue;
		return properties.getProperty(key);
	}

	public static String getProperty(String key) {
		return getProperty(key, "");
	}
	
	public static String getDecodeProperty(String key){
		return SecurityUtil.base64decode(getProperty(key));
	}

}
