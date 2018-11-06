package com.aicai.dao.example.domain;

import com.alibaba.fastjson.JSON;

public class BetOrder {
	private long id;
	private String orderNo;

	public BetOrder(long orderId) {
		this.id = orderId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getOrderNo() {
		return orderNo;
	}
	
	@Override
	public String toString(){
		return "{className:'" + getClass().getName() + "'}," + JSON.toJSONString(this);
	}
}
