package com.aicai.dao.readwrite.domain;

/**
 * 用于读写分离的ReadService线程上下文
 * 
 * @author zhouguangming
 * 创建时间: 2014年4月17日 上午10:03:53
 */
public class ReadServiceThreadContext {
	
	/**
	 * ReadServiceThreadContext实例数组
	 */
	private static final ReadServiceThreadContext[] instanceArray = 
		{new ReadServiceThreadContext(true), new ReadServiceThreadContext(false)};
	/**
	 * 实时
	 */
	private final boolean realtime;

	/**
	 * 私有构造方法，防止其它途径创建实例
	 * 
	 * @param realtime
	 */
	private ReadServiceThreadContext(boolean realtime) {
		this.realtime = realtime;
	}

	public boolean isRealtime() {
		return realtime;
	}
	
	/**
	 * 获取实时的上下文实例
	 * 
	 * @return
	 */
	public static ReadServiceThreadContext getRealtimeInstance() {
		return instanceArray[0];
	}
	
	/**
	 * 获取非实时的上下文实例 
	 *
	 * @return
	 */
	public static ReadServiceThreadContext getNonRealtimeInstance() {
		return instanceArray[1];
	}
}
