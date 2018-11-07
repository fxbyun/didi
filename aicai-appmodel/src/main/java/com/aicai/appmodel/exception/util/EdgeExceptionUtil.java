package com.aicai.appmodel.exception.util;

public class EdgeExceptionUtil {
	public static RuntimeException newRuntimeExceptionWithoutCause(Exception cause) {
		RuntimeException exceptResult = new RuntimeException(cause.getLocalizedMessage());
		exceptResult.setStackTrace(cause.getStackTrace());
		return exceptResult;
	}
	
	public static Exception exceptionWithStackTraceFromCause(Exception newException, Exception cause){
		newException.setStackTrace(cause.getStackTrace());
		return newException;
	}
}
