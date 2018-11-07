package com.aicai.appmodel.domain.result;

import com.aicai.appmodel.page.DataPage;

public class PageResult<T> extends BaseResult {
	private static final long serialVersionUID = 1400532352709932863L;

	private DataPage<T> page;

	public DataPage<T> getPage(){
		return page;
	}
	public void setPage(DataPage<T> page){
		this.page = page;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public <SubClass extends PageResult> SubClass withPage(DataPage<T> page){
		this.page = page;
		return (SubClass)this;
	}
}
