package com.aicai.profiler.connector;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import com.aicai.profiler.Profiler;
import com.aicai.profiler.connector.annotation.PrintLevel;
import com.aicai.profiler.core.helper.PrintDigestHelper;
import com.aicai.profiler.core.util.JoinPoinUtil;

/**
 * 基于PrintDigest注解的日志输出aop 与AiCaiPrintDigestInterceptor互斥输出
 * 
 * @project aicai-profiler
 * @author van
 * @date 2012-11-2 Copyright (C) 2010-2012 www.2caipiao.com Inc. All rights
 *       reserved.
 */
@Aspect
public class AiCaiPrintDigestAspect {
	@Pointcut("@within(com.aicai.profiler.connector.annotation.PrintDigest)||@annotation(com.aicai.profiler.connector.annotation.PrintDigest)")
	public void pointcut() {
	}

	@Before(value = "pointcut()")
	public void beforeAdvice(JoinPoint jp) {
		try {
			Method method = JoinPoinUtil.getMethod(jp);
			String methodFullName = JoinPoinUtil.createMethodFullName(method);

			if (!PrintDigestHelper.isMethodNeedSkipPrint(method)) {

				Object[] args = PrintDigestHelper.getCanPrintArgs(method, jp.getArgs());

				PrintLevel printLevel = PrintDigestHelper.getPrintLevelIfNull(method, PrintLevel.INFO);

				Profiler.callMark(methodFullName, args, printLevel);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
