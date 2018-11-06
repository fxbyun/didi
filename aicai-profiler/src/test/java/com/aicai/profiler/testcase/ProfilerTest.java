package com.aicai.profiler.testcase;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Field;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.aicai.profiler.Profiler;
import com.aicai.profiler.core.ExecData;
import com.aicai.profiler.core.ExecNode;
import com.aicai.profiler.core.LogManager;
import com.aicai.profiler.core.LogManagerImpl;
import com.aicai.profiler.domain.User;
import com.aicai.profiler.service.DifferentNameBean;
import com.aicai.profiler.service.TestAnnotationOnMethodService;
import com.aicai.profiler.service.TestChangePrintLevelOnClazzService;
import com.aicai.profiler.service.TestService;
import com.aicai.profiler.service.TestSkipAnnotationOnClazzService;

public class ProfilerTest{

	private ClassPathXmlApplicationContext context = null;
	
	@Before
	public void before() {
		context = new ClassPathXmlApplicationContext("spring-*.xml");
		
	}

	//测试采集数据
	@Test
	public void testGatherLogData() {
		Profiler profiler = new Profiler();
		LogManager mockLonLogManager = new MockLogManager();
		LogManager logManager = null;
		ExecNode root = null;
		try {
			Field field = Profiler.class.getDeclaredField("logManager");
			field.setAccessible(true);
			field.set(null, mockLonLogManager);
			
			Profiler.start("test", 500);
			Thread.sleep(600);
			Profiler.stop("test");
			
			Field field2 = Profiler.class.getDeclaredField("logManager");
			field2.setAccessible(true);
			logManager = (LogManager)field2.get(profiler);
			
			Field fieldExecNode =logManager.getClass().getDeclaredField("root");
			fieldExecNode.setAccessible(true);
			root = (ExecNode)fieldExecNode.get(logManager);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		assertTrue("采集时间错误",(root.getExecTime() >= 590 && root.getExecTime() <= 610));
	}
	
	//测试打印数据
	@Test
	public void testShowLogDate() {
		ExecData exceData = new ExecData(); 
		ExecNode execNode = new ExecNode("test",1326423741984L,500);
		execNode.setEndTime(1326423742400L);
		exceData.root = execNode;
		
		ExecNode execNode2 = new ExecNode("test2",1326423742500L,500);
		execNode2.setEndTime(1326423742600L);
		execNode2.setParent(execNode);
		execNode.getChildList().add(execNode2);

		LogManager logManager = new LogManagerImpl();
		String showLog = logManager.showTree(exceData.root);
		String startTime = showLog.split("\n")[0];
		String log1 = showLog.split("\n")[1];
		String log2 = showLog.split("\n")[2];
		assertEquals("打印开始时间错误","2012-01-13 11:02:21",startTime);
		assertEquals("打印日志错误","*[416, 316, 100%, 0]test",log1);
		assertEquals("打印日志错误","+---[100, 100, 24%, 516]test2",log2);
	}
	
	//总体测试
	@Test
	public void testProfile() {
		TestService service = (TestService) context
				.getBean("testService");
		service.perform();
		
		//测试打印参数
		//方法注解 
		TestAnnotationOnMethodService testAnnotationOnMethodService = (TestAnnotationOnMethodService) context
				.getBean("testAnnotationOnMethodService");
		testAnnotationOnMethodService.test_测试在方法上注解_日志级别为DEBUG_跳过第一个参数("1", "2", 3);
		testAnnotationOnMethodService.test_测试在方法上注解_日志级别为WARN_跳过第二个参数("1", "2", 3);
		testAnnotationOnMethodService.test_测试方法上注解_日志级别为ERROR_跳过最后一个参数("1", "2", 3);
		//类注解
		TestChangePrintLevelOnClazzService testChangePrintLevelOnClazzService = (TestChangePrintLevelOnClazzService) context
				.getBean("testChangePrintLevelOnClazzService");
		testChangePrintLevelOnClazzService.test_类注解_日志级别DEBUG();
		testChangePrintLevelOnClazzService.test_方法注解覆盖类注解_输出ERROR();
		User user=new User();
		user.setAge(26);
		user.setName("郑伟");
		testChangePrintLevelOnClazzService.test_类注解_自定义对象_日志级别DEBUG(user);
		//无注解
		TestSkipAnnotationOnClazzService testSkipAnnotationOnClazzService = (TestSkipAnnotationOnClazzService) context
				.getBean("testSkipAnnotationOnClazzService");
		
		testSkipAnnotationOnClazzService.test_通过Interceptor输出(user);
		testSkipAnnotationOnClazzService.test_通过Interceptor输出(null);
		testSkipAnnotationOnClazzService.test_通过Interceptor输出_跳过第一个参数(user,"123");
		testSkipAnnotationOnClazzService.test_随便你怎么弄都不会输出();
		testSkipAnnotationOnClazzService.test_通过精细化输出_日志级别变为ERROR();
		

		DifferentNameBean differentNameBean = (DifferentNameBean) context
				.getBean("differentNameBean");

		differentNameBean.test1();
		differentNameBean.test2();
		
	}

}
