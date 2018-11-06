package com.aicai.profiler.service;

import com.aicai.profiler.connector.annotation.PrintDigest;
import com.aicai.profiler.connector.annotation.PrintLevel;
import com.aicai.profiler.connector.annotation.SkipDigest;

public class TestAnnotationOnMethodService {
	@PrintDigest(printLevel = PrintLevel.DEBUG)
	public void test_测试在方法上注解_日志级别为DEBUG_跳过第一个参数(@SkipDigest String arg1, String arg2, int arg3) {
		// do nothing
	}

	@PrintDigest(printLevel = PrintLevel.WARN)
	public void test_测试在方法上注解_日志级别为WARN_跳过第二个参数(String arg1, @SkipDigest String arg2, int arg3) {
		// do nothing
	}

	@PrintDigest(printLevel = PrintLevel.ERROR)
	public void test_测试方法上注解_日志级别为ERROR_跳过最后一个参数(String arg1, String arg2, @SkipDigest int arg3) {
		// do nothing
	}

}
