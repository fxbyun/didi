package com.aicai.dao.example.domain;

import java.math.BigDecimal;
import java.util.Calendar;

import com.alibaba.fastjson.JSON;

public class BetGroupPlan extends BetPlan {
	private static final long serialVersionUID = 4084426147562937061L;

	/** 方案标题 */
	private String title;

	/** 方案描述 */
	private String introduce;

	/** 每份金额 */
	private BigDecimal perAmount = BigDecimal.ONE;

	/** 总份数 */
	private int part;

	/** 已认购份数 */
	private int soldPart;

	/** 发起人保底份数 */
	private int reservePart;

	/*** 最低跟单金额 (跟单份数*方案每份金额 ) */
	private BigDecimal minFollowAmount;

	/** 截止跟单时间 */
	private Calendar joinEndTime;

	/** 是否自动跟单 */
	private boolean isFollow;

	/** 方案内容公开类型 */
	private int publicType;

	/** 合买方案状态 */
	private int planStatus;

	/** 奖金提成比例 比例值 是整数，3、5表示3% 5% */
	private BigDecimal prizeDeduct = BigDecimal.ZERO;

	/** 参与人数 */
	private int joinPerson;

	/** 回报率 **/
	private BigDecimal payBack = BigDecimal.ZERO;

	/** 置顶位置(0=不置顶,1-首页置顶，2彩种置，3首页+彩种) */
	private int topLocation = 0;

	/** 被查看次int */
	private int viewCount;

	/** 幸运值(火热旺) */
	private int luckValue;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIntroduce() {
		return introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

	public BigDecimal getPerAmount() {
		return perAmount;
	}

	public void setPerAmount(BigDecimal perAmount) {
		this.perAmount = perAmount;
	}

	public int getPart() {
		return part;
	}

	public void setPart(int part) {
		this.part = part;
	}

	public int getSoldPart() {
		return soldPart;
	}

	public void setSoldPart(int soldPart) {
		this.soldPart = soldPart;
	}

	public int getReservePart() {
		return reservePart;
	}

	public void setReservePart(int reservePart) {
		this.reservePart = reservePart;
	}

	public BigDecimal getMinFollowAmount() {
		return minFollowAmount;
	}

	public void setMinFollowAmount(BigDecimal minFollowAmount) {
		this.minFollowAmount = minFollowAmount;
	}

	public Calendar getJoinEndTime() {
		return joinEndTime;
	}

	public void setJoinEndTime(Calendar joinEndTime) {
		this.joinEndTime = joinEndTime;
	}

	public boolean isFollow() {
		return isFollow;
	}

	public void setFollow(boolean isFollow) {
		this.isFollow = isFollow;
	}

	public int getPublicType() {
		return publicType;
	}

	public void setPublicType(int publicType) {
		this.publicType = publicType;
	}

	public int getPlanStatus() {
		return planStatus;
	}

	public void setPlanStatus(int planStatus) {
		this.planStatus = planStatus;
	}

	public BigDecimal getPrizeDeduct() {
		return prizeDeduct;
	}

	public void setPrizeDeduct(BigDecimal prizeDeduct) {
		this.prizeDeduct = prizeDeduct;
	}

	public int getJoinPerson() {
		return joinPerson;
	}

	public void setJoinPerson(int joinPerson) {
		this.joinPerson = joinPerson;
	}

	public BigDecimal getPayBack() {
		return payBack;
	}

	public void setPayBack(BigDecimal payBack) {
		this.payBack = payBack;
	}

	public int getTopLocation() {
		return topLocation;
	}

	public void setTopLocation(int topLocation) {
		this.topLocation = topLocation;
	}

	public int getViewCount() {
		return viewCount;
	}

	public void setViewCount(int viewCount) {
		this.viewCount = viewCount;
	}

	public int getLuckValue() {
		return luckValue;
	}

	public void setLuckValue(int luckValue) {
		this.luckValue = luckValue;
	}

	@Override
	public String toString(){
		return "{className:'" + getClass().getName() + "'}," + JSON.toJSONString(this);
	}
}
