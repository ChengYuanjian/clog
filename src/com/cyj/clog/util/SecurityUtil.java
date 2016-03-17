package com.cyj.clog.util;

import java.io.IOException;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class SecurityUtil {

	private SecurityUtil() {
	}

	public static String base64encode(String strMing) {
		BASE64Encoder base64en = new BASE64Encoder();
		return base64en.encode(strMing.getBytes());
	}

	public static String base64decode(String strMi) {
		BASE64Decoder base64de = new BASE64Decoder();
		try {
			return new String(base64de.decodeBuffer(strMi));
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Cannot decode " + strMi);
			return "";
		}
	}

}
