package com.aicai.dao.readwrite.domain;

import java.util.ArrayList;
import java.util.List;

public class WebControllerResult {
	
	private List<String> errormsgList = new ArrayList<String>();
	private ReadWriteDaoData daoData;

	public boolean isSuccess() {
		return errormsgList.size() == 0;
	}

	public void addErrormsg(String msg) {
		errormsgList.add(msg);
	}
	
	public ReadWriteDaoData getDaoData() {
		return daoData;
	}

	public void setDaoData(ReadWriteDaoData daoData) {
		this.daoData = daoData;
	}

	public List<String> getErrormsgList() {
		return errormsgList;
	}

	public void setErrormsgList(List<String> errormsgList) {
		this.errormsgList = errormsgList;
	}
}
