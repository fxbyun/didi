package com.aicai.dao.example.manager.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.aicai.dao.GenericDao;
import com.aicai.dao.example.domain.BetGroupPlan;
import com.aicai.dao.example.domain.result.Result;
import com.aicai.dao.example.manager.BetGroupPlanManager;

public class BetGroupPlanManagerImpl implements BetGroupPlanManager {

	@Autowired
	private GenericDao  groupPlanDao;

	public void setGroupPlanDao(GenericDao  groupPlanDao) {
		this.groupPlanDao = groupPlanDao;
	}

	@Override
	public Result<BetGroupPlan> queryGroupPlan(long id) {
		Result<BetGroupPlan> result = new Result<BetGroupPlan>();
		try{
		BetGroupPlan groupPlan = groupPlanDao.queryOne("BetPlanDao.selectBetGroupPlanByPrimaryKey", 204606);
		result.setModel(groupPlan);
		}catch(Throwable e){
			result.setSuccess(false);//false表示操作出错，model为null表示根据这id找不到记录
		}
		return result;
	}

}
