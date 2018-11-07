package com.aicai.appmodel.clusterutil;

@Deprecated
/**
 * 请直接转用NetUtil.getMyIp()
 *
 */
public class MyIpFactory {

	public void setHostNameToConnect(String hostNameToConnect) {
	}

	public String getIp() throws Exception {
		return NetUtil.getMyIp();
	}
}
