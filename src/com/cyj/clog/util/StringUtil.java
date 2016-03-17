package com.cyj.clog.util;

public class StringUtil {

	private StringUtil() {
	}

	public static boolean isEmpty(String str) {
		if (str == null || "".equals(str.trim()))
			return true;
		return false;
	}

	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}

	public static String calc(String str, String... strings) {
		if (isNotEmpty(str) && strings != null && strings.length > 0) {
			for (int i = 0; i < strings.length; i++) {
				str = str.replace("{" + i + "}", strings[i]);
			}
		}
		return str;
	}

}
