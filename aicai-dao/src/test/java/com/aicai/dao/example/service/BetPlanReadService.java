package com.aicai.dao.example.service;

import com.aicai.dao.example.domain.BetGroupPlan;
import com.aicai.dao.example.domain.query.QueryGroupPlanOption;
import com.aicai.dao.example.domain.result.Result;

/**
 * BetPlanReadService放置在两端都有的"aicai-betplan-common-1.1.0.jar"
 * @author zhoufeng
 *
 */
public interface BetPlanReadService {
	public Result<BetGroupPlan> queryGroupPlanById(long id, QueryGroupPlanOption queryOption);
}
