package com.aicai.appmodel.domain.result.testcase;

import org.apache.commons.beanutils.BeanUtils;
import org.junit.Assert;
import org.junit.Test;

import com.aicai.appmodel.domain.result.BaseResult;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class ToStringTest {

	@Test
	public void test_filterSuccessJsonToClient() {
		BaseResult result = new BaseResult();
		JSONObject jsonMap = (JSONObject)JSON.toJSON(result);
		System.out.println(jsonMap);
		System.out.println(result.filterSuccessJsonToClient(jsonMap));
	}

	@Test
	public void test_BaseResult() throws Throwable {
		BaseResult result = new BaseResult();
		Assert.assertNotNull("BaseResult.toString()", result.toString());

		BaseResult result2 = new BaseResult();
		BeanUtils.cloneBean(result);
		BeanUtils.copyProperties(result2, result);
	}
}
