/**
 *
 */
package com.aicai.dao.oracle.testcase;

import static com.aicai.dao.example.domain.Game.FC_SSQ;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

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

/**
 * @author liuhui
 * @date 2011-12-1
 */
@ContextConfiguration(locations = { "classpath:spring-db-oracle-boolevent.xml", "classpath:spring-dao-oracle-boolean.xml" })
public class BooleanToIntTest extends AbstractJUnit4SpringContextTests {

	@Autowired
	private GenericDao gameIssueDao;
	private QueryRunner dbUnit;
	private final static String table_boolean_to_int = "bet_game_issue";
	
	@Before
	public void before() {
		DataSource dataSource = (DataSource) applicationContext.getBean("dataSourceTest");
		dbUnit = new QueryRunner(dataSource);
		
	}

	@Test
	public void test_insertAndReturnAffectedCount() throws Exception {
		prepareEnv(103);
		GameIssue issue = new GameIssue();
		Calendar startTime = Calendar.getInstance();
		startTime.set(Calendar.YEAR, 2001);
		
		issue.setId(103);
		issue.setIssueNo("10115");
		issue.setGame(FC_SSQ);
		issue.setCurrent(false);
		issue.setStartTime(startTime);
		issue.setEndTime(startTime);
		issue.setOpenTime(startTime);
		issue.setStatus(4);
		int affectedCount = gameIssueDao.insertAndReturnAffectedCount("GameIssueDao.insert", issue);
		Assert.assertEquals("affectedCount", 1, affectedCount);
		clearEnv(103,table_boolean_to_int);
	}

	@Test
	public void test_根据数字id查询单条记录() throws SQLException {
		GameIssue issue = new GameIssue();
		issue.setGame(FC_SSQ);
		issue.setId(100);
		
		GameIssue dbIssue = gameIssueDao.queryOne("GameIssueDao.selectByPrimaryKey", issue.getId());
		Assert.assertEquals("game", issue.getGame(), dbIssue.getGame());
	}

	@Test
	public void test_测试Calendar的update() throws Exception {
		GameIssue issue = new GameIssue();
		Calendar startTime = Calendar.getInstance();
		startTime.set(Calendar.YEAR, 2001);

		issue.setStartTime(startTime);
		issue.setEndTime(startTime);
		issue.setOpenTime(startTime);
		issue.setId(100);
		issue.setCurrent(false);
		Map<String, Object> param = new HashMap<String, Object>();
	
		param.put("id", issue.getId());
        param.put("isCurrent", false);		
		param.put("startTime", "2001-2-1 11:10:13");
		param.put("endTime", "2001-2-1 11:10:13");
		param.put("openTime", "2001-2-1 11:10:13");

		int affectedCount = gameIssueDao.update("GameIssueDao.update", param);
		Assert.assertEquals("affectedCount", 1, affectedCount);
		
	}
	private void prepareEnv(long id) throws Exception {
		String sql = "delete from  bet_game_issue where id = ? ";
		dbUnit.update(sql, id);
	}
	
	private void clearEnv(long id, String tableName) throws Exception {
		String sql = "delete from " + tableName + " where id = ?";
		dbUnit.update(sql, id);	
	}
}
