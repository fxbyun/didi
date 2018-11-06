package com.aicai.profiler.service;

import com.aicai.profiler.connector.annotation.PrintDigest;
import com.aicai.profiler.connector.annotation.PrintLevel;
import com.aicai.profiler.connector.annotation.SkipDigest;
import com.aicai.profiler.domain.User;
public class TestSkipAnnotationOnClazzService {
	
	public void test_通过Interceptor输出(User user) {
	}

	public void test_通过Interceptor输出_跳过第一个参数(@SkipDigest User user, String str) {
	}

	@SkipDigest
	public void test_随便你怎么弄都不会输出() {
	}

	@PrintDigest(printLevel = PrintLevel.ERROR)
	public void test_通过精细化输出_日志级别变为ERROR() {
	}
}
