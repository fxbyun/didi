package com.aicai.dao.oracle.testcase;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.aicai.dao.GenericDao;
import com.aicai.dao.example.domain.BetGroupPlan;
import com.aicai.dao.example.domain.Member;
import com.aicai.dao.example.domain.constant.BetPlanHasLoadedAttachBit;

@ContextConfiguration(locations = { "classpath:spring-db-oracleForUnit-bet.xml", "classpath:spring-dao-bet.xml" })
public class HasLoadedAttachTest extends AbstractJUnit4SpringContextTests{

	@Autowired
	private GenericDao groupPlanDao;
	
	@Test
	public void test(){
		BetGroupPlan groupPlan = groupPlanDao.queryOne("BetPlanDao.selectBetGroupPlanByPrimaryKey", 204606);
		System.out.println(groupPlan);
		Assert.assertNotNull(groupPlan);
		
		System.out.println(groupPlan.getMember());
		Assert.assertNotNull("member不应为null，应是个有id的临时对象", groupPlan.getMember());
		Assert.assertEquals("因此表直接有此字段，应能获取memberId", 46501, groupPlan.getMember().getId());
		Assert.assertNull("因没手动获取，应不能获得loginName,应只有memberId才有值", groupPlan.getMember().getLoginName());
		Assert.assertFalse("因member中只有id是真实DB数据,所以此attach对象为false", groupPlan.hasAttachLoaded(BetPlanHasLoadedAttachBit.member));
		
		System.out.println(groupPlan.getOrder());
		Assert.assertNotNull("order不应为null，应是个有id的临时对象", groupPlan.getOrder());
		Assert.assertEquals("因此表直接有此字段，应能获取orderId", 239610, groupPlan.getOrder().getId());
		Assert.assertNull("因没手动获取，应不能获得order.orderNo,应只有order.id才有值", groupPlan.getOrder().getOrderNo());
		
		System.err.println(groupPlan.getOrder().getId() + " vs " + groupPlan.getOrderNo());
		Assert.assertNotNull("因此表多余地冗余了orderNo,plan.orderNo应有值", groupPlan.getOrderNo());
		
		String orderNo = groupPlan.getOrderNo();
		String suffix = groupPlan.getOrderNo().substring(orderNo.length() - "239610".length());
		System.err.println("orderId:" +groupPlan.getOrder().getId()  + " vs orderNo:" + suffix);		
		Assert.assertFalse("由于hibernate配置错误,造成orderNo一般不等于orderId,而是偏离一些,所以order表错误使用成两种唯一主键,但orderId/orderNo不会有重复冲突", "239610".equals(suffix));
		
		if(!groupPlan.hasAttachLoaded(BetPlanHasLoadedAttachBit.member)){
			/**
			模拟member从DB中或缓存中取出来，挂在groupPlan供后续处理使用,如果只是想用memberId是不用另取数据的。
			hibernate中只是想memberId会不会关联取一次数据？比如用memberId打个log
			如果后端不需要member其他数据，他也不需要获取/补充完整member就可以传给前端，由前端根据自己的需要再次获取member完整数据*/
			Member member = new Member(groupPlan.getMember().getId());
			member.setLoginName(groupPlan.getAccount());
			member.setDisplayName("南山一根葱,西丽刘德华");
			groupPlan.setMember(member);
		}
		Assert.assertTrue("member中应已有完整对象数据", groupPlan.hasAttachLoaded(BetPlanHasLoadedAttachBit.member));
		Assert.assertEquals("member account", groupPlan.getAccount(), groupPlan.getMember().getLoginName());
	}
}
