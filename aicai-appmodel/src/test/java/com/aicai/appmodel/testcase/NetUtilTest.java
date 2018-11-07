package com.aicai.appmodel.testcase;

import org.junit.Assert;
import org.junit.Test;

import com.aicai.appmodel.clusterutil.NetUtil;

public class NetUtilTest {

	@Test
	/**
	 * 麻烦各同学在linux/windows下的物理机/虚拟机下跑跑，进行各种测试
	 */
	public void test_getMyIp_期望返回正确SiteLocalIp() {
		String ip = NetUtil.getMyIp();
		System.out.println("myIp: " + ip);
		if (ip.startsWith("10.") | ip.startsWith("172.") | ip.startsWith("192.")) {
			// 正确
		}else{
			Assert.fail("获取到的ip地址不是正确的内网地址:" + ip);
		}
	}
}
