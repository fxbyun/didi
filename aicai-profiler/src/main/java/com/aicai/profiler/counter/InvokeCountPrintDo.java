package com.aicai.profiler.counter;

import java.util.Calendar;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class InvokeCountPrintDo {
	
	private String m;
	private Calendar ft;
	private Calendar lt;
	private long tc;
	private long ac;

	public InvokeCountPrintDo(InvokeCountDo countDo) {
		this.m = countDo.getSimpleMethodName();
		this.ft = countDo.getPreRecTime();
		this.lt = countDo.getLastUpdateTime();
		this.tc = countDo.getTotalCount().get();
		this.ac = countDo.getThisCount();
	}

	public String getM() {
		return m;
	}

	public void setM(String m) {
		this.m = m;
	}

	public Calendar getFt() {
		return ft;
	}

	public void setFt(Calendar ft) {
		this.ft = ft;
	}

	public Calendar getLt() {
		return lt;
	}

	public void setLt(Calendar lt) {
		this.lt = lt;
	}

	public long getTc() {
		return tc;
	}

	public void setTc(long tc) {
		this.tc = tc;
	}

	public long getAc() {
		return ac;
	}

	public void setAc(long ac) {
		this.ac = ac;
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this, SerializerFeature.UseISO8601DateFormat, SerializerFeature.UseSingleQuotes);
	}

}
