package com.cyj.clog.util.test;


import junit.framework.Assert;

import org.junit.Test;

import com.cyj.clog.util.PropertyUtil;

public class PropertyUtilTest {
	
	
	@Test
	public void test_getProperty(){
		Assert.assertEquals("clog", PropertyUtil.getProperty("clog.sysname"));
	}

}
