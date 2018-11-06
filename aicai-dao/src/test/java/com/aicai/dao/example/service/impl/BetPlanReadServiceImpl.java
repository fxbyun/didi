package com.aicai.dao.example.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.aicai.dao.example.domain.BetGroupPlan;
import com.aicai.dao.example.domain.Member;
import com.aicai.dao.example.domain.constant.QueryGroupPlanNeedAttachBit;
import com.aicai.dao.example.domain.query.QueryGroupPlanOption;
import com.aicai.dao.example.domain.result.Result;
import com.aicai.dao.example.manager.BetGroupPlanManager;
import com.aicai.dao.example.service.BetPlanReadService;

public class BetPlanReadServiceImpl implements BetPlanReadService {
	
	@Autowired //当main目录有个此类型的类，而test目录也有个此类型的mock类时，测试代码用extends AbstractJUnit4SpringContextTests方式会抛异常：找到两个类，不知注入哪个类
	private BetGroupPlanManager groupPlanManager;

	public void setGroupPlanManager(BetGroupPlanManager groupPlanManager) {
		this.groupPlanManager = groupPlanManager;
	}

	@Override
	public Result<BetGroupPlan> queryGroupPlanById(long id, QueryGroupPlanOption queryOption) {
		Result<BetGroupPlan> result = groupPlanManager.queryGroupPlan(id);
		if(!result.isSuccess()){
			return result;
		}
		BetGroupPlan groupPlan = result.getModel();
		if(groupPlan != null && queryOption.isNeedAttach(QueryGroupPlanNeedAttachBit.member)){
			Member member = new Member(groupPlan.getMember().getId());
			member.setLoginName(groupPlan.getAccount());
			member.setDisplayName("南山一根葱,西丽刘德华");
			groupPlan.setMember(member);
		}

		return result;
	}

}
