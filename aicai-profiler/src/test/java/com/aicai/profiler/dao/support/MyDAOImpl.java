package com.aicai.profiler.dao.support;

import com.aicai.profiler.connector.annotation.PrintDigest;
import com.aicai.profiler.connector.annotation.PrintLevel;
import com.aicai.profiler.dao.MyDAO;


public class MyDAOImpl implements MyDAO {

	@Override
	@PrintDigest(printLevel=PrintLevel.DEBUG)
	public void myDAOMethod() {
		try {
			Thread.sleep(150);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}
