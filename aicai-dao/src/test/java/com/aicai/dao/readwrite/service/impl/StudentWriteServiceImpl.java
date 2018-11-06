package com.aicai.dao.readwrite.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.dao.readwrite.domain.Student;
import com.aicai.dao.readwrite.manager.StudentManager;
import com.aicai.dao.readwrite.service.StudentWriteService;

/**
 * @author zhouguangming
 * 创建时间: 2014年4月4日 上午11:26:53
 */
public class StudentWriteServiceImpl implements StudentWriteService {

	@Autowired
	@Qualifier("studentManager")
	private StudentManager studentManager;
	
	@Override
	public ModelResult<Integer> update(Map<String, Object> paramMap) {
		return studentManager.update(paramMap);
	}

	@Override
	public ModelResult<Integer> save(Student student) {
		return studentManager.save(student);
	}

	@Override
	public ModelResult<Integer> updateWithMultiSqlTransaction(Map<String, Object> paramMap) {
		return studentManager.updateWithMultiSqlTransaction(paramMap);
	}
}
