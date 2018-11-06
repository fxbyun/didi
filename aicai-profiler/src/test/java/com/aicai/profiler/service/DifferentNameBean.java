package com.aicai.profiler.service;

import com.aicai.profiler.connector.annotation.PrintProfiler;

@PrintProfiler(elapseTime = 0)
public class DifferentNameBean {
	public void test1(){}
	@PrintProfiler(elapseTime = 10)
	public void test2(){
		
		
	}
}
