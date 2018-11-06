package com.aicai.profiler.connector;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.springframework.aop.interceptor.AbstractMonitoringInterceptor;

import com.aicai.profiler.Profiler;
import com.aicai.profiler.connector.annotation.PrintDigest;
import com.aicai.profiler.connector.annotation.PrintLevel;
import com.aicai.profiler.connector.annotation.SkipDigest;
import com.aicai.profiler.core.helper.PrintDigestHelper;

/**
 * 打印详细调用信息的拦截器
 * 
 * <pre>
 * 		<ol>
 * 			<li>用户可以注入defaultPrintLevel控制打印级别</li>
 * 			<li>对于已经在类或者方法上注解了精细化打印{@link PrintDigest}的，这里会跳过打印，委托给精细化控制</li>
 * 			<li>对于一些不想打印输出的类/方法/参数，可以通过注解{@link SkipDigest}跳过打印数据</li>
 * 		</ol>
 * </pre>
 * 
 * @author van
 * @date 2012-11-2 Copyright (C) 2010-2012 www.2caipiao.com Inc. All rights
 *       reserved.
 */
public class AiCaiPrintDigestInterceptor extends AbstractMonitoringInterceptor {

	private static final long serialVersionUID = 7406883880062745237L;

	private PrintLevel defaultPrintLevel = PrintLevel.INFO;

	public AiCaiPrintDigestInterceptor() {
	}

	public AiCaiPrintDigestInterceptor(boolean useDynamicLogger) {
		setUseDynamicLogger(useDynamicLogger);
	}

	protected boolean isInterceptorEnabled(MethodInvocation invocation, Log logger) {
		return true;
	}

	@Override
	protected Object invokeUnderTrace(MethodInvocation invocation, Log logger) throws Throwable {

		String methodFullName = createInvocationTraceName(invocation);

		try {
			Method method = invocation.getMethod();

			// 如果没有委托给精细化打印，并且不用跳过打印,则在此进行打印
			if (!(PrintDigestHelper.isMethodNeedSkipPrint(method) || PrintDigestHelper.isMethodExistsPrintDigest(method))) {
				// 获取可打印的参数
				Object[] args = PrintDigestHelper.getCanPrintArgs(method, invocation.getArguments());

				Profiler.callMark(methodFullName, args, defaultPrintLevel);
			}
		} catch (Exception e) {
		} finally {
		}

		return invocation.proceed();
	}

	public void setDefaultPrintLevel(PrintLevel defaultPrintLevel) {
		this.defaultPrintLevel = defaultPrintLevel;
	}

}
