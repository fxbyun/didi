package com.aicai.profiler.service;

import com.aicai.profiler.connector.annotation.PrintDigest;
import com.aicai.profiler.connector.annotation.PrintLevel;
import com.aicai.profiler.manager.MyManager;
import com.aicai.profiler.manager.TestManager;

public class TestService {

	private TestManager testManager;
	private MyManager myManager;

	public void perform() {
		try {
			Thread.sleep(20);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		testManager.managerMethod();
		myManager.myManagerMethod();
	}

	public TestManager getTestManager() {
		return testManager;
	}

	public void setTestManager(TestManager testManager) {
		this.testManager = testManager;
	}

	public MyManager getMyManager() {
		return myManager;
	}

	public void setMyManager(MyManager myManager) {
		this.myManager = myManager;
	}
}
