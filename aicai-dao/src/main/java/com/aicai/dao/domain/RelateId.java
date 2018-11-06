package com.aicai.dao.domain;

/**
 * 用在DO定义上，
 * @author zhoufeng
 *
 */
public interface RelateId {
	public long getFromRelateId();
	public int getFromRelateType();
	public long getToRelateId();
	public int getToRelateType();
}
