package com.aicai.dao.domain.testcase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import junit.framework.Assert;

import org.junit.Test;

import com.aicai.dao.example.domain.MemberJsonObject;

/**
 * http://wiki.inzwc.com/wiki/index.php/Features
 *  
 * @author haibin
 */
public class MemberJsonObjectFeatureTest {
	@Test
	public void test_MemberFeature_综合枚举和时间() {
		MemberJsonObject member = new MemberJsonObject(1l);
		member.setDisplayName("周科荣");
		member.setLoginName("周科荣");
		member.setFeaturesVersion(0);
		// 对象
		MemberIntro memberIntro = new MemberIntro();
		memberIntro.setAccount("周科荣");
		Calendar now = Calendar.getInstance();
		memberIntro.setApplyTime(now);
		memberIntro.setGenericIntro("简介");
		memberIntro.setHotIntro("红人简介");
		memberIntro.setMemberId(1L);
		memberIntro.setStatus(1);
		// 枚举
		MemberStatus memberStatus = MemberStatus.ENABLE;
		member.setupFeature("memberIntro", memberIntro);
		member.setupFeature("memberStatus", memberStatus);
		String memberFeaturesText = member.getFeatures();
		// //将产生的JSON串传入到member对象中，模拟数据库取数据
		member.setFeatures(memberFeaturesText);
		MemberStatus dbmemberStatus = member.getFeature("memberStatus", MemberStatus.class);
		Assert.assertEquals(dbmemberStatus, memberStatus);
		MemberIntro dbmemberIntro = member.getFeature("memberIntro", MemberIntro.class);
		Assert.assertEquals(dbmemberIntro.getAccount(), memberIntro.getAccount());
		Assert.assertEquals(dbmemberIntro.getGenericIntro(), memberIntro.getGenericIntro());
		Assert.assertEquals(dbmemberIntro.getApplyTime().getTimeInMillis(), memberIntro.getApplyTime().getTimeInMillis());
		Assert.assertEquals(dbmemberIntro.getHotIntro(), memberIntro.getHotIntro());
		Assert.assertEquals(dbmemberIntro.getMemberId(), memberIntro.getMemberId());
		Assert.assertEquals(dbmemberIntro.getStatus(), memberIntro.getStatus());
		// 手工生成JSON数据,并进行反序列化数据
		String buildJsonText = "{\"memberIntro\":{\"account\":\"周科荣\",\"applyTime\":1361001564106,\"genericIntro\":\"简介\",\"hotIntro\":\"红人简介\",\"memberId\":1,\"status\":1},\"memberStatus\":0}";
		// 传入进member中进行JSON反序列化
		member.setFeatures(buildJsonText);
		memberIntro = member.getFeature("memberIntro", MemberIntro.class);
		Assert.assertEquals(dbmemberIntro.getAccount(), memberIntro.getAccount());
		Assert.assertEquals(dbmemberIntro.getGenericIntro(), memberIntro.getGenericIntro());
		Assert.assertEquals(Long.valueOf("1361001564106").longValue(), memberIntro.getApplyTime().getTimeInMillis());
		Assert.assertEquals(dbmemberIntro.getHotIntro(), memberIntro.getHotIntro());
		Assert.assertEquals(dbmemberIntro.getMemberId(), memberIntro.getMemberId());
		Assert.assertEquals(dbmemberIntro.getStatus(), memberIntro.getStatus());
		MemberStatus ms = member.getFeature("memberStatus", MemberStatus.class);
		Assert.assertEquals(dbmemberStatus, ms);
	}

	@Test
	public void test_测试时间的序列化和反序列化() {
		MemberJsonObject member = new MemberJsonObject(1l);
		member.setDisplayName("周科荣");
		member.setLoginName("周科荣");
		member.setFeaturesVersion(0);
		Calendar nowInstance = Calendar.getInstance();
		member.setupFeature("createTime", nowInstance);
		String memberFeaturesText = member.getFeatures();
		// 进行反序列化
		member.setFeatures(memberFeaturesText);
		Calendar otherNow = member.getFeature("createTime", Calendar.class);
		Assert.assertEquals(nowInstance.getTimeInMillis(), otherNow.getTimeInMillis());
		// 自写json串进行反序列化
		String timeText = "2012-12-01 09:30:05";

		String createTimeJson = "{\"createTime\":\"2012-12-01T09:30:05\"}";
		member.setFeatures(createTimeJson);
		System.err.println(member.getFeatures());
		System.err.println("有些小缺陷，但应由feature接口实现者去处理：" + member.getFeature("createTime"));

		Date sdate = member.getFeature("createTime", Date.class);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Assert.assertEquals(sdf.format(sdate), timeText);
	}

	@Test
	public void test() {
		MemberJsonObject member = new MemberJsonObject(1l);
		System.out.println(member.getFeatures());
	}
}
