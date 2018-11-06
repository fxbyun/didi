package com.aicai.dao.example.service;

import com.aicai.dao.example.domain.BetGroupPlan;
import com.aicai.dao.example.domain.query.QueryGroupPlanOption;
import com.aicai.dao.example.domain.result.Result;

public interface BetPlanWriteService {
	
	/**
	 * WriteService处的query操作，为重要性非常高，实时性要求较高的少量业务服务，会不经过服务端处的cache代码，直读数据表
	 * @param id
	 * @param queryOption
	 * @return
	 */
	public Result<BetGroupPlan> queryGroupPlanById(long id, QueryGroupPlanOption queryOption);
}
