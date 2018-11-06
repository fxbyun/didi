package com.aicai.dao.mysql.testcase;

import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicLong;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.aicai.dao.example.domain.AccountTransferInDO;
import com.aicai.dao.example.domain.AccountTransferOutDO;
import com.aicai.dao.example.domain.CashInDO;
import com.aicai.dao.example.domain.constant.AccountTransferOutStatus;
import com.aicai.dao.example.domain.constant.DataRelateType;
import com.aicai.dao.example.domain.constant.SequenceName;
import com.aicai.dao.example.manager.MoneyTransferManager;
import com.aicai.dao.example.manager.SequenceManager;
import com.aicai.dao.mysql.testcase.constant.TransferOutConstant;

public class TransactionTest{
	private ClassPathXmlApplicationContext ctx;
	private QueryRunner run;
	private ResultSetHandler<Object> singleValueHandler;

	@Before
	public void before() {
		ctx = new ClassPathXmlApplicationContext("spring-db-mysql.xml","spring-dao-mysql-transaction.xml","spring-manager.xml");
		DataSource dataSource = (DataSource) ctx.getBean("dataSourceTest");

		run = new QueryRunner(dataSource);

		singleValueHandler = new ResultSetHandler<Object>() {
			public Object handle(ResultSet rs) throws SQLException {
				if (!rs.next()) {
					return null;
				}
				return rs.getObject(1);
			}
		};
	}

	@Test
	public void test_测试转账转出事务_期望成功() throws Exception {
		prepareEnv();

		MoneyTransferManager transferManager = (MoneyTransferManager) ctx
				.getBean("moneyTransferManager");
		long balanceBefore = (Long) run.query(
				TransferOutConstant.sql_selectBalance, singleValueHandler,
				TransferOutConstant.fromAccountId_100);

		AccountTransferOutDO transferOut = transferManager.transferOutToOther(
				TransferOutConstant.fromAccountId_100, DataRelateType.betOrder,
				100, TransferOutConstant.amount_22800);

		assert共用_测试转账中转出事务_期望成功(balanceBefore, transferOut);

		clearEnv(transferOut.getTransferOutId());
	}

	@Test
	public void test_测试转账转出事务_事务中间线程停顿3秒_期望成功() throws Exception {
		prepareEnv();
		Method method = MoneyTransferManager.class.getDeclaredMethod(
				"transferOutToOther_privateForTest", long.class, int.class,
				long.class, long.class, int.class, boolean.class);
		method.setAccessible(true);
		long balanceBefore = (Long) run.query(
				TransferOutConstant.sql_selectBalance, singleValueHandler,
				TransferOutConstant.fromAccountId_100);

		MoneyTransferManager transferManager = (MoneyTransferManager) ctx
				.getBean("moneyTransferManager");
		AccountTransferOutDO transferOut = (AccountTransferOutDO) method
				.invoke(transferManager, TransferOutConstant.fromAccountId_100,
						DataRelateType.betOrder, 100,
						TransferOutConstant.amount_22800, 3, false);

		assert共用_测试转账中转出事务_期望成功(balanceBefore, transferOut);
		clearEnv(transferOut.getTransferOutId());
	}

	@Test
	public void test_2线程测试转出事务乐观锁() throws Exception {
		prepareEnv();
		final Method method = MoneyTransferManager.class.getDeclaredMethod(
				"transferOutToOther_privateForTest", long.class, int.class,
				long.class, long.class, int.class, boolean.class);
		method.setAccessible(true);

		final MoneyTransferManager transferManager = (MoneyTransferManager) ctx
				.getBean("moneyTransferManager");

		final AtomicLong balance1 = new AtomicLong(0);
		final AtomicLong balance2 = new AtomicLong(0);
		final AtomicLong balance3 = new AtomicLong(0);
		final AtomicLong balance4 = new AtomicLong(0);
		final AtomicLong transferOutIdForClear_1 = new AtomicLong(0);
		final AtomicLong transferOutIdForClear_2 = new AtomicLong(0);

		@SuppressWarnings("unused")
		final Object syncLock = new Object();
		Runnable run_subBalanceFirst_sleep5second_updataStatus = new Runnable() {
			@Override
			public void run() {

				balance1.set(method共用_用DbUtils查询账户余额(TransferOutConstant.fromAccountId_100));
				System.out.println("第一个事务开始时的余额:" + balance1);
				
				try {
					AccountTransferOutDO transferOut = (AccountTransferOutDO) method.invoke(
							transferManager,
							TransferOutConstant.fromAccountId_100,
							DataRelateType.betOrder, 100,
							TransferOutConstant.amount_22800, 6, false);// 插入转出记录及减余额后，暂停6秒
					
					balance4.set(method共用_用DbUtils查询账户余额(TransferOutConstant.fromAccountId_100));
					System.out.println("第一个事务提交事务后的余额：" + balance4);

					transferOutIdForClear_1.set(transferOut.getTransferOutId());
					assert共用_2线程测试乐观锁(balance1, balance2, balance3, balance4, transferOutIdForClear_1, transferOutIdForClear_2);
					return;
				} catch (Exception e) {
					e.printStackTrace();
					balance4.set(method共用_用DbUtils查询账户余额(TransferOutConstant.fromAccountId_100));
					System.out.println("第一个事务回滚后的余额：" + balance4);
					assert共用_2线程测试乐观锁(balance1, balance2, balance3, balance4, new AtomicLong(1), transferOutIdForClear_2);
					return;
				}
			}
		};

		Thread thread1 = new Thread(
				run_subBalanceFirst_sleep5second_updataStatus);
		thread1.start();

		Thread.sleep(2000);// 本线程停止2秒

		balance2.set(method共用_用DbUtils查询账户余额(TransferOutConstant.fromAccountId_100));
		System.out.println("第一个事务减余额后未提交事务的余额：" + balance2);

		AccountTransferOutDO transferOut2 = transferManager.transferOutToOther(
				TransferOutConstant.fromAccountId_100, DataRelateType.betOrder,
				101, TransferOutConstant.amount_22800);

		balance3.set(method共用_用DbUtils查询账户余额(TransferOutConstant.fromAccountId_100));
		System.out.println("第二个事务减余额后提交事务的余额：" + balance3);
		
		transferOutIdForClear_2.set(transferOut2.getTransferOutId());
	}

	private void assert共用_测试转账中转出事务_期望成功(long balanceBefore,
			AccountTransferOutDO transferOut) throws Exception {
		long balanceAfter = (Long) run.query(
				TransferOutConstant.sql_selectBalance, singleValueHandler,
				TransferOutConstant.fromAccountId_100);

		Assert.assertEquals("转出金额不正确", TransferOutConstant.amount_22800,
				balanceBefore - balanceAfter);

		int status = (Integer) run
				.query("select status from pay_account_transfer_out where transfer_out_id = ?",
						singleValueHandler, transferOut.getTransferOutId());
		Assert.assertEquals("转出状态应为已减余额_未反馈下游操作结果",
				AccountTransferOutStatus.SUB_BALANCE_AND_NOTRETURN_DOWN_RELATE,
				status);
	}

	private void assert共用_2线程测试乐观锁(Object... param) {
		try {
			clearEnv(((AtomicLong) param[4]).get());
			clearEnv(((AtomicLong) param[5]).get());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private long method共用_用DbUtils查询账户余额(long accountId) {
		try {
			return (Long) run.query(TransferOutConstant.sql_selectBalance,
					singleValueHandler, accountId);
		} catch (SQLException e1) {
			e1.printStackTrace();
			return 0;
		}
	}

	private void prepareEnv() throws Exception {
		String sql = "delete from pay_account where account_id = ?";
		run.update(sql, TransferOutConstant.fromAccountId_100);

		sql = "delete from pay_account_transfer_out where from_account = ?";
		run.update(sql, TransferOutConstant.fromAccountId_100);

		sql = "insert pay_account(account_id, member_id, balance, lock_balance, account_currency) "
				+ "values(?, 100, 1000000, 0, 1)";
		run.update(sql, TransferOutConstant.fromAccountId_100);
	}

	private void clearEnv(long transferOutId) throws Exception {
		run.update(
				"delete from pay_account_transfer_out where transfer_out_id = ?",
				transferOutId);
	}

	@SuppressWarnings("unused")
	public void test_未整理好() {

		MoneyTransferManager transferManager = (MoneyTransferManager) ctx
				.getBean("moneyTransferManager");
		SequenceManager sequenceManager = (SequenceManager) ctx
				.getBean("sequenceManager");
		CashInDO cashIn = new CashInDO();
		cashIn.setCashInId(sequenceManager
				.getSequence(SequenceName.transferInId));
		cashIn.setMemeberId(sequenceManager.getSequence(SequenceName.memberId));
		cashIn.setToAccountId(sequenceManager
				.getSequence(SequenceName.accountId));
		AccountTransferInDO transerIn = transferManager
				.transferMoneyFromCashInToAccount(cashIn);
	}
}
