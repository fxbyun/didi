package com.aicai.appmodel.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class BaseDO implements Serializable {
	private static final long serialVersionUID = -8692783160144327315L;

	protected long id;
	private long flagBit;

	/**
	 * 默认值有助于被忽视的数据库字段值初始化和默认值
	 */
	private JSONObject features = new JSONObject();

	/**
	 * 现在由于还有个别war未能升级为java8, 所以先不定义 LocalDateTime field, 或者定义在新版本
	 * aicai-appmodel-1.0.2-SNAPSHOT
	 */
	private LocalDateTime createTime;
	private LocalDateTime updateTime;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getFlagBit() {
		return flagBit;
	}

	public void setFlagBit(long flagBit) {
		this.flagBit = flagBit;
	}

	public void addFlagBit(Long flagBit) {
		this.flagBit |= flagBit.longValue();
	}

	public void removeFlagBit(long flagBit) {
		this.flagBit &= flagBit ^ Long.MAX_VALUE;
	}

	/**
	 * 存入数据库时使用
	 * 
	 * @return
	 */
	public String getFeatures() {
		return JSON.toJSONString(features, SerializerFeature.UseISO8601DateFormat);
	}

	public <T> T getFeature(String columnName, Class<T> clazz) {
		return features.getObject(columnName, clazz);
	}

	public String getFeature(String columnName) {
		return features.getString(columnName);
	}

	public void setupFeatures(JSONObject features) {
		this.features = features;
	}

	public void setupFeature(String columnName, Object value) {
		features.put(columnName, value);
	}

	public void removeFeature(String columnName) {
		features.remove(columnName);
	}

	/**
	 * 从数据库获取数据时需要使用
	 * 
	 * @param features
	 */
	public void setFeatures(String features) {
		this.features = JSONObject.parseObject(features, Feature.AllowISO8601DateFormat);
	}

	public LocalDateTime getCreateTime() {
		return createTime;
	}

	public void setCreateTime(LocalDateTime createTime) {
		this.createTime = createTime;
	}

	public LocalDateTime getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(LocalDateTime updateTime) {
		this.updateTime = updateTime;
	}

	/**
	public int compareTo(BaseDO other) {
		long result = this.id - other.id;
		if(result > 0){
			return 1;
		}
		if(result == 0){
			return 0;
		}
		return -1;
	}*/
}
