package com.aicai.dao.domain;

/**
 * 定义在DO上，将数据从原始地方传送(同步)到另一个地方时，用来compareAndSet判断是否能在"另一个地方"保存成功。
 * 原始地方的每次update，都要加一。
 * 这不是乐观锁，所以不命名为普遍认知的乐观锁version
 * @author zhoufeng
 *
 */
public interface SyncVersion {
	public long getSyncVersion();
}
