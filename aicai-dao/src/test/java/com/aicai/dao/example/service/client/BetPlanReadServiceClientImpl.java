package com.aicai.dao.example.service.client;

import com.aicai.dao.example.domain.BetGroupPlan;
import com.aicai.dao.example.domain.query.QueryGroupPlanOption;
import com.aicai.dao.example.domain.result.Result;
import com.aicai.dao.example.service.BetPlanReadService;

/**
 * BetPlanReadServiceClient放置在客户端"aicai-betplan-client-1.1.0.jar"，负责做简单/不易变的校验，决定从cache中读取数据还是从后端读取数据
 * 
 * @author zhoufeng
 *
 */
public class BetPlanReadServiceClientImpl implements BetPlanReadServiceClient {//implements BetPlanReadService不是必须的，
	private BetPlanReadService planReadService;

	@Override
	public Result<BetGroupPlan> queryGroupPlanById(long id, QueryGroupPlanOption queryOption) {
		if(queryOption == null){
			queryOption = new QueryGroupPlanOption();
			// or throw new IllegalArgumentException("QueryGroupPlanOption can't be null");
		}
		return planReadService.queryGroupPlanById(id, queryOption);
	}
}
