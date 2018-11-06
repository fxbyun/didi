package com.aicai.profiler.connector.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <pre>
 * 注解为精细化打印 类/方法注解为精细化打印之后，
 * 通过{@link com.aicai.profiler.connector.AiCaiPrintDigestInterceptor}配置的自动代理将会对其无效
 * 取而代之是通过用户自定义的打印设置进行打印
 * </pre>
 * 
 * @project aicai-profiler
 * @author van
 * @date 2012-11-1 Copyright (C) 2010-2012 www.2caipiao.com Inc. All rights
 *       reserved.
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface PrintDigest {
	PrintLevel printLevel() default PrintLevel.INFO;
}
