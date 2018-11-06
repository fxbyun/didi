package com.aicai.profiler.core.helper;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.aicai.profiler.connector.annotation.PrintDigest;
import com.aicai.profiler.connector.annotation.PrintLevel;
import com.aicai.profiler.connector.annotation.SkipDigest;

/**
 * 
 * @author van
 * @date 2012-11-2 Copyright (C) 2010-2012 www.2caipiao.com Inc. All rights
 *       reserved.
 */
public class PrintDigestHelper {

	/**
	 * 获取方法的打印级别，没有则返回默认值
	 * 
	 * @param method
	 * @param defaultLevel
	 * @return
	 */
	public static PrintLevel getPrintLevelIfNull(Method method, PrintLevel defaultLevel) {
		PrintLevel printLevel = defaultLevel;
		if (method.getDeclaringClass().isAnnotationPresent(PrintDigest.class)) {
			printLevel = method.getDeclaringClass().getAnnotation(PrintDigest.class).printLevel();
		}
		// 方法上的精细化打印信息可以覆盖类上的
		if (method.isAnnotationPresent(PrintDigest.class)) {
			printLevel = method.getAnnotation(PrintDigest.class).printLevel();
		}
		return printLevel;
	}

	/**
	 * 判断方法是否委托了精细化打印
	 * 
	 * @param method
	 * @return
	 */
	public static boolean isMethodExistsPrintDigest(Method method) {
		// 方法所在类是否已经委托给精细化打印了
		boolean isClazzChangePrintLevel = method.getDeclaringClass().isAnnotationPresent(PrintDigest.class);

		// 方法是否已经委托给精细化打印了
		boolean isMethodChangePrintLevel = method.isAnnotationPresent(PrintDigest.class);
		return isClazzChangePrintLevel || isMethodChangePrintLevel;
	}

	/**
	 * 判断方法是否需要跳过打印
	 * 
	 * @param method
	 * @return
	 */
	public static boolean isMethodNeedSkipPrint(Method method) {

		Class<?> declaringClazz = method.getDeclaringClass();
		// 方法是否需要跳过打印
		boolean isMethodNeedSkipPrint = method.isAnnotationPresent(SkipDigest.class);
		// 方法所在类是否需要跳过打印
		boolean isClazzNeedSkipPrint = declaringClazz.isAnnotationPresent(SkipDigest.class);
		return isMethodNeedSkipPrint || isClazzNeedSkipPrint;

	}

	/**
	 * 取得方法可打印的参数
	 * 
	 * @param method
	 * @param arguments
	 * @return
	 */
	public static Object[] getCanPrintArgs(Method method, Object[] arguments) {
		List<Object> argsList = new ArrayList<Object>();
		if (null == arguments) {
			return argsList.toArray();
		}
		Annotation[][] parameterAnnotations = method.getParameterAnnotations();
		if (null != parameterAnnotations) {
			for (int i = 0; i < parameterAnnotations.length; i++) {
				Annotation[] annotations = parameterAnnotations[i];
				if (!hasSkipPrint(annotations)) {
					argsList.add(arguments[i]);
				}
			}
		}
		return argsList.toArray();
	}

	private static boolean hasSkipPrint(Annotation[] annotations) {
		boolean hasSkipPrint = false;
		if (null == annotations) {
			return false;
		}
		for (int j = 0; j < annotations.length; j++) {
			// 此参数第j个注解是SkipPrint，这个参数需要skip掉
			if (annotations[j].annotationType() == SkipDigest.class) {
				hasSkipPrint = true;
			}
		}
		return hasSkipPrint;
	}
}
