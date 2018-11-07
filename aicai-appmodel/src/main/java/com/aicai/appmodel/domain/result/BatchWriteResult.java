package com.aicai.appmodel.domain.result;

import java.util.ArrayList;
import java.util.Collection;

@SuppressWarnings({"unchecked", "rawtypes"})
public class BatchWriteResult<F> extends BaseResult {
	private static final long serialVersionUID = 3792782277780856214L;

	private Collection successList = new ArrayList();

	private Collection<F> failList = new ArrayList<F>();

	@Override
	/**
	 * 如果有一个写入记录成功，就算成功
	 */
	public boolean isSuccess() {
		super.isSuccess();
		if (successList.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 由接口实现方确定返回idList，还是DO List
	 * 
	 * @return
	 */
	public <S> Collection<S> getSuccessList() {
		return successList;
	}

	public <SubClass extends BatchWriteResult> SubClass withSuccessList(Collection successList) {
		this.successList = successList;
		return (SubClass)this;
	}

	/**
	 * 一般返回idList，对于insert List的可返回 DO List
	 * 
	 * @return
	 */
	public Collection<F> getFailList() {
		return failList;
	}

	public <SubClass extends BatchWriteResult> SubClass withFailList(Collection<F> failList) {
		this.failList = failList;
		return (SubClass)this;
	}

}
