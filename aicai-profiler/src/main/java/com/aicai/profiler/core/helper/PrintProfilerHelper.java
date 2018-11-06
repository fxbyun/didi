package com.aicai.profiler.core.helper;

import java.lang.reflect.Method;

import com.aicai.profiler.connector.annotation.PrintProfiler;

/**
 * 
 * @author van
 * @date 2012-11-2 Copyright (C) 2010-2012 www.2caipiao.com Inc. All rights
 *       reserved.
 */
public class PrintProfilerHelper {

	public static int getElapseTimeIfNull(Method method, int defaultTime) {
		int time = defaultTime;
		if (method.getDeclaringClass().isAnnotationPresent(PrintProfiler.class)) {
			time = method.getDeclaringClass().getAnnotation(PrintProfiler.class).elapseTime();
		}
		// 方法上的精细化打印信息可以覆盖类上的
		if (method.isAnnotationPresent(PrintProfiler.class)) {
			time = method.getAnnotation(PrintProfiler.class).elapseTime();
		}
		return time;
	}

	/**
	 * 判断方法是否存在Profiler注解
	 * 
	 * @param method
	 * @return
	 */
	public static boolean isMethodExistsPrintProfiler(Method method) {

		boolean isClazzNeedProfiler = method.getDeclaringClass().isAnnotationPresent(PrintProfiler.class);
		boolean isMethodNeedProfiler = method.isAnnotationPresent(PrintProfiler.class);
		return isClazzNeedProfiler || isMethodNeedProfiler;
	}

}
