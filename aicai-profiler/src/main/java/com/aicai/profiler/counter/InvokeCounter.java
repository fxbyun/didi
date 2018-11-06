package com.aicai.profiler.counter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
/**
 * 调用计数器打印
 * @author kerong.zhou
 */
public class InvokeCounter {

	private static ExecutorService counterPoolExecutor = Executors.newFixedThreadPool(2);// 至少两个线程；打印器，计数器
	
	private static AtomicBoolean printerInit = new AtomicBoolean(false);

	public static void count(String methodName, String beanNameRegex) {
		InvokeCountTask run = new InvokeCountTask(methodName, beanNameRegex);
		counterPoolExecutor.execute(run);
	}

	public static void initPrint(long printInterval) {
		if (printerInit.compareAndSet(false, true)) {
			InvokeCountPrintTask task = new InvokeCountPrintTask(printInterval);
			counterPoolExecutor.execute(task);
		}
	}

	public static void destoryThreadPool() {
		if (counterPoolExecutor.isShutdown())
			return;
		counterPoolExecutor.shutdownNow();
	}
}
