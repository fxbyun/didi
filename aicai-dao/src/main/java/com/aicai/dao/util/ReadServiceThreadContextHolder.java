package com.aicai.dao.util;

import com.aicai.dao.readwrite.advice.ReadServiceAdvice;
import com.aicai.dao.readwrite.domain.ReadServiceThreadContext;

/**
 * {@link ReadServiceThreadContext} 持有类
 * 
 * @see {@link ReadServiceThreadContext}
 * @see {@link ReadServiceAdvice}
 * 
 * @author zhouguangming 
 * 创建时间: 2014年4月2日 下午7:29:46
 */
public final class ReadServiceThreadContextHolder {
	/**
	 * {@link ReadServiceThreadContext}持有者
	 */
	private static final ThreadLocal<ReadServiceThreadContext> holder = new ThreadLocal<ReadServiceThreadContext>();

	/**
	 * 私有构造方法，防止创建实例
	 */
	private ReadServiceThreadContextHolder() {
		throw new RuntimeException("不应该创建" + ReadServiceThreadContextHolder.class.getName() + "的实例！");
	}

	/**
	 * 设置{@link ReadServiceThreadContext} 实例
	 * 
	 * @param readServiceThreadContext
	 */
	public static void setReadServiceThreadContext(ReadServiceThreadContext readServiceThreadContext) {
		holder.set(readServiceThreadContext);
	}

	/**
	 * 是否持有{@link ReadServiceThreadContext}实例
	 * 
	 * @return true:持有；false:不持有
	 */
	public static boolean hasReadServiceThreadContext() {
		if (holder.get() == null) {
			return false;
		} else {
			return true;
		}
	}	
	
	/**
	 * 是否实时
	 * 
	 * @return true:是；false:不是
	 */
	public static boolean isRealtime() {
		ReadServiceThreadContext readServiceThreadContext = holder.get();
		if (readServiceThreadContext != null) {
			return readServiceThreadContext.isRealtime();
		}
		
		return false;
	}
	
	/**
	 * 清除{@link ReadServiceThreadContext}实例
	 */
	public static void removeReadServiceThreadContext() {
		holder.remove();
	}
}
