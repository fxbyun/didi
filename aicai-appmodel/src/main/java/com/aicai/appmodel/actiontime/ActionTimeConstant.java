package com.aicai.appmodel.actiontime;

public class ActionTimeConstant {
	public static int actionType_notSet = 0;
	public static int actionType_start = 1;
	public static int actionType_startFinished = 1 << 1;
	public static int actionType_stop = 1 << 2;
	public static int actionType_stopFinished = 1 << 3;
}
