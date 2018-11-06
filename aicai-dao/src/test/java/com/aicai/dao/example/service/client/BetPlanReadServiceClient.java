package com.aicai.dao.example.service.client;

import com.aicai.dao.example.service.BetPlanReadService;

public interface BetPlanReadServiceClient extends BetPlanReadService {
	//extends BetPlanReadService不是必须的，使用者只按接口BetPlanReadServiceClient使用，按需要去掉这个extends BetPlanReadService好了
}
