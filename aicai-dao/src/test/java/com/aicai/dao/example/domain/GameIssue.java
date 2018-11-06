package com.aicai.dao.example.domain;

import java.io.Serializable;
import java.util.Calendar;
import org.apache.ibatis.type.Alias;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeConfig;

@Alias("GameIssue")
public class GameIssue implements Serializable {
	private static final long serialVersionUID = 2086652067893164897L;

	private long id;

	/** 彩期编号 */
	private String issueNo;

	private Game game;

	/** 开始销售时间(yyyy-MM-dd HH:mm) */
	private Calendar startTime;

	/** 停止销售时间(yyyy-MM-dd HH:mm) */
	private Calendar endTime;

	/** 开奖时间(yyyy-MM-dd HH:mm) */
	private Calendar openTime;

	/** 是否是当前销售期 */
	private boolean isCurrent = Boolean.FALSE;;

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

	public Calendar getStartTime() {
		return startTime;
	}

	public void setStartTime(Calendar startTime) {
		this.startTime = startTime;
	}

	public Calendar getEndTime() {
		return endTime;
	}

	public void setEndTime(Calendar endTime) {
		this.endTime = endTime;
	}

	public Calendar getOpenTime() {
		return openTime;
	}

	public void setOpenTime(Calendar openTime) {
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
		return JSON.toJSONStringWithDateFormat(this, "yyyy-MM-dd HH:mm:ss");
	}
}
