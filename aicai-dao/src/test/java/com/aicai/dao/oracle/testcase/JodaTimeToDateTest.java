/**
 *
 */
package com.aicai.dao.oracle.testcase;

import static com.aicai.dao.example.domain.Game.FC_SSQ;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.aicai.dao.GenericDao;
import com.aicai.dao.example.domain.GameIssue;
import com.aicai.dao.example.domain.JodaGameIssue;

/**
 * @project aicai-dao
 * @author liuhui
 * @date 2011-12-1
 */
@ContextConfiguration(locations = { "classpath:spring-db-oracle-gameissue.xml", "classpath:spring-dao-oracle-gameissue.xml" })
public class JodaTimeToDateTest extends AbstractJUnit4SpringContextTests{

	@Autowired
	private GenericDao issueDao;
	private QueryRunner dbUnit;
	private final static String table_boolean_to_int = "bet_game_issue";
	
	@Before
	public void before() throws Exception {
		DataSource dataSource = (DataSource)applicationContext.getBean("dataSourceOracleBetForUnit");
		dbUnit = new QueryRunner(dataSource);
	}
	
	@Test
	public void test_insert() throws Exception {
		prepareEnv(107);
		JodaGameIssue jodaissue = new JodaGameIssue();
		DateTime time = new DateTime(2001, 2, 1, 1, 1, 1, 0);
		jodaissue.setId(107);
		jodaissue.setIssueNo("10119");
		jodaissue.setGame(FC_SSQ);
		jodaissue.setCurrent(false);
		jodaissue.setStartTime(time);
		jodaissue.setEndTime(time);
		jodaissue.setOpenTime(time);
		jodaissue.setStatus(4);
		int affectedCount = issueDao.insertAndReturnAffectedCount("GameIssueDao.insert", jodaissue);
		Assert.assertEquals("affectedCount", 1, affectedCount);
		clearEnv(107, table_boolean_to_int);
	}

	
	@Test
	public void test_queryOne() throws Exception {
		GameIssue issue = new GameIssue();
		issue.setGame(FC_SSQ);
		issue.setId(100);
		
		JodaGameIssue dbIssue = issueDao.queryOne("GameIssueDao.selectByPrimaryKey_joda", issue.getId());
		Assert.assertEquals("game", issue.getGame(), dbIssue.getGame());
	}

	@Test
	public void test_update() throws Exception {
		DateTime startTime = new DateTime(2001, 2, 1, 1, 1, 1, 0);
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", 100);
		paramMap.put("startTime", startTime);
		
		int affectedCount = issueDao.update("GameIssueDao.updateByMap", paramMap);
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
