package com.aicai.profiler.manager.support;

import com.aicai.profiler.dao.TestDAO;
import com.aicai.profiler.manager.TestManager;


public class TestManagerImpl implements TestManager {

	private TestDAO testDAO; 

	@Override
	public String managerMethod() {
		testDAO.daoMethod();
//		try {
//			Thread.sleep(200);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		return "2";
	}

	public TestDAO getTestDAO() {
		return testDAO;
	}

	public void setTestDAO(TestDAO testDAO) {
		this.testDAO = testDAO;
	}
	
}
