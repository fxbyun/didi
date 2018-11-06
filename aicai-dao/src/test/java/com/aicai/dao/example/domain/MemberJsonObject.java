package com.aicai.dao.example.domain;

import java.util.Calendar;

import com.aicai.dao.domain.ExtensionField;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class MemberJsonObject implements ExtensionField {
	private long id;
	private String loginName;
	private String displayName;
	private Calendar lastUpdateTime;

	private long flagBit;
	private int flagVersion;
	private JSONObject features = new JSONObject();
	private int featuresVersion;

	public MemberJsonObject(long id) {
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
		return JSON.toJSONString(features,SerializerFeature.UseISO8601DateFormat);
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
		features.put(columnName, value);
	}
	@Override
	public void removeFeature(String columnName) {
		features.remove(columnName);
	}

	@Override
	public String getFeature(String columnName) {
		return features.getString(columnName);
	}

	@Override
	public <T> T getFeature(String columnName, Class<T> clazz) {
		return features.getObject(columnName, clazz);
	}

	@Override
	public void setFeatures(String featuresText) {
		features = JSONObject.parseObject(featuresText,Feature.AllowISO8601DateFormat);
	}

	public Calendar getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Calendar lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
}
