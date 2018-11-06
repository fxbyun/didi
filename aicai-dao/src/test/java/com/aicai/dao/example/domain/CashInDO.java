package com.aicai.dao.example.domain;

import org.apache.ibatis.type.Alias;

import com.aicai.dao.example.domain.constant.CashInStatus;

@Alias("CashInDO")
public class CashInDO {
	private long cashInId;
	private long memeberId;
	private long toAccountId;
	private long amount;
	private int currency;
	private int upRelateType;
	private long upRelateId;
	private int downRelateType = 0;
	private long downRelateId = 0;
	private int status = CashInStatus.INSERT_BUT_NOADD_DOWNRELATE;

	public long getCashInId() {
		return cashInId;
	}

	public void setCashInId(long cashInId) {
		this.cashInId = cashInId;
	}
	
	public long getMemeberId() {
		return memeberId;
	}

	public void setMemeberId(long memeberId) {
		this.memeberId = memeberId;
	}

	public long getToAccountId() {
		return toAccountId;
	}

	public void setToAccountId(long toAccountId) {
		this.toAccountId = toAccountId;
	}

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}

	public int getCurrency() {
		return currency;
	}

	public void setCurrency(int currency) {
		this.currency = currency;
	}

	public int getUpRelateType() {
		return upRelateType;
	}

	public void setUpRelateType(int upRelateType) {
		this.upRelateType = upRelateType;
	}

	public long getUpRelateId() {
		return upRelateId;
	}

	public void setUpRelateId(long upRelateId) {
		this.upRelateId = upRelateId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getDownRelateType() {
		return downRelateType;
	}

	public void setDownRelateType(int downRelateType) {
		this.downRelateType = downRelateType;
	}

	public long getDownRelateId() {
		return downRelateId;
	}

	public void setDownRelateId(long downRelateId) {
		this.downRelateId = downRelateId;
	}

}
