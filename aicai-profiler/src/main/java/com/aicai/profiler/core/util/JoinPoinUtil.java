package com.aicai.profiler.core.util;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

public class JoinPoinUtil {
	public static String createMethodFullName(Method method) {
		Class<?> declaringClazz = method.getDeclaringClass();
		String pageName = declaringClazz.getPackage().getName();
		String methodFullName = pageName + "." + declaringClazz.getSimpleName() + "." + method.getName();
		return methodFullName;
	}

	public static Method getMethod(JoinPoint jp) throws NoSuchMethodException {
		MethodSignature methodSignature = (MethodSignature) jp.getSignature();
		return getTargetClass(jp).getMethod(methodSignature.getName(), methodSignature.getParameterTypes());
	}

	public static Class<? extends Object> getTargetClass(JoinPoint jp) throws NoSuchMethodException {
		return jp.getTarget().getClass();
	}
}
