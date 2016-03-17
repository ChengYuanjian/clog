package com.cyj.clog.util.test;

import org.junit.Test;

import com.cyj.clog.util.SecurityUtil;

public class SecurityUtilTest {
	
	@Test
	public void test_base64encode(){
		System.out.println(SecurityUtil.base64encode("cyj"));
	}
	
	@Test
	public void test_base64decode(){
		System.out.println(SecurityUtil.base64decode("Y3lq"));
	}

}
