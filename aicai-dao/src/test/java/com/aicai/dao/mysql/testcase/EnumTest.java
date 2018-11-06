package com.aicai.dao.mysql.testcase;

import static com.aicai.dao.example.domain.Game.FC_SSQ;

import java.util.Calendar;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.aicai.dao.GenericDao;
import com.aicai.dao.example.domain.GameIssue;

@ContextConfiguration(locations = { "classpath:spring-db-mysql.xml", "classpath:spring-dao-enum.xml" })
public class EnumTest extends AbstractJUnit4SpringContextTests {
	@Autowired
	private GenericDao gameIssueDao;
	private QueryRunner dbUnit;
	private final static String table_game_issue = "game_issue";
	

	/**
	 * 需要加上dbunit清理现场和assert的代码
	 * @throws Exception 
	 */
	@Before
	public void before() {
		DataSource dataSource = (DataSource)applicationContext.getBean("dataSourceTest");
		dbUnit = new QueryRunner(dataSource);
	}
	private void prepareEnv(long id) throws Exception {
		String sql = "delete from game_issue where id = ? ";
		dbUnit.update(sql, id);
	}
	private void clearEnv(long id, String tableName) throws Exception {
		String sql = "delete from " + tableName + " where id = ?";
		dbUnit.update(sql, id);	
	}
	@Test
	public void test_insert() throws Exception {
		prepareEnv(103);
		Calendar time = Calendar.getInstance();
		time.set(Calendar.YEAR, 2000);
		
		GameIssue issue = new GameIssue();
		issue.setId(103);
		issue.setIssueNo("10085");
		issue.setGame(FC_SSQ);
		issue.setStartTime(time);
		issue.setEndTime(time);
		issue.setCurrent(false);
		int affectedCount = gameIssueDao.insertAndReturnAffectedCount("GameIssueDao.insert", issue);
		Assert.assertEquals("affectedCount", 1, affectedCount);
		clearEnv(103, table_game_issue);
	}
	
}
