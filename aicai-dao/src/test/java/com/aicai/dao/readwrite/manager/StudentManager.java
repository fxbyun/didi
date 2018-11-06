package com.aicai.dao.readwrite.manager;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.dao.readwrite.ReadWriteDao;
import com.aicai.dao.readwrite.domain.Student;

/**
 * @author zhouguangming 
 * 创建时间: 2014年4月3日 上午9:23:46
 */
public class StudentManager {
	@Autowired
	@Qualifier("readWriteDao")
	private ReadWriteDao studentDao;

	@Autowired
	@Qualifier("transactionTemplate")
	protected TransactionTemplate transactionTemplate;

	public ModelResult<List<Student>> queryList() {
		List<Student> studentList = studentDao.queryList("StudentDao.queryStudent");
		ModelResult<List<Student>> result = new ModelResult<>();
		result.setModel(studentList);
		
		return result;
	}

	public ModelResult<Integer> update(Map<String, Object> paramMap) {
		int affectedCount = studentDao.update("StudentDao.updateStudent", paramMap);
		ModelResult<Integer> result = new ModelResult<>();
		result.setModel(affectedCount);

		return result;
	}
	
	public Student queryStudent(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	public ModelResult<Integer> save(Student student) {
		int affectedCount = studentDao.insertAndReturnAffectedCount("StudentDao.insertNewStudent", student);
		ModelResult<Integer> result = new ModelResult<>();
		result.setModel(affectedCount);

		return result;
	}

	public ModelResult<Integer> updateWithMultiSqlTransaction(final Map<String, Object> paramMap) {
		Integer affectedCount = transactionTemplate.execute(new TransactionCallback<Integer>() {

			@Override
			public Integer doInTransaction(TransactionStatus status) {
				studentDao.queryList("StudentDao.queryStudent");
				int affectCount = studentDao.update("StudentDao.updateStudent", paramMap);
				
				return affectCount;
			}
		});
		
		ModelResult<Integer> result = new ModelResult<>();
		result.setModel(affectedCount);
		
		return result;
	}
}
