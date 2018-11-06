package com.aicai.profiler.connector.annotation;
/**
 * 日志打印级别，用户可以通过传入的打印级别控制日志输出
 * 方法上的打印级别会覆盖类上的打印级别
 * 
 * @project aicai-profiler
 * @author van
 * @date 2012-11-2
 * Copyright (C) 2010-2012 www.2caipiao.com Inc. All rights reserved.
 */
public enum PrintLevel {
	INFO,

	DEBUG,

	ERROR,

	WARN
}
