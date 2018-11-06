package com.aicai.profiler.counter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InvokeCountTask implements Runnable {

	private String methodName;
	private String regex;
	private String serviceAndMethodRegex = "\\.(\\w+Service\\w+\\.\\w+)";	
	private Pattern p = Pattern.compile(serviceAndMethodRegex);
	
	
	public InvokeCountTask(String methodName,String regex) {
		this.methodName = methodName;
		this.regex = regex;
	}

	@Override
	public void run() {
		// 提取服务名和方法名
		if (methodName.matches(regex)) {
			Matcher m = p.matcher(methodName);
			if (m.find()) {
				methodName = m.group(1);
			}
			InvokeCountData.incrementCount(methodName);
		}
	}
	
	public static void main(String[] args) {
		String methodName = "com.aicai.betplan.service.impl.PlanServiceImpl.queryPrintEndTime";
		String serviceAndMethodRegex = "\\.(\\w+Service\\w+\\.\\w+)";
		Pattern p = Pattern.compile(serviceAndMethodRegex);
		Matcher m = p.matcher(methodName);
		if (m.find()) {
			methodName = m.group(1);
		}
		System.out.println(methodName);
	}
}
