package com.aicai.profiler.service;

import com.aicai.profiler.connector.annotation.PrintDigest;
import com.aicai.profiler.connector.annotation.PrintLevel;
import com.aicai.profiler.connector.annotation.PrintProfiler;
import com.aicai.profiler.domain.User;

@PrintDigest(printLevel = PrintLevel.DEBUG)
@PrintProfiler(elapseTime=0)
public class TestChangePrintLevelOnClazzService {
	public void test_类注解_日志级别DEBUG() {
		System.out.println("test_类注解_日志级别DEBUG  被调用");
	}
	public void test_类注解_自定义对象_日志级别DEBUG(User user) {
	}

	@PrintDigest(printLevel = PrintLevel.ERROR)
	public void test_方法注解覆盖类注解_输出ERROR() {
	}
}
