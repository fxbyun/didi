package com.aicai.profiler.connector;

import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.springframework.aop.interceptor.AbstractMonitoringInterceptor;

import com.aicai.profiler.Profiler;
import com.aicai.profiler.core.helper.PrintProfilerHelper;
import com.aicai.profiler.counter.InvokeCounter;

public class AiCaiProfilerInterceptor extends AbstractMonitoringInterceptor {

	private static final long serialVersionUID = 7406883880062745237L;

	private int monitorTime;
	/**是否开启调用计数功能*/
	private boolean openCounter = false;
	/** 计数功能BeanName正则过滤(匹配上才计算) */
	private String counterBeanRegex = ".*Service.*";
	/** 计数器日志打印间隔 */
	private int counterPrintInterval = 10 * 60;
	
	public void init() {
		if (openCounter) {
			InvokeCounter.initPrint(counterPrintInterval * 1000);
		}
	}

	public AiCaiProfilerInterceptor() {
		
	}
	public AiCaiProfilerInterceptor(boolean useDynamicLogger) {
		setUseDynamicLogger(useDynamicLogger);
	}

	protected boolean isInterceptorEnabled(MethodInvocation invocation, Log logger) {
		return true;
	}

	@Override
	protected Object invokeUnderTrace(MethodInvocation invocation, Log logger) throws Throwable {
		String name = createInvocationTraceName(invocation);
		// 是否委托了注解的拦截
		boolean isExistsPrintProfiler = PrintProfilerHelper.isMethodExistsPrintProfiler(invocation.getMethod());
		try {
			if (!isExistsPrintProfiler) {
				Profiler.start(name, monitorTime);
			}
			if (openCounter) {
				InvokeCounter.count(name, counterBeanRegex);
			}
			return invocation.proceed();
		} finally {
			if (!isExistsPrintProfiler) {
				Profiler.stop(name);
			}
		}
	}
	
	public void destroy() {
		if (openCounter) {
			InvokeCounter.destoryThreadPool();
		}
	}

	public int getMonitorTime() {
		return monitorTime;
	}

	public void setMonitorTime(int monitorTime) {
		this.monitorTime = monitorTime;
	}

	public boolean isOpenCounter() {
		return openCounter;
	}

	public void setOpenCounter(boolean openCounter) {
		this.openCounter = openCounter;
	}

	public int getCounterPrintInterval() {
		return counterPrintInterval;
	}

	public void setCounterPrintInterval(int counterPrintInterval) {
		this.counterPrintInterval = counterPrintInterval;
	}

	public String getCounterBeanRegex() {
		return counterBeanRegex;
	}

	public void setCounterBeanRegex(String counterBeanRegex) {
		this.counterBeanRegex = counterBeanRegex;
	}
}
