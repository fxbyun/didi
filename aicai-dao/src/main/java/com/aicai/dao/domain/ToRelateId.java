package com.aicai.dao.domain;

/**
 * 用在DO定义上，
 * 区别于RelateId，有些数据是操作的源头，没有fromRelateId
 * @author zhoufeng
 *
 */
public interface ToRelateId {
	public long getToRelateId();
	public int getToRelateType();
}
