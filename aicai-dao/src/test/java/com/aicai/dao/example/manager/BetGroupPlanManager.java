package com.aicai.dao.example.manager;

import com.aicai.dao.example.domain.BetGroupPlan;
import com.aicai.dao.example.domain.result.Result;

public interface BetGroupPlanManager {
	public Result<BetGroupPlan> queryGroupPlan(long id);
}
