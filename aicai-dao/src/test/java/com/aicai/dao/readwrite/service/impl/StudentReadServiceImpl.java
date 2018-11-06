package com.aicai.dao.readwrite.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.dao.readwrite.annotation.RealtimeForReadService;
import com.aicai.dao.readwrite.domain.Student;
import com.aicai.dao.readwrite.manager.StudentManager;
import com.aicai.dao.readwrite.service.StudentReadService;

/**
 * @author zhouguangming
 * 创建时间: 2014年4月3日 上午9:22:34
 */
public class StudentReadServiceImpl implements StudentReadService {

	@Autowired
	@Qualifier("studentManager")
	private StudentManager studentManager;
	
	@Override
	@RealtimeForReadService(isRealtime = true) //有标注 @RealtimeForReadService,且@RealtimeForReadService 的isRealtime的值为true,所以查询方法走主库
	public ModelResult<Student> queryStudentWithRealtime(long id) {
		Student student = studentManager.queryStudent(id);
		
		ModelResult<Student> result = new ModelResult<>();
		result.setModel(student);
		
		return result;
	}
	
	//没有标注 @RealtimeForReadService, 查询方法的行为同有标注 @RealtimeForReadService, 且@RealtimeForReadService 的isRealtime的值为false
	@Override
	public ModelResult<List<Student>> queryList() {
		return studentManager.queryList();
	}

	@Override
	@RealtimeForReadService(isRealtime = false)  //有标注 @RealtimeForReadService, 因@RealtimeForReadService 的isRealtimee的值为false， 所以查询方法的行为不一定，根据权重的算法可能走主库,也可能走从库
	public ModelResult<List<Student>> queryListWithRealtimeFalse() {
		return studentManager.queryList();
	}

	@Override
	@RealtimeForReadService(isRealtime = true)  //有标注 @RealtimeForReadService,且@RealtimeForReadService 的isRealtime的值为true,所以查询方法走主库
	public ModelResult<List<Student>> queryListWithRealtime() {
		return studentManager.queryList();
	}
	
	@Override
	@RealtimeForReadService(isRealtime = true) //有标注 @RealtimeForReadService,且@RealtimeForReadService 的isRealtime的值为true,所以查询方法走主库
	public void throwException() throws Exception {
		studentManager.queryList();
		throw new Exception("手动抛出异常！");
	}
}
