package com.aicai.profiler.counter;

import java.util.Calendar;
import java.util.concurrent.atomic.AtomicLong;
/**
 * 计数器，为了防止并发大时发生atomicLong的失败重试，使用固定线程来更新调用次数
 * @author kerong.zhou
 */
public class InvokeCountDo implements Comparable<InvokeCountDo> {

	private AtomicLong totalCount = new AtomicLong(0);// 总计数
	private long preRecCount = 0l; // 前一次计数
	private Calendar preRecTime = Calendar.getInstance();
	private Calendar firstInvokeTime = Calendar.getInstance();
	private Calendar lastUpdateTime = Calendar.getInstance();
	private String simpleMethodName;
	
	public InvokeCountDo(String simpleMethodName) {
		this.simpleMethodName = simpleMethodName;
	}
	
	public void increCount() {
		totalCount.incrementAndGet();
		lastUpdateTime = Calendar.getInstance();
	}

	@Override
	public int compareTo(InvokeCountDo o) {
		if (totalCount.get() - o.getTotalCount().get() > 0)
			return 1;
		else if (totalCount.get() - o.getTotalCount().get() == 0)
			return getThisCount() - o.getThisCount() > 0 ? 1 : -1;
		else if (totalCount.get() - o.getTotalCount().get() < 0)
			return -1;
		return -1;
	}

	public Long getThisCount() {
		return totalCount.get() - preRecCount;

	}

	public AtomicLong getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(AtomicLong totalCount) {
		this.totalCount = totalCount;
	}

	public void updatePreRecCount() {
		this.preRecCount = this.totalCount.get();
		this.preRecTime = Calendar.getInstance();
	}

	public Calendar getPreRecTime() {
		return preRecTime;
	}

	public void setPreRecTime(Calendar preRecTime) {
		this.preRecTime = preRecTime;
	}

	public String getSimpleMethodName() {
		return simpleMethodName;
	}

	public Calendar getLastUpdateTime() {
		return lastUpdateTime;
	}

	public Calendar getFirstInvokeTime() {
		return firstInvokeTime;
	}
}
