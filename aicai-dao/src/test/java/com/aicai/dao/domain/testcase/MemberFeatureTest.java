package com.aicai.dao.domain.testcase;

import java.util.Calendar;
import java.util.Date;

import junit.framework.Assert;

import org.junit.Test;

import com.aicai.dao.example.domain.Member;
public class MemberFeatureTest{
	
	@Test
	public void test_MemberFeature_综合枚举和时间(){
		Member member = new Member(1l);
		member.setDisplayName("周科荣");
		member.setLoginName("周科荣");
		member.setFeaturesVersion(0);
		//对象
		MemberIntro memberIntro = new MemberIntro();
		memberIntro.setAccount("周科荣");
		Calendar now = Calendar.getInstance();
		memberIntro.setApplyTime(now);
		memberIntro.setGenericIntro("简介");
		memberIntro.setHotIntro("红人简介");
		memberIntro.setMemberId(1L);
		memberIntro.setStatus(1);
		//枚举
		MemberStatus memberStatus = MemberStatus.ENABLE;
		member.setupFeature("memberIntro", memberIntro);
		member.setupFeature("memberStatus",memberStatus);
		String memberFeaturesText = member.getFeatures();
//		//将产生的JSON串传入到member对象中，模拟数据库取数据
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
		//手工生成JSON数据,并进行反序列化数据
		String buildJsonText = "{\"memberIntro\":{\"account\":\"周科荣\",\"applyTime\":1361001564106,\"genericIntro\":\"简介\",\"hotIntro\":\"红人简介\",\"memberId\":1,\"status\":1},\"memberStatus\":0}";
		//传入进member中进行JSON反序列化
		member.setFeatures(buildJsonText);
		memberIntro = member.getFeature("memberIntro", MemberIntro.class);
		Assert.assertEquals(dbmemberIntro.getAccount(), memberIntro.getAccount());
		Assert.assertEquals(dbmemberIntro.getGenericIntro(), memberIntro.getGenericIntro());
		Assert.assertEquals(Long.valueOf("1361001564106").longValue(), memberIntro.getApplyTime().getTimeInMillis());
		Assert.assertEquals(dbmemberIntro.getHotIntro(), memberIntro.getHotIntro());
		Assert.assertEquals(dbmemberIntro.getMemberId(), memberIntro.getMemberId());
		Assert.assertEquals(dbmemberIntro.getStatus(), memberIntro.getStatus());
		MemberStatus ms = member.getFeature("memberStatus",MemberStatus.class);
		Assert.assertEquals(dbmemberStatus, ms);
	}
	
	@Test
	public void test_测试时间的序列和反序列() {
		Date date = new Date();
		Member member = new Member(1l);
		member.setDisplayName("周科荣");
		member.setLoginName("周科荣");
		member.setFeaturesVersion(0);
		member.setupFeature("date", date);
		String featrueText = member.getFeatures();
		member.setFeatures(featrueText);
		Date newDate = member.getFeature("date", Date.class);
		Assert.assertEquals(date.getTime(), newDate.getTime());
		//进行反序列化
	}
	
	@Test
	public void test_测试有双引号或者单引号字符串的序列和反序列() {
		String weatherText = "今天天气不错，好像有\"雨\"";
		Member member = new Member(1l);
		member.setDisplayName("周科荣");
		member.setLoginName("周科荣");
		member.setupFeature("weather", weatherText);
		String featureText = member.getFeatures();
		member.setFeatures(featureText);
		//注意，字符串的不再需要使用getFeatrue(str,String.class)转化，使用这个方法会报错
		String featureWeatherText = member.getFeature("weather");
		Assert.assertEquals(weatherText, featureWeatherText);
	}
}
