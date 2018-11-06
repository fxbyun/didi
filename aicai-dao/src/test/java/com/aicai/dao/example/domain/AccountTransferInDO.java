package com.aicai.dao.example.domain;

import org.apache.ibatis.type.Alias;

import com.aicai.dao.example.domain.constant.AccountTransferInStatus;

@Alias("AccountTransferInDO")
public class AccountTransferInDO {
	private long transferInId;
	private long toAccountId;
	private long amount;
	private int upRelateType;
	private long upRelateId;
	private int status = AccountTransferInStatus.INSERT_AND_NOADD_BALANCE;

	public long getTransferInId() {
		return transferInId;
	}

	public void setTransferInId(long transferInId) {
		this.transferInId = transferInId;
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
}
