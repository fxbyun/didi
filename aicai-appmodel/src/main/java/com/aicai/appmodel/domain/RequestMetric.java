package com.aicai.appmodel.domain;

/**
 * 在用户处的最前端，虽然本次request不能准确传送本次的rrt回来，
 * 但他可以复合传送上次request"各典型dataSize段"的rrt回来,
 * springmvc处会用到这个类反序列化json数据
 * 
 * @author zhoufenglokki
 *
 */
public class RequestMetric {
	private long requestId;
	private int roundTripTime;
	private short requestDeltaTime;
	private short replyDeltaTime;
	private byte retryCount;
	private int parseErrCount;

	public long getRequestId() {
		return requestId;
	}

	public void setRequestId(long requestId) {
		this.requestId = requestId;
	}

	public int getRoundTripTime() {
		return roundTripTime;
	}

	public void setRoundTripTime(int roundTripTime) {
		this.roundTripTime = roundTripTime;
	}

	public byte getRetryCount() {
		return retryCount;
	}

	public void setRetryCount(byte retryCount) {
		this.retryCount = retryCount;
	}

	public int getParseErrCount() {
		return parseErrCount;
	}

	public void setParseErrCount(int parseErrCount) {
		this.parseErrCount = parseErrCount;
	}
}
