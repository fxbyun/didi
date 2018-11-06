package com.aicai.dao.example.domain;

import java.util.HashMap;
import java.util.Map;

import com.aicai.dao.domain.ExtensionField;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

public class Member implements ExtensionField {
	private long id;
	private String loginName;
	private String displayName;

	private long flagBit;
	private int flagVersion;
	private Map<String, String> features = new HashMap<String, String>();
	private int featuresVersion;

	public Member(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String longinName) {
		this.loginName = longinName;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	@Override
	public String toString() {
		return "{className:'" + getClass().getName() + "'}," + JSON.toJSONString(this);
	}

	@Override
	public long getFlagBit() {
		return flagBit;
	}

	@Override
	public int getFlagVersion() {
		return flagVersion;
	}

	@Override
	public String getFeatures() {
		return JSON.toJSONString(features);
	}

	@Override
	public int getFeaturesVersion() {
		return featuresVersion;
	}

	public void setFeaturesVersion(int featuresVersion) {
		this.featuresVersion = featuresVersion;
	}

	@Override
	public void setupFeature(String columnName, String value) {
		features.put(columnName, value);
	}

	@Override
	public void setupFeature(String columnName, Object value) {
		features.put(columnName, JSON.toJSONString(value));
	}

	@Override
	public void removeFeature(String columnName) {
		features.remove(columnName);
	}

	@Override
	public String getFeature(String columnName) {
		return features.get(columnName);
	}

	@Override
	public <T> T getFeature(String columnName, Class<T> clazz) {
		return JSON.parseObject(features.get(columnName), clazz);
	}

	@Override
	public void setFeatures(String featuresText) {
		features = JSON.parseObject(featuresText, new TypeReference<Map<String, String>>() {
		});
	}
}
