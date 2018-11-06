package com.aicai.dao.example.domain;

import java.util.Calendar;

import com.alibaba.fastjson.JSON;

public class Event {
	private long id;
	private String name;
	private int status;
	private Calendar createTime;
	private Calendar updateTime;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Calendar getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Calendar createTime) {
		this.createTime = createTime;
	}

	public Calendar getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Calendar updateTime) {
		this.updateTime = updateTime;
	}
	
	@Override
	public String toString(){
		return JSON.toJSONString(this);
	}
}
