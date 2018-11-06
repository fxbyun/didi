package com.aicai.profiler.connector.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 打印性能日志的注解
 * 
 * @author van
 * @date 2012-11-2 Copyright (C) 2010-2012 www.2caipiao.com Inc. All rights
 *       reserved.
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PrintProfiler {
	int elapseTime() default 500;
	String extraInfo() default "";
}
