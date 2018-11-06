package com.aicai.dao.readwrite.domain;

public class ReadWriteDaoData {
	
	private boolean enableReadWrite;
	private Integer readRate;
	private String daoName;
	
	
	public boolean isEnableReadWrite() {
		return enableReadWrite;
	}
	public void setEnableReadWrite(boolean enableReadWrite) {
		this.enableReadWrite = enableReadWrite;
	}
	public Integer getReadRate() {
		return readRate;
	}
	public void setReadRate(Integer readRate) {
		this.readRate = readRate;
	}
	public String getDaoName() {
		return daoName;
	}
	public void setDaoName(String daoName) {
		this.daoName = daoName;
	}
}
