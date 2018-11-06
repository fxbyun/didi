package com.aicai.dao.oracle.testcase;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.aicai.dao.example.domain.BetGroupPlan;
import com.aicai.dao.example.domain.Member;
import com.aicai.dao.example.domain.constant.BetPlanHasLoadedAttachBit;
import com.aicai.dao.example.domain.query.QueryGroupPlanOption;
import com.aicai.dao.example.domain.result.Result;
import com.aicai.dao.example.service.BetPlanReadService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-db-oracleForUnit-bet.xml", "classpath:spring-dao-bet.xml", "classpath:spring-service-bet.xml", "classpath:spring-manager-bet.xml" })
public class NeedAttachBitTest {

	@Autowired
	BetPlanReadService planService;
	
	@Test
	public void test_不需获取member_期望成功(){
		Result<BetGroupPlan> result = planService.queryGroupPlanById(204606, new QueryGroupPlanOption());
		Assert.assertTrue("success", result.isSuccess());
		BetGroupPlan groupPlan = result.getModel();
		Assert.assertNotNull("groupPlan", groupPlan);
		Assert.assertFalse("should not loaded member", groupPlan.hasAttachLoaded(BetPlanHasLoadedAttachBit.member));
	}
	
	@Test
	public void test_需获取member_期望成功(){
		QueryGroupPlanOption option = new QueryGroupPlanOption();
		option.setNeedAttach(BetPlanHasLoadedAttachBit.member);
		
		Result<BetGroupPlan> result = planService.queryGroupPlanById(204606, option);
		Assert.assertTrue("success", result.isSuccess());
		BetGroupPlan groupPlan = result.getModel();
		Assert.assertNotNull("groupPlan", groupPlan);
		Assert.assertTrue("should loaded member", groupPlan.hasAttachLoaded(BetPlanHasLoadedAttachBit.member));
		
		Member member = groupPlan.getMember();
		Assert.assertEquals("member获取不正确", "南山一根葱,西丽刘德华", member.getDisplayName());
	}
}
