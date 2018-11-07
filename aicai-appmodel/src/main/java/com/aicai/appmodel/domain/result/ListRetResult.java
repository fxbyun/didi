package com.aicai.appmodel.domain.result;

import java.util.Collection;

@Deprecated
/**
 * Deprecated：先转用BatchWriteResult
 * 
 */
public interface ListRetResult<T> extends Result<T> {
	/**
	 * 由接口实现方确定返回idList，还是DO List
	 * @return
	 */
	public Collection<T> getSuccessList();
	public void setSuccessList(Collection<T> successList);
	/**
	 * 一般返回idList，对于insert List的可返回 DO List
	 * @return
	 */
	public Collection<T> getFailList();
	public void setFailList(Collection<T> failList);
}
