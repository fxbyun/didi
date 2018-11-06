package com.aicai.dao.oracle.testcase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.apache.commons.dbutils.QueryRunner;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.aicai.dao.GenericDao;
import com.aicai.dao.example.domain.Game;
import com.aicai.dao.example.domain.GameIssue;
import com.aicai.unit.csv.TestDataManager;
import com.aicai.unit.csv.domain.TestDataContext;

public class CalendarSetTimeTest {

	private TestDataManager dataManager = new TestDataManager();

	@Test
	public void test_CalendarToOracleDateTypeHandler() throws Exception {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(
				"classpath:/spring-db-oracle-gameissue.xml",
				"classpath:/spring-dao-oracle-gameissue.xml");
		List<GameIssue> issueList = assert共用_TypeHandler用于where条件及查询结果(ctx);
		Assert.assertEquals("2001-2-1 10:11:23", 101, issueList.get(0).getId());
		Assert.assertEquals("2001-2-3 22:32:20", 102, issueList.get(1).getId());
	}
	
	private ApplicationContext load正确TypeHandler的context(){
		return new ClassPathXmlApplicationContext(
				"classpath:/spring-db-oracle-setDate.xml",
				"classpath:/spring-dao-oracle-gameissue.xml");
	}
	
	private ApplicationContext load错误TypeHandler的context(){
		return new ClassPathXmlApplicationContext(
				"classpath:/spring-db-oracle-gameissue.xml",
				"classpath:/spring-dao-oracle-gameissue.xml");
	}
	
	@Ignore
	@Test
	public void test_CalendarErrorTypeHandler() throws Exception {
		ApplicationContext ctx = load正确TypeHandler的context();
		assert共用_TypeHandler用于where条件及查询结果(ctx);
	}

	@Ignore
	@Test
	public void test_CalendarTypeHandler() throws Exception {
		ApplicationContext ctx = load错误TypeHandler的context();
		assert共用_TypeHandler用于where条件及查询结果(ctx);
	}
	
	@Ignore
	@Test
	public void test_TypeHandler用于Insert(){
		ApplicationContext ctx = load正确TypeHandler的context();
		GenericDao issueDao = ctx.getBean("gameIssueDao", GenericDao.class);
		GameIssue issue = new GameIssue();
		issue.setId(101);
		issue.setCurrent(false);
		issue.setGame(Game.FC_SSQ);
		issue.setIssueNo("101");
		issue.setPreIssueId(100);
		issue.setStatus(0);
		Calendar time = Calendar.getInstance();
		issue.setStartTime(time);
		issue.setEndTime(time);
		issue.setOpenTime(time);
		int affectedCount = issueDao.insertAndReturnAffectedCount("GameIssueDao.insert", issue);
		Assert.assertEquals("insert不超过", 1, affectedCount);
	}
	
	@Ignore
	@Test
	public void test_TypeHandler用于update() throws Exception{
		ApplicationContext ctx = load正确TypeHandler的context();
		GenericDao issueDao = ctx.getBean("gameIssueDao", GenericDao.class);
		TestDataContext dataContext = dataManager.loadDataContext("/csv/testData-gameissue.json");
		dataContext.dataBeforeTest = dataManager.loadCsv(dataContext.csvPathBeforeTest, dataContext, true);
		
	}

	private List<GameIssue> assert共用_TypeHandler用于where条件及查询结果(ApplicationContext ctx) throws Exception {
		GenericDao issueDao = ctx.getBean("gameIssueDao", GenericDao.class);
		QueryRunner dbUtil = ctx.getBean("dbUtil", QueryRunner.class);

		TestDataContext dataContext = dataManager.loadDataContext("/csv/testData-gameissue.json");
		dataContext.dataBeforeTest = dataManager.loadCsv(dataContext.csvPathBeforeTest, dataContext, true);
		dataManager.putDataToDb(dbUtil, dataContext);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("queryStartTime", "2001-2-1 10:11:23");
		map.put("queryEndTime", "2001-2-3 22:32:44");
		List<GameIssue> issueList = issueDao.queryList("GameIssueDao.selectListByStartTime", map);
		Assert.assertEquals("where条数", 2, issueList.size());
		return issueList;
	}
	
	private String calendarToString(Calendar time){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(time.getTime());
	}
}
