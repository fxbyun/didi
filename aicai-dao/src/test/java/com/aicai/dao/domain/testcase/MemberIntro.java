package com.aicai.dao.domain.testcase;

import java.io.Serializable;
import java.util.Calendar;
public class MemberIntro implements Serializable {/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * (用户id)
	 *  member_id
	 */
	private Long memberId;
	
	/**
	 * account(冗余字段,用户帐号) 
	 */
	private String account;
	
	/**
	 * generic_intro(一般介绍),
	 */
	private String genericIntro;
	/**
	 * hot_intro(红人介绍)
	 */
	private String hotIntro;
	
	/***
	 * 审核状态 
	 * @link com.aicaipiao.common.entity.enumerated.IntorApplyStatus 
	 */
	private Integer status;
	
 
	/***
     *  申请介绍时间,只有申请介绍时修改,根据这个时间判断一个月内只能修改一次
     *  审核不通过的，允许修改，时间不限制
     *  
     */
    private Calendar applyTime;
 
	
	public MemberIntro() {
		super();
	}
	public Long getMemberId() {
		return memberId;
	}
	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getGenericIntro() {
		return genericIntro;
	}
	public void setGenericIntro(String genericIntro) {
		this.genericIntro = genericIntro;
	}
	public String getHotIntro() {
		return hotIntro;
	}
	public void setHotIntro(String hotIntro) {
		this.hotIntro = hotIntro;
	}
    public Integer getStatus() {
        return status;
    }
    public void setStatus(Integer status) {
        this.status = status;
    }
 

    public Calendar getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Calendar applyTime) {
        this.applyTime = applyTime;
    }
}
