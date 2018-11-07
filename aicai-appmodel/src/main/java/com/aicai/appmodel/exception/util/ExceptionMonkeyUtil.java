package com.aicai.appmodel.exception.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 比 阿里开源的动态非侵入AOP解决方案：JVM-Sandbox 土很多的显式埋点工具
 * 
 * @author zhoufenglokki
 *
 */
public class ExceptionMonkeyUtil {
	private static final Map<String, Throwable> exceptionMap = new ConcurrentHashMap<>();

	private static boolean throwExceptionSwitch = false;

	/**
	 * 
	 * @param key
	 *            英文，不超过50长度，以“点”分隔，：“粗模块.细模块.细节上下文位置或方法"
	 */
	public static void monkeyJump(String key) {
		if (throwExceptionSwitch) {
			Throwable ex = exceptionMap.get(key);
			if (ex != null) {
				ExceptionMonkeyUtil.<RuntimeException> sneakyThrowInner(ex);
			}
		}
	}

	private static <T extends Throwable> T sneakyThrowInner(Throwable ex) throws T {
		throw (T) ex;
	}

	public static void putException(String key, Throwable ex) {
		exceptionMap.put(key, ex);
	}

	public static void clearException() {
		exceptionMap.clear();
	}

	public static boolean isThrowExceptionSwitch() {
		return throwExceptionSwitch;
	}

	public static void setThrowExceptionSwitch(boolean throwExceptionSwitch) {
		ExceptionMonkeyUtil.throwExceptionSwitch = throwExceptionSwitch;
	}

}
