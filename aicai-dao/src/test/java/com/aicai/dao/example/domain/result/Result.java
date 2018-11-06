package com.aicai.dao.example.domain.result;

public class Result<T> {
	private boolean success = true;
	private T model;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public T getModel() {
		return model;
	}

	public void setModel(T model) {
		this.model = model;
	}
}
