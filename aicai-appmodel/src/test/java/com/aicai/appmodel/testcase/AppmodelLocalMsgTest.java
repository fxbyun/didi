package com.aicai.appmodel.testcase;

import org.junit.Assert;
import org.junit.Test;

import com.aicai.appmodel.constant.UnknownErrorCodeConstant;
import com.aicai.appmodel.localmsg.LocalMsgManager;

public class AppmodelLocalMsgTest {
	
	@Test
	public void test_测试加载默认属性文件期望返回正确的值() {
		LocalMsgManager manager = new LocalMsgManager();
		manager.init();
		Assert.assertTrue("加载的属性文件信息不正确", UnknownErrorCodeConstant.getMsg("testCaseValue").equals("中国"));
	}
}
