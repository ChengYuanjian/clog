package com.cyj.clog.test;

import org.junit.Test;

import com.cyj.clog.CLog;

public class CLogTest {
	
	@Test
	public void test_trace(){
		CLog.trace("trace msg");
	}
	
	public static void main(String[] args) {
		CLog.trace("trace msg");
	}

}
