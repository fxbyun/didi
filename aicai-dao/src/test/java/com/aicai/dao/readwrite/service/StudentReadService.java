package com.aicai.dao.readwrite.service;

import java.util.List;

import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.dao.readwrite.domain.Student;

/**
 * @author zhouguangming
 * 创建时间: 2014年4月4日 上午11:17:49
 */
public interface StudentReadService {

	public abstract ModelResult<Student> queryStudentWithRealtime(long id);

	public abstract ModelResult<List<Student>> queryList();
	
	public abstract ModelResult<List<Student>> queryListWithRealtimeFalse();

	public abstract ModelResult<List<Student>> queryListWithRealtime();

	public abstract void throwException() throws Exception;
}