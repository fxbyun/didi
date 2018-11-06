package com.aicai.profiler.connector;

import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.springframework.aop.interceptor.AbstractMonitoringInterceptor;

import com.aicai.profiler.Profiler;

public class AiCaiCallInterceptor extends AbstractMonitoringInterceptor {

	private static final long serialVersionUID = 7406883880062745237L;

	public AiCaiCallInterceptor() {
	}

	public AiCaiCallInterceptor(boolean useDynamicLogger) {
		setUseDynamicLogger(useDynamicLogger);
	}

	protected boolean isInterceptorEnabled(MethodInvocation invocation, Log logger) {
		return true;
	}

	@Override
	protected Object invokeUnderTrace(MethodInvocation invocation, Log logger) throws Throwable {

		String methodFullName = createInvocationTraceName(invocation);

		try {
			Profiler.callMark(methodFullName);

		} finally {
		}

		return invocation.proceed();
	}

}
