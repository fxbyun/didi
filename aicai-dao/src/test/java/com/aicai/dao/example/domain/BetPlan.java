package com.aicai.dao.example.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

import com.aicai.dao.domain.HasLoadedAttach;
import com.aicai.dao.example.domain.constant.BetPlanHasLoadedAttachBit;
import com.aicai.dao.util.HasLoadedAttachUtil;

public abstract class BetPlan implements HasLoadedAttach, Serializable {
	private static final long serialVersionUID = -8161153894990430542L;

	protected long attachBit;

	private long id;

	/** 方案编号 */
	private String planNo;

	private Member member;

	/** 用户帐号 */
	private String account;

	private Game game;

	/** 父彩种 */
	private Game parentGame;

	/** 彩期编号 */
	private String issueNo;

	/** 方案总金额 */
	private BigDecimal amount;

	/** 方案倍数 */
	private int multiple;

	/** 方案总注数 */
	private int betCount;

	/** 投注方式 */
	private int betType;

	/** 玩法描述 */
	private String playTypeDesc;

	/** 选号方式 */
	private int selectType;

	/** 对应的订单 */
	private BetOrder order;

	/** 订单编号 */
	private String orderNo;

	/**
	 * 对应方案内容 为null表示未loadedAttach
	 */
	private transient List<PlanContent> planContentList;

	/** 对于单场,竞猜类彩种(按赛事维护),记录方案中时间最早的场次时间点 */
	private long minTimePoint;

	/** 对于单场,竞猜类彩种(北单，篮彩，竞足)的开奖场次，记录方案中时间最迟的场次ID 是race表主键 */
	private long maxRaceId;

	/**
	 * 对于单场,竞猜类彩种方案购买场次 格式 no1,no2,no3 如北单 001,002,003,042
	 * /竞彩篮球110201301,110201302,110201303/竞彩足球110201002,
	 **/
	private String raceNo;

	/** 是否单关（固定奖） T (过关)固定奖 F 单关(浮动奖) 默认值F浮动奖 */
	private boolean floatAward = false;

	/** 是否上传文件 */
	private boolean upload = false;

	/** 上传时间 */
	private Calendar uploadTime;

	/** 打票截止时间 */
	private Calendar printStopTime;

	/** 方案热度类型 */
	private int hotStatus = 0;

	/** 创建时间 */
	private Calendar createTime;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getAttachBit() {
		return attachBit;
	}

	public void setAttachBit(long attachFlag) {
		this.attachBit = attachFlag;
	}

	public String getPlanNo() {
		return planNo;
	}

	public void setPlanNo(String planNo) {
		this.planNo = planNo;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		if(member == null){
			throw new IllegalArgumentException("从数据库出来的BetPlan的member不会为null,最低限度是个有memberId的临时对象,是否有什么别的地方错了");
		}
		this.member = member;
		this.attachBit |= BetPlanHasLoadedAttachBit.member;
	}

	/**
	 * 供从DB中取出记录使用
	 * @param memberId
	 */
	public void setMemberId(long memberId) {
		this.member = new Member(memberId);
		removeAttachFlag(BetPlanHasLoadedAttachBit.member);
	}

	protected void removeAttachFlag(long flagBit) {
		this.attachBit &= flagBit ^ Long.MAX_VALUE;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public Game getParentGame() {
		return parentGame;
	}

	public void setParentGame(Game parentGame) {
		this.parentGame = parentGame;
	}

	public String getIssueNo() {
		return issueNo;
	}

	public void setIssueNo(String issueNo) {
		this.issueNo = issueNo;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public int getMultiple() {
		return multiple;
	}

	public void setMultiple(int multiple) {
		this.multiple = multiple;
	}

	public int getBetCount() {
		return betCount;
	}

	public void setBetCount(int betCount) {
		this.betCount = betCount;
	}

	public int getBetType() {
		return betType;
	}

	public void setBetType(int betType) {
		this.betType = betType;
	}

	public String getPlayTypeDesc() {
		return playTypeDesc;
	}

	public void setPlayTypeDesc(String playTypeDesc) {
		this.playTypeDesc = playTypeDesc;
	}

	public int getSelectType() {
		return selectType;
	}

	public void setSelectType(int selectType) {
		this.selectType = selectType;
	}

	public BetOrder getOrder() {
		return order;
	}

	public void setOrder(BetOrder order) {
		if(order == null){
			throw new IllegalArgumentException("从数据库出来的BetPlan的order不会为null,最低限度是个有orderId的临时对象,是否有什么别的地方错了");
		}
		this.order = order;
		this.attachBit |= BetPlanHasLoadedAttachBit.betOrder;
		setOrderNo(order.getOrderNo());
	}

	/**
	 * 供从DB中取出记录使用
	 * @param orderId
	 */
	public void setOrderId(long orderId) {
		this.order = new BetOrder(orderId);
		removeAttachFlag(BetPlanHasLoadedAttachBit.betOrder);
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public List<PlanContent> getPlanContentList() {
		return planContentList;
	}

	public void setPlanContentList(List<PlanContent> planContentList) {
		this.planContentList = planContentList;
	}

	public long getMinTimePoint() {
		return minTimePoint;
	}

	public void setMinTimePoint(long minTimePoint) {
		this.minTimePoint = minTimePoint;
	}

	public long getMaxRaceId() {
		return maxRaceId;
	}

	public void setMaxRaceId(long maxRaceId) {
		this.maxRaceId = maxRaceId;
	}

	public String getRaceNo() {
		return raceNo;
	}

	public void setRaceNo(String raceNo) {
		this.raceNo = raceNo;
	}

	public boolean isFloatAward() {
		return floatAward;
	}

	public void setFloatAward(boolean floatAward) {
		this.floatAward = floatAward;
	}

	public boolean isUpload() {
		return upload;
	}

	public void setUpload(boolean upload) {
		this.upload = upload;
	}

	public Calendar getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(Calendar uploadTime) {
		this.uploadTime = uploadTime;
	}

	public Calendar getPrintStopTime() {
		return printStopTime;
	}

	public void setPrintStopTime(Calendar printStopTime) {
		this.printStopTime = printStopTime;
	}

	public int getHotStatus() {
		return hotStatus;
	}

	public void setHotStatus(int hotStatus) {
		this.hotStatus = hotStatus;
	}

	public Calendar getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Calendar createTime) {
		this.createTime = createTime;
	}

	@Override
	public boolean hasAttachLoaded(long bitForPart) {
		return HasLoadedAttachUtil.hasAttachLoaded(attachBit, bitForPart);
	}

}
