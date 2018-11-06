package com.aicai.dao.example.manager;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.aicai.dao.GenericDao;
import com.aicai.dao.example.domain.AccountTransferInDO;
import com.aicai.dao.example.domain.AccountTransferOutDO;
import com.aicai.dao.example.domain.CashInDO;
import com.aicai.dao.example.domain.constant.AccountTransferInStatus;
import com.aicai.dao.example.domain.constant.AccountTransferOutStatus;
import com.aicai.dao.example.domain.constant.DataRelateType;
import com.aicai.dao.example.domain.constant.SequenceName;

@Component("moneyTransferManager")
public class MoneyTransferManager {

    @Autowired
    private GenericDao accountTransferInDao;

    @Autowired
    private GenericDao accountTransferOutDao;

    @Autowired
    private GenericDao cashInDao;

    @Autowired
    private TransactionTemplate transactionTemplate;

    private SequenceManager sequenceManager = new RamSequenceManager();

    @SuppressWarnings("unchecked")
    public AccountTransferInDO transferMoneyFromCashInToAccount(CashInDO cashIn) {
        AccountTransferInDO transerIn = buildTransferInDO_FromCashIn(cashIn);
        insertTransferIn(transerIn);

        transactionTemplate.execute(new TransactionCallback<Object>() {

            @Override
            public Object doInTransaction(TransactionStatus status) {
                return null; // TODO
            }
        });
        return transerIn;
    }

    /**
     * 根据上游数据作转出事务
     * 
     * @param fromAccountId
     *            自此账户转出
     * @param upRelateType
     *            转出指令接受自此数据类型
     * @param upRelateId
     *            转出指令接受自此数据id
     * @param amount
     *            转出金额
     * @return
     */
    public AccountTransferOutDO transferOutToOther(long fromAccountId, int upRelateType, long upRelateId, long amount) {
        final AccountTransferOutDO transferOut = buildTransferOutDO(fromAccountId, upRelateType, upRelateId, amount);

        final Map<String, Object> param = buildTransferOutParam(transferOut);
        accountTransferOutDao.insertAndReturnAffectedCount("AccountTransferOutDao.insert", transferOut);

        Integer resultCode = transactionTemplate.execute(new TransactionCallback<Integer>() {

            @Override
            public Integer doInTransaction(TransactionStatus status) {
                System.out.println("transaction2 start:" + new Date());
                int affectRow = accountTransferOutDao.update("AccountTransferOutDao.transferOut_subBalance", param);
                if (affectRow != 1) {
                    status.setRollbackOnly();
                    return 1;
                }
                System.out.println("transaction2 middle:" + new Date());

                affectRow = accountTransferOutDao.update("AccountTransferOutDao.transferOut_updateStatus", param);
                if (affectRow != 1) {
                    status.setRollbackOnly();
                    return 2;
                }
                System.out.println("transaction2 end:" + new Date());

                return null;
            }
        });

        if (resultCode != null) {
            // TODO 如果错误码是1，可重试几次
        }
        return transferOut;
    }

    /**
     * 可以在事务中间按需暂停运行的transferOutToOther()，给自动化测试用
     * 因为不是最终运行代码，所以Deprecated和private
     * @param fromAccountId
     * @param upRelateType
     * @param upRelateId
     * @param amount
     * @param sleepSecond
     * @return
     * 
     */
    @Deprecated
    private AccountTransferOutDO transferOutToOther_privateForTest(long fromAccountId, int upRelateType,
            long upRelateId, long amount, final int sleepSecond, final boolean testRollback) {
        final AccountTransferOutDO transferOut = buildTransferOutDO(fromAccountId, upRelateType, upRelateId, amount);

        final Map<String, Object> param = buildTransferOutParam(transferOut);
        accountTransferOutDao.insertAndReturnAffectedCount("AccountTransferOutDao.insert", transferOut);

        Integer resultCode = transactionTemplate.execute(new TransactionCallback<Integer>() {

            @Override
            public Integer doInTransaction(TransactionStatus status) {
                System.out.println("transaction1 start:" + new Date());
                int affectRow = accountTransferOutDao.update("AccountTransferOutDao.transferOut_subBalance", param);
                if (affectRow != 1) {
                    status.setRollbackOnly();
                    return 1;
                }

                try {
                    Thread.sleep(sleepSecond * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (testRollback) {
                    throw new RuntimeException();
                }

                affectRow = accountTransferOutDao.update("AccountTransferOutDao.transferOut_updateStatus", param);
                if (affectRow != 1) {
                    status.setRollbackOnly();
                    return 2;
                }
                System.out.println("transaction1 end:" + new Date());
                return null;
            }
        });

        if (resultCode != null) {
            // TODO 如果错误码是1，可重试几次
        }
        return transferOut;
    }

    private Map<String, Object> buildTransferOutParam(AccountTransferOutDO transferOut) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("fromAccountId", transferOut.getFromAccountId());
        param.put("amount", transferOut.getAmount());
        param.put("transferOutId", transferOut.getTransferOutId());
        param.put("oldTransferOutStatus", AccountTransferOutStatus.INSERT_AND_NOSUB_BALANCE);
        param.put("newTransferOutStatus", AccountTransferOutStatus.SUB_BALANCE_AND_NOTRETURN_DOWN_RELATE);
        return param;
    }

    private AccountTransferOutDO buildTransferOutDO(long fromAccountId, int upRelateType, long upRelateId, long amount) {
        AccountTransferOutDO transferOut = new AccountTransferOutDO();
        transferOut.setTransferOutId(sequenceManager.getSequence(SequenceName.transferOutId));
        transferOut.setFromAccountId(fromAccountId);
        transferOut.setAmount(amount);
        transferOut.setUpRelateType(upRelateType);
        transferOut.setUpRelateId(upRelateId);
        transferOut.setStatus(AccountTransferOutStatus.INSERT_AND_NOSUB_BALANCE);
        return transferOut;
    }

    private AccountTransferInDO buildTransferInDO_FromCashIn(CashInDO cashIn) {
        AccountTransferInDO transferIn = new AccountTransferInDO();
        transferIn.setTransferInId(sequenceManager.getSequence(SequenceName.transferInId));
        transferIn.setToAccountId(cashIn.getToAccountId());
        transferIn.setAmount(cashIn.getAmount());
        transferIn.setUpRelateType(DataRelateType.cashIn);
        transferIn.setUpRelateId(cashIn.getCashInId());
        transferIn.setStatus(AccountTransferInStatus.INSERT_AND_NOADD_BALANCE);
        return transferIn;
    }

    private void insertTransferIn(AccountTransferInDO transferIn) {
        accountTransferInDao.insertAndReturnAffectedCount("AccountTransferInDao.insert", transferIn);
    }
}
