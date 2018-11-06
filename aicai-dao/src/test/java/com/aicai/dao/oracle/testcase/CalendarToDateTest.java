/**
 *
 */
package com.aicai.dao.oracle.testcase;

import static com.aicai.dao.example.domain.Game.FC_SSQ;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.aicai.appmodel.page.DataPage;
import com.aicai.dao.GenericDao;
import com.aicai.dao.example.domain.Event;
import com.aicai.dao.example.domain.GameIssue;

/**
 * @project aicai-dao
 * @author liuhui
 * @date 2011-12-1
 */
@ContextConfiguration(locations = { "classpath:spring-db-oracle-gameissue.xml", "classpath:spring-dao-oracle-gameissue.xml" })
public class CalendarToDateTest extends AbstractJUnit4SpringContextTests {

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
		prepareEnv(106);
		GameIssue issue = new GameIssue();
		Calendar time = Calendar.getInstance();
		time.set(Calendar.YEAR, 2001);
		issue.setId(106);
		issue.setIssueNo("10118");
		issue.setGame(FC_SSQ);
		issue.setCurrent(false);
		issue.setStartTime(time);
		issue.setEndTime(time);
		issue.setOpenTime(time);
		issue.setStatus(4);
		int affectedCount = issueDao.insertAndReturnAffectedCount("GameIssueDao.insert", issue);
		Assert.assertEquals("affectedCount", 1, affectedCount);
		clearEnv(106, table_boolean_to_int);
	}

	@Test
	public void test_queryOne() throws Exception {
		GameIssue issue = new GameIssue();
		issue.setGame(FC_SSQ);
		issue.setId(100);
		
		GameIssue dbIssue = issueDao.queryOne("GameIssueDao.selectByPrimaryKey", issue.getId());
		Assert.assertEquals("game", issue.getGame(), dbIssue.getGame());
	}
	
	@Test
	public void test_queryPage(){
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("status", 7);
		
		Calendar time = Calendar.getInstance();
		time.set(Calendar.YEAR, 2001);
		paramMap.put("startTime", time);
		paramMap.put("gameId", 101);
		DataPage<GameIssue> page = new DataPage<GameIssue>(2, 7);
		
		List<GameIssue> dbIssueList = issueDao.queryList("GameIssueDao.selectListByStatus", paramMap, page);
		Assert.assertEquals("size", 4, dbIssueList.size());
		for(GameIssue dbIssue : dbIssueList){
			System.out.println(dbIssue.getId());
		}
	}
	
	@SuppressWarnings("unused")
	@Test
	public void test_queryIdIn(){
		long[] idList = new long[]{88342, 325860};
		
		List<GameIssue> dbIssue = issueDao.queryIdIn("GameIssueDao.selectByIdList", idList);
		
		idList = new long[]{88342};
		dbIssue = issueDao.queryIdIn("GameIssueDao.selectByIdList", idList);
	}

	@Test
	public void test_update() throws Exception {
		Calendar startTime = Calendar.getInstance();
		startTime.set(Calendar.YEAR, 2001);
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", 100);
		paramMap.put("startTime", startTime);
		
		int affectedCount = issueDao.update("GameIssueDao.updateByMap", paramMap);
		Assert.assertEquals("affectedCount", 1, affectedCount);
		
		GameIssue dbIssue = issueDao.queryOne("GameIssueDao.selectByPrimaryKey", 100);
		assert共用_比较丢失毫秒数后的calendar("update startTime", startTime, dbIssue.getStartTime());
	}

	private void assert共用_比较丢失毫秒数后的calendar(String field, Calendar expect, Calendar actual){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Assert.assertEquals(field, format.format(expect.getTime()), format.format(actual.getTime()));
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
