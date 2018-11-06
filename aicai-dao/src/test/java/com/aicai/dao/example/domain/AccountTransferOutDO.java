package com.aicai.dao.example.domain;

import org.apache.ibatis.type.Alias;

import com.aicai.dao.example.domain.constant.AccountTransferOutStatus;

@Alias("AccountTransferOutDO")
public class AccountTransferOutDO {

	private long transferOutId;
	private long fromAccountId;
	private long amount;
	private int upRelateType;
	private long upRelateId;
	private Integer downRelateType;
	private Long downRelateId;
	private int status = AccountTransferOutStatus.INSERT_AND_NOSUB_BALANCE;

	public long getTransferOutId() {
		return transferOutId;
	}

	public void setTransferOutId(long transferOutId) {
		this.transferOutId = transferOutId;
	}

	public long getFromAccountId() {
		return fromAccountId;
	}

	public void setFromAccountId(long fromAccountId) {
		this.fromAccountId = fromAccountId;
	}

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
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

	public Integer getDownRelateType() {
		return downRelateType;
	}

	public void setDownRelateType(Integer downRelateType) {
		this.downRelateType = downRelateType;
	}

	public Long getDownRelateId() {
		return downRelateId;
	}

	public void setDownRelateId(Long downRelateId) {
		this.downRelateId = downRelateId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
