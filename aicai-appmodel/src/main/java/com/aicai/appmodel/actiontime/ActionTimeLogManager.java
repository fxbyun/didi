package com.aicai.appmodel.actiontime;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ActionTimeLogManager {
	private List<ActionTimeLog> startStopTimeLog = new CopyOnWriteArrayList<>();
	private volatile int timeLogCount = 0;

	public void addActionTimeLog(int actionType) {
		timeLogCount++;
		if (startStopTimeLog.size() > 200) {
			startStopTimeLog.clear();
		}

		ActionTimeLog timeLog = new ActionTimeLog();
		timeLog.setActionType(actionType);
		timeLog.setActionTime(LocalDateTime.now());
		startStopTimeLog.add(timeLog);
	}

	public List<ActionTimeLog> getStartStopTimeLog() {
		return startStopTimeLog;
	}

	public int getTimeLogCount() {
		return timeLogCount;
	}

}
