package com.aicai.appmodel.domain;

import java.io.Serializable;

public class FormFieldDiffResult implements Serializable {
	private static final long serialVersionUID = 1L;

	private String fieldName;
	private String title;
	private Object newValue;
	private Object dbValue;

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Object getNewValue() {
		return newValue;
	}

	public void setNewValue(Object newValue) {
		this.newValue = newValue;
	}

	public Object getDbValue() {
		return dbValue;
	}

	public void setDbValue(Object dbValue) {
		this.dbValue = dbValue;
	}

}
