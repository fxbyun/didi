package com.aicai.dao.readwrite.testcase;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.aicai.appmodel.domain.result.ModelResult;
import com.aicai.dao.readwrite.domain.Student;
import com.aicai.dao.readwrite.service.StudentReadService;
import com.aicai.dao.readwrite.service.StudentWriteService;

/**
 * 读写分离相关集成测试
 * 
 * @author zhouguangming
 * 创建时间: 2014年4月3日 上午9:37:23
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:readwrite/spring-readwrite-test.xml" })
public class ReadWriteTest {
	@Autowired
	@Qualifier("studentReadService")
	private StudentReadService studentReadService;
	
	@Autowired
	@Qualifier("studentWriteService")
	private StudentWriteService studentWriteService;

	@Test
	public void queryFromMasterOrSlave() {
		this.insertOneStudent();

		ModelResult<List<Student>> resultFromSlaveOrMaster1 = studentReadService.queryList();
		assertTrue(resultFromSlaveOrMaster1.getModel().size() >= 0);

		ModelResult<List<Student>> resultFormMaster = studentReadService.queryListWithRealtime();
		assertTrue(resultFormMaster.getModel().size() >= 1);

		ModelResult<List<Student>> resultFromSlaveOrMaster2= studentReadService.queryList();
		assertTrue(resultFromSlaveOrMaster2.getModel().size() >= 0);
	}
	
	@Test
	public void query() {
		this.insertOneStudent();

		ModelResult<List<Student>> resultFromSlaveOrMaster1 = studentReadService.queryListWithRealtimeFalse();
		assertTrue(resultFromSlaveOrMaster1.getModel().size() >= 0);

		ModelResult<List<Student>> resultFormMaster = studentReadService.queryListWithRealtime();
		assertTrue(resultFormMaster.getModel().size() >= 1);

		ModelResult<List<Student>> resultFromSlaveOrMaster2 = studentReadService.queryListWithRealtimeFalse();
		assertTrue(resultFromSlaveOrMaster2.getModel().size() >= 0);
	}

	@Test
	public void singleSqlTransaction() {
		this.insertOneStudent();

		final Map<String, Object> paramMap = new HashMap<String, Object>(2);
		paramMap.put("name", "zyr");
		paramMap.put("number", "1");
		
		ModelResult<Integer> result = studentWriteService.update(paramMap);
		assertTrue(result != null);
		assertTrue(result.isSuccess());
		assertTrue(result.getModel().intValue() >= 1);
	}
	
	@Test
    public void multiSqlTransaction() {
		this.insertOneStudent();
		
        final Map<String, Object> paramMap = new HashMap<String, Object>(2);
        paramMap.put("name", "xxxxxx");
        paramMap.put("number", "1");
        
        ModelResult<Integer> result = studentWriteService.updateWithMultiSqlTransaction(paramMap);
		assertTrue(result != null);
		assertTrue(result.isSuccess());
		assertTrue(result.getModel().intValue() >= 1);
    }
	
	@Test(expected = Exception.class)
	public void throwException() throws Exception {
		studentReadService.throwException();
	}

	private void insertOneStudent() {
		Student student = new Student();
		student.setName("李四");
		student.setNumber(2);
		studentWriteService.save(student);
	}
}
