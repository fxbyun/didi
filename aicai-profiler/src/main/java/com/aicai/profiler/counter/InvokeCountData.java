package com.aicai.profiler.counter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InvokeCountData {
	
	private static final Map<String, InvokeCountDo> invokeCountDoMap = new ConcurrentHashMap<String, InvokeCountDo>();
	
	private static Logger log = LoggerFactory.getLogger("profiler.counter.log");
	
	private static InvokeCountDo getInvokeCountDo(String simpleMethodName) {
		if (!invokeCountDoMap.containsKey(simpleMethodName)) {
			InvokeCountDo countDo = new InvokeCountDo(simpleMethodName);
			invokeCountDoMap.put(simpleMethodName, countDo);
		}
		return invokeCountDoMap.get(simpleMethodName);
	}
	
	public static void incrementCount(String simpleMethodName) {
		getInvokeCountDo(simpleMethodName).increCount();
	}
	
	public static void printData() {
		if (invokeCountDoMap.isEmpty())
			return;
		StringBuffer sb = new StringBuffer();
		sb.append("invokeCount print:\n");
		for (String key : invokeCountDoMap.keySet()) {
			InvokeCountDo countDo = invokeCountDoMap.get(key);
			if (countDo.getThisCount() > 0) {
				InvokeCountPrintDo printDo = new InvokeCountPrintDo(countDo);
				sb.append(printDo.toString()).append("\n");
			}
			invokeCountDoMap.get(key).updatePreRecCount();
		}
		log.info(sb.toString());
	}
}
