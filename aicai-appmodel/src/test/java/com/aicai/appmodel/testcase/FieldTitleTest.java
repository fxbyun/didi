package com.aicai.appmodel.testcase;

import java.util.Map;

import org.junit.Test;

public class FieldTitleTest {
	@Test
	public void test() {
		Map<Long, String> bizTypeMap = BizTypeConstant.getBiztypeMap();
		System.out.println(bizTypeMap.values());
	}
}
