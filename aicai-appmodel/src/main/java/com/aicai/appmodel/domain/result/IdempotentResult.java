package com.aicai.appmodel.domain.result;

/**
 * 
 * @author zhoufeng
 *
 * @param <T>
 */
public class IdempotentResult<T> extends ModelResult<T> {
	private static final long serialVersionUID = 1L;

	private int savedDataType = 0;
	private long savedDataId = 0;

	public int getSavedDataType() {
		return savedDataType;
	}

	public void setSavedDataType(int savedDataType) {
		this.savedDataType = savedDataType;
	}

	public long getSavedDataId() {
		return savedDataId;
	}

	public void setSavedDataId(long savedDataId) {
		this.savedDataId = savedDataId;
	}
}
