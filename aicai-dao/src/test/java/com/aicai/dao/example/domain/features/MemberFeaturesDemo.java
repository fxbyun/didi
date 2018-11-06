package com.aicai.dao.example.domain.features;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;

import com.aicai.dao.GenericDao;
import com.aicai.dao.example.domain.MemberJsonObject;

public class MemberFeaturesDemo {
	
	@Autowired
	private GenericDao  memberDao;
	
	public void updateMember() {
		MemberJsonObject member = memberDao.queryOne("MemberDao.findById", 1l);
		// 修改登录名称，根据业务需要，需要保存到次要属性字段中
		String orgnialLoginName = member.getLoginName();
		member.setupFeature("orgnialLoginName", orgnialLoginName);
		member.setLoginName("修改后的");
		// 记录用户上一次修改时间
		member.setupFeature("lastUpdateTime", member.getLastUpdateTime());
		member.setLastUpdateTime(Calendar.getInstance());
		// 这个属性最好更新的时候使用update member set features_version = features_version+1
		// where id = #{id}
		member.setFeaturesVersion(member.getFeaturesVersion() + 1);
		memberDao.updateByObj("MemberDao.updateLoginName", member);
	}
	
	public void  getFeaturesData(long id) {
		// 查询出需要的数据
		MemberJsonObject member = memberDao.queryOne("MemberDao.findById", 1l);
		String orgnialLoginName = member.getFeature("orgnialLoginName");
		System.out.println(orgnialLoginName);
		Calendar lastUpdateTime = member.getFeature("lastUpdateTime",Calendar.class);
		System.out.println(lastUpdateTime.getTimeInMillis());
	}
}
