package com.aicai.profiler.dao.support;

import com.aicai.profiler.dao.TestDAO;

public class TestDAOImpl implements TestDAO {

	@Override
	public String daoMethod() {
		try {
			Thread.sleep(150);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return "1";
	}

}
