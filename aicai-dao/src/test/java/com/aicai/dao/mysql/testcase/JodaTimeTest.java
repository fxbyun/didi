/**
 *
 */
package com.aicai.dao.mysql.testcase;

import static com.aicai.dao.example.domain.Game.FC_SSQ;

import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.aicai.dao.GenericDao;
import com.aicai.dao.example.domain.JodaGameIssue;

/**
 * 
 * @author liuhui
 * @date 2011-12-1
 */
@ContextConfiguration(locations = { "classpath:spring-db-mysql-joda.xml", "classpath:spring-dao-allevent.xml" })
public class JodaTimeTest extends AbstractJUnit4SpringContextTests {

	@Autowired
	private GenericDao  issueDao;

	@Test
	public void test_insertAndReturnAffectedCount() throws Exception {
		JodaGameIssue issue = new JodaGameIssue();
		issue.setId(102);
		issue.setIssueNo("10085");
		issue.setGame(FC_SSQ);
		DateTime time = new DateTime(2001, 2, 1, 1, 1, 1, 0);
		issue.setStartTime(time);
		issue.setEndTime(time);
		issueDao.insertAndReturnAffectedCount("GameIssueDao.insert", issue);
	}


	@Test
	public void test_queryOne() throws Exception {
		JodaGameIssue jodaissue = new JodaGameIssue();
		jodaissue.setGame(FC_SSQ);
		jodaissue.setId(101);
		
		JodaGameIssue dbIssue = issueDao.queryOne("GameIssueDao.selectByPrimaryKey", jodaissue.getId());
		Assert.assertEquals("game", jodaissue.getGame(), dbIssue.getGame());
	}
	@Test
	public void test_update() throws Exception {
		DateTime endTime = new DateTime(2001, 2, 1, 1, 1, 1, 0);
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", 101);
		paramMap.put("endTime", endTime);
		
		int affectedCount = issueDao.update("GameIssueDao.updateByMap", paramMap);
		Assert.assertEquals("affectedCount", 1, affectedCount);
	}
}
