package com.aicai.dao.domain.testcase;

import java.util.Arrays;
import java.util.List;

/**
 * 客户状态
 * 
 * @author yaya
 * 
 */
public enum MemberStatus{
	
	ENABLE   (0, "正常"), 
	DISABLE  (1, "失效");
	/** 顺序 */
	private int index;

	/** 描述 */
	private String description;

	private MemberStatus(int index, String description) {
		this.index = index;
		this.description = description;
	}

	public static List<MemberStatus> getAll() {
		return Arrays.asList(MemberStatus.class.getEnumConstants());
	}

	public String toString() {
		return this.description;
	}
}
