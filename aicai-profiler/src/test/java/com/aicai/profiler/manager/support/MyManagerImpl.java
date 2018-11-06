package com.aicai.profiler.manager.support;

import com.aicai.profiler.dao.MyDAO;
import com.aicai.profiler.manager.MyManager;

public class MyManagerImpl implements MyManager {

	private MyDAO dao; 
	
	@Override
	public void myManagerMethod() {
//		try {
//			Thread.sleep(100);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		dao.myDAOMethod();
		
	}
	public MyDAO getDao() {
		return dao;
	}

	public void setDao(MyDAO dao) {
		this.dao = dao;
	}
}
