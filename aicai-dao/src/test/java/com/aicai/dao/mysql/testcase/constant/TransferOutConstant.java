package com.aicai.dao.mysql.testcase.constant;

public class TransferOutConstant {
	public static final long fromAccountId_100 = 100;
	public static final long amount_22800 = 22800;
	
	public static final String sql_selectBalance = "select balance from pay_account where account_id = ?"; 
}
