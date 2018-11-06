package com.aicai.profiler;

import java.util.Calendar;
import java.util.List;

import com.aicai.profiler.connector.annotation.PrintLevel;
import com.aicai.profiler.core.ExecData;
import com.aicai.profiler.core.ExecNode;
import com.aicai.profiler.core.LogManager;
import com.aicai.profiler.core.LogManagerImpl;
import com.aicai.profiler.core.callMonitor.CallMark;
import com.aicai.profiler.core.callMonitor.CallMarkManager;
import com.aicai.profiler.core.callMonitor.CallMarkManagerImpl;

public class Profiler {
	private final static ThreadLocal<ExecData> dataHolder = new ThreadLocal<ExecData>();

	private static int ELAPSE_TIME_MS_TO_LOG = 500;
	
	private static LogManager logManager = new LogManagerImpl();
	private static CallMarkManager callMarkManager = new CallMarkManagerImpl();

	public static void start(String logName) {
		start(logName, ELAPSE_TIME_MS_TO_LOG);
	}

	public static void start(String logName, int elapseTimeMsToLog) {
		ExecData execData = dataHolder.get();
		ExecNode currentNode = new ExecNode(logName, System.currentTimeMillis(), elapseTimeMsToLog);
		if (execData == null) {
			execData = new ExecData();
			execData.root = currentNode;
			dataHolder.set(execData);
		} else {
			ExecNode parent = execData.currentNode;
			currentNode.setParent(parent);
			parent.getChildList().add(currentNode);
		}
		execData.currentNode = currentNode;
		execData.level++;
	}

	public static void stop(String matchLogNameWithStart) {
		ExecData execData = dataHolder.get();
		long nowTime = System.currentTimeMillis();
		execData.currentNode.setEndTime(nowTime);
		ExecNode child = execData.currentNode;
		execData.currentNode = child.getParent();
		execData.level--;
		if (execData.level == 0) {
			if ((nowTime - execData.root.getStartTime()) >= execData.root.getElapseTimeMsToLog() || execData.showflag) {
				logManager.showTree(execData.root);
			}
			dataHolder.remove();
		} else {
			if (child.getExecTime() >= child.getElapseTimeMsToLog()) {
				execData.showflag = true;
			}
		}
	}

	/**
	 * 记录一个被调用，包括方法名参数,记录级别
	 * 
	 * @param methodFullName
	 * @param argus
	 * @param logLevel
	 */
	public static void callMark(String methodFullName, Object[] args, PrintLevel printLevel) {
		// 产生一个调用信息
		CallMark callMark = new CallMark(Calendar.getInstance(), methodFullName, args);
		// 管理调用信息
		callMarkManager.showCallMark(callMark, printLevel);
	}

	/**
	 * 记录一个被调用
	 * 
	 * @param methodFullName
	 */
	public static void callMark(String methodFullName) {
		// 产生一个调用信息
		CallMark callMark = new CallMark(Calendar.getInstance(), methodFullName);
		// 管理调用信息
		callMarkManager.showCallMark(callMark);
	}
}
