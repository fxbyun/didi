package com.aicai.dao.readwrite.domain;

/**
 * @author zhouguangming 
 * 创建时间: 2014年4月4日 上午10:08:52
 */
public class Student {
	private long id;
	private String name;
	private int number;

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

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}
}
