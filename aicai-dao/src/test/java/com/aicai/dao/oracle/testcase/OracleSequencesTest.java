/**
 *
 */
package com.aicai.dao.oracle.testcase;

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
 * @project aicai-dao
 * @author liuhui
 * @date 2011-12-1
 */
@ContextConfiguration(locations = { "classpath:spring-db-oracle-gameissue-seq.xml", "classpath:spring-dao-oracle-gameissue.xml" })
public class OracleSequencesTest extends AbstractJUnit4SpringContextTests {

	@Autowired
	private GenericDao issueDao;
	@SuppressWarnings("unused")
	private QueryRunner dbUnit;
	
	@Before
	public void before() throws Exception {
		DataSource dataSource = (DataSource)applicationContext.getBean("dataSourceOracleBetForUnit");
		dbUnit = new QueryRunner(dataSource);
	}

	@Test
    public void test_generateSequence() throws Exception{
		long id1 = issueDao.generateSequence("GameIssueDao.sequences");
		long id2 = issueDao.generateSequence("GameIssueDao.sequences");
		long difference = id2 - id1;
		
		Assert.assertEquals("difference", 1, difference);
	} 


}
