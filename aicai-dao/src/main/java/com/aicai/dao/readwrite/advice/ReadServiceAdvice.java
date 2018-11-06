package com.aicai.dao.readwrite.advice;

import java.lang.reflect.Method;

import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.ThrowsAdvice;

import com.aicai.dao.readwrite.annotation.RealtimeForReadService;
import com.aicai.dao.readwrite.domain.ReadServiceThreadContext;
import com.aicai.dao.util.ReadServiceThreadContextHolder;

/**
 * 读服务Advice <p>
 * 注：配置时请注意：其只能拦截ReadService，不能拦截WriteService, 当然也不能拦截Manager等其它实现上，否则会有问题
 * 
 * @author zhouguangming
 * 创建时间: 2014年4月2日 下午7:49:38
 */
public class ReadServiceAdvice implements MethodBeforeAdvice, AfterReturningAdvice, ThrowsAdvice {

	/**
	 * 目标方法执行前，spring AOP将执行该方法
	 */
	@Override
	public void before(Method method, Object[] args, Object target) throws Exception {
		RealtimeForReadService realtime = method.getAnnotation(RealtimeForReadService.class);
		if (realtime == null) {
			ReadServiceThreadContextHolder.setReadServiceThreadContext(ReadServiceThreadContext.getNonRealtimeInstance());
			return;
		}
		if (realtime.isRealtime()) {
			ReadServiceThreadContextHolder.setReadServiceThreadContext(ReadServiceThreadContext.getRealtimeInstance());
		} else {
			ReadServiceThreadContextHolder.setReadServiceThreadContext(ReadServiceThreadContext.getNonRealtimeInstance());
		}
	}

	/**
	 * 目标方法成功返回后，spring AOP将执行该方法
	 */
	@Override
	public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
		ReadServiceThreadContextHolder.removeReadServiceThreadContext();
	}
	
	/**
	 * 当目标方法执行过程中抛出异常时，spring AOP调用此方法, 清除{@link ReadServiceThreadContext}实例
	 */
	public void afterThrowing(Throwable t) {
		ReadServiceThreadContextHolder.removeReadServiceThreadContext();
	}
}
