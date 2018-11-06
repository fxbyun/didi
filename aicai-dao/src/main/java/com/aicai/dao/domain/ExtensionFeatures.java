package com.aicai.dao.domain;

public interface ExtensionFeatures {
	String getFeatures();

	void setFeatures(String features);

	int getFeaturesVersion();

	void setupFeature(String columnName, String value);

	void setupFeature(String columnName, Object value);

	void removeFeature(String columnName);

	String getFeature(String columnName);

	<T> T getFeature(String columnName, Class<T> clz);
}
