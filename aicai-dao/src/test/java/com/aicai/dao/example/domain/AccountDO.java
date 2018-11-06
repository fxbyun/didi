package com.aicai.dao.example.domain;


public class AccountDO {
	private long accountId;
	private long memeberId;
	private long balance;
	private long lockBalance;
	private int accountCurrency;

	public long getAccountId() {
		return accountId;
	}

	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}

	public long getMemeberId() {
		return memeberId;
	}

	public void setMemeberId(long memeberId) {
		this.memeberId = memeberId;
	}

	public long getBalance() {
		return balance;
	}

	public void setBalance(long balance) {
		this.balance = balance;
	}

	public long getLockBalance() {
		return lockBalance;
	}

	public void setLockBalance(long lockBalance) {
		this.lockBalance = lockBalance;
	}

	public int getAccountCurrency() {
		return accountCurrency;
	}

	public void setAccountCurrency(int accountCurrency) {
		this.accountCurrency = accountCurrency;
	}

}
