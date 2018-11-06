package com.aicai.profiler.core.callMonitor;

import com.aicai.profiler.connector.annotation.PrintLevel;

public interface CallMarkManager {
	public void showCallMark(CallMark callMark);
	public void showCallMark(CallMark callMark,PrintLevel printLevel);
}
