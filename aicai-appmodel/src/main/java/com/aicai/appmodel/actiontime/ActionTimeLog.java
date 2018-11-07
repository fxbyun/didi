package com.aicai.appmodel.actiontime;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 主要用来记录单机一些线程启动、开始停止、停止完成的时序，供监控发现问题.
 * 如果各个单机的时序不是只有唯一的线程在活动时，需要触发报警后人工介入
 * 
 * @author zhoufenglokki
 *
 */
public class ActionTimeLog implements Serializable {
	private static final long serialVersionUID = 1L;

	private LocalDateTime actionTime;
	private int actionType = ActionTimeConstant.actionType_notSet;

	public LocalDateTime getActionTime() {
		return actionTime;
	}

	public void setActionTime(LocalDateTime actionTime) {
		this.actionTime = actionTime;
	}

	public int getActionType() {
		return actionType;
	}

	public void setActionType(int actionType) {
		this.actionType = actionType;
	}

}
