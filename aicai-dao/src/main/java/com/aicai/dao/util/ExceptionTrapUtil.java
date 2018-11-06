package com.aicai.dao.util;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import org.springframework.dao.DuplicateKeyException;

/**
 * 制造异常中断埋点类，可测试 redo，事务，唯一键冲突场景
 * 在junit 中可直接使用，手工测试可配合 ExceptionTrapController 使用
 * @author zhoufenglokki
 *
 */
public class ExceptionTrapUtil {
	static Set<String> exceptionSet = new CopyOnWriteArraySet<>();

	static volatile boolean throwExceptionSwitch = false;

	/**
	 * exceptionNameWithContext 的格式，可以为：
	 * 粗模块.细事务名.RuntimeException
	 * 
	 * @param exceptionNameWithContext
	 */
	public static void getException(String exceptionNameWithContext) {
		if (!throwExceptionSwitch) {
			return;
		}
		boolean contained = exceptionSet.contains(exceptionNameWithContext);// 用 remove() 需要多次放入
		if (!contained) {
			return;
		}
		if (exceptionNameWithContext.endsWith("RuntimeException")) {
			throw new RuntimeException(exceptionNameWithContext);
		}
		if (exceptionNameWithContext.endsWith("DuplicateKeyException")){
			throw new DuplicateKeyException(exceptionNameWithContext);  // FIXME 唯一键冲突的异常类型或 oracle errorCode 需要由具体尝试中确定
		}
	}

	public static void setupThrowExceptionSwitch(boolean trueForThrow) {
		throwExceptionSwitch = trueForThrow;
	}

	public static void putTrap(String exceptionNameWithContext) {
		exceptionSet.add(exceptionNameWithContext);
	}

	public static Set<String> queryTrapList() {
		return exceptionSet;
	}

	public static boolean queryThrowExceptionSwitch() {
		return throwExceptionSwitch;
	}
}
