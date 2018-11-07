package com.aicai.appmodel.domain.result;

import com.aicai.appmodel.page.DataPage;

@Deprecated
/**
 * Deprecated：先转用PageResult
 * 
 */
public interface PageRetResult<T> extends Result<T> {
	public DataPage<T> getPage();
	public void setPage(DataPage<T> page);
}
