package com.aicai.dao.readwrite.service;

import java.util.Map;

import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.dao.readwrite.domain.Student;

/**
 * @author zhouguangming
 * 创建时间: 2014年4月4日 上午11:23:58
 */
public interface StudentWriteService {

	public abstract ModelResult<Integer> update(Map<String, Object> paramMap);

	/**
	 * @param student
	 * @return
	 */
	public abstract ModelResult<Integer> save(Student student);

	/**
	 * @param paramMap
	 * @return
	 */
	public abstract ModelResult<Integer> updateWithMultiSqlTransaction(Map<String, Object> paramMap);
}
