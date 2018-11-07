package com.aicai.appmodel.domain.option;

public interface UserEndpointInfo {
	/**
	 * app自知ip
	 * 
	 * @return
	 */
	public String getUserIp1();

	/**
	 * 机房边界forwardIp
	 * 
	 * @return
	 */
	public String getUserIp2();

	/**
	 * 网卡mac地址
	 * 
	 * @return
	 */
	public String getUserMacAddr();

	/**
	 * IMEI/手机号/苹果机器号
	 * 
	 * @return
	 */
	public String getUserMachineId();

	/**
	 * app处持久化id
	 * 
	 * @return
	 */
	public String getUserMachineTraceId();
}
