package com.aicai.dao.example.domain;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;
import org.joda.time.DateTime;

import com.alibaba.fastjson.JSON;

@Alias("JodaGameIssue")
public class JodaGameIssue implements Serializable {
	private static final long serialVersionUID = 6609849146998626835L;

	private long id;

	/** 彩期编号 */
	private String issueNo;

	private Game game;

	/** 开始销售时间(yyyy-MM-dd HH:mm) */
	private DateTime startTime;

	/** 停止销售时间(yyyy-MM-dd HH:mm) */
	private DateTime endTime;

	/** 开奖时间(yyyy-MM-dd HH:mm) */
	private DateTime openTime;

	/** 是否是当前销售期 */
	private boolean isCurrent;

	/** 彩期状态 */
	private int status = 0;

	/** 上一期 */
	private long preIssueId;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getIssueNo() {
		return issueNo;
	}

	public void setIssueNo(String issueNo) {
		this.issueNo = issueNo;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public DateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(DateTime startTime) {
		this.startTime = startTime;
	}

	public DateTime getEndTime() {
		return endTime;
	}

	public void setEndTime(DateTime endTime) {
		this.endTime = endTime;
	}

	public DateTime getOpenTime() {
		return openTime;
	}

	public void setOpenTime(DateTime openTime) {
		this.openTime = openTime;
	}

	public boolean isCurrent() {
		return isCurrent;
	}

	public void setCurrent(boolean isCurrent) {
		this.isCurrent = isCurrent;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public long getPreIssueId() {
		return preIssueId;
	}

	public void setPreIssueId(long preIssueId) {
		this.preIssueId = preIssueId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String toString() {
		return JSON.toJSONString(this);
	}
}
