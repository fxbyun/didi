package com.aicai.profiler.counter;

public class InvokeCountPrintTask implements Runnable{
	
	private long sleepTime = 60 * 1000;
	
	public InvokeCountPrintTask(long sleepTime){
		this.sleepTime = sleepTime;
	}
	
	public InvokeCountPrintTask(){
		
	}

	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				InvokeCounter.destoryThreadPool();
				break;
			}
			InvokeCountData.printData();
		}
	}

}
