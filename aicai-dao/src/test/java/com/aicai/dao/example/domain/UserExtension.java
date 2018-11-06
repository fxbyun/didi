package com.aicai.dao.example.domain;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.aicai.dao.domain.ExtensionFeatures;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

public class UserExtension implements Serializable, ExtensionFeatures {
    private static final long serialVersionUID = 8110997885735961588L;
    Integer id;
    String name;
    String password;

	private Map<String, String> features = new HashMap<String, String>();
    int featuresVersion;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

	@Override
	public String getFeatures() {
		return JSON.toJSONString(features);
	}

    @Override
    public int getFeaturesVersion() {
        return featuresVersion;
    }

	@Override
	public void setFeatures(String featuresText) {
		features = JSON.parseObject(featuresText, new TypeReference<Map<String, String>>() {
		});
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
}