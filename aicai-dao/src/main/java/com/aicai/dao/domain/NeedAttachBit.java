package com.aicai.dao.domain;

/**
 * 定义在ReadService方法中的一个查询输入参数中，表示调用者需要返回某些数据以方便后续逻辑判断。
 * 服务提供者可根据此方法的数据实时性要求从cache中返回数据，或从DB中返回实时数据。
 * 可以在DO中携带数据返回，也可以在ResultDO中携带数据返回。
 * 
 * @see HasLoadedAttach
 * @author zhoufeng
 *
 */
public interface NeedAttachBit {
	public void setNeedAttach(long neetAttachBit);
	public boolean isNeedAttach(long neetAttachBit);

	/**
	 * 如果某数据是从cache中返回的，那他反序列化出来的attach数据不一定是最新的，
	 * 需要clean为只剩下id是有效的或者从attach的cache中来update
	 * @param needUpdateAttach
	 */
	public void setNeedUpdateAttach(boolean needUpdateAttach);
	
	/**
	 * 如果某数据是从cache中返回的，那他反序列化出来的attach数据不一定是最新的，
	 * 需要clean为只剩下id是有效的或者从attach的cache中来update
	 * 
	 */
	public boolean isNeedUpdateAttach();
}
