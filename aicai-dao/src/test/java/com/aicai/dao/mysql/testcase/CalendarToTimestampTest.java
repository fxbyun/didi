/**
 *
 */
package com.aicai.dao.mysql.testcase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.aicai.dao.GenericDao;
import com.aicai.dao.example.domain.CalendarEvent;

/**
 * @project aicai-dao
 * @author liuhui
 * @date 2011-12-1
 */
@SuppressWarnings("unused")
@ContextConfiguration(locations = { "classpath:spring-db-mysql-timestamp.xml", "classpath:spring-dao-enum.xml" })
public class CalendarToTimestampTest extends AbstractJUnit4SpringContextTests{

	private final static String table_calendar_to_timestamp = "calendar_to_timestamp";
	
	@Autowired
	private GenericDao  issueDao;
	private QueryRunner dbUnit;
	
	@Before
	public void before() {
		DataSource dataSource = (DataSource)applicationContext.getBean("dataSourceTest");
		dbUnit = new QueryRunner(dataSource);
	}
	
	
	private void prepareEnv(long id) throws Exception {
		String sql = "delete from calendar_to_timestamp where id = ? ";
		dbUnit.update(sql, id);
	}
	
	private void prepareEnv_delAll() throws Exception {
		String sql = "delete from calendar_to_timestamp";
		dbUnit.update(sql);
	}
	
	private void clearEnv(long id, String tableName) throws Exception {
		String sql = "delete from " + tableName + " where id = ?";
		dbUnit.update(sql, id);	
	}
	
	@Test
	public void test_insertAndReturnAffectedCount() throws Exception{
		prepareEnv(101);
		
        CalendarEvent event = method共用_buildEvent();
        event.setId(101);
        
		int affectedCount = issueDao.insertAndReturnAffectedCount("CalendarToTimestampEventDao.insertAndReturnAffectedCount", event);
		Assert.assertEquals("affectedCount", 1, affectedCount);
		
		CalendarEvent dbEvent = issueDao.queryOne("CalendarToTimestampEventDao.selectByPrimaryKey", 101);
		assert共用_比较丢失毫秒数后的calendar("create_Time", event.getCreateTime(), dbEvent.getCreateTime());
		assert共用_比较丢失毫秒数后的calendar("updateTime", event.getUpdateTime(), dbEvent.getUpdateTime());
	
		clearEnv(101, table_calendar_to_timestamp);
	}
	
	@Test
	public void test_updateByMap() throws Exception{
		prepareEnv_delAll();
		
		CalendarEvent event = method共用_buildEvent();
		issueDao.insertAndSetupId("CalendarToTimestampEventDao.insertAndReturnAffectedCount", event);

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", event.getId());
		event.setUpdateTime(Calendar.getInstance());
		paramMap.put("updateTime", event.getUpdateTime());
		
		int affectedCount = issueDao.update("CalendarToTimestampEventDao.updateByMap", paramMap);
		Assert.assertEquals("affectedCount", 1, affectedCount);
		
		CalendarEvent dbEvent = issueDao.queryOne("CalendarToTimestampEventDao.selectByPrimaryKey", event.getId());
		assert共用_比较丢失毫秒数后的calendar("updateTime", event.getUpdateTime(), dbEvent.getUpdateTime());
		
		clearEnv(dbEvent.getId(), table_calendar_to_timestamp);
	}

	private CalendarEvent method共用_buildEvent(){
		Calendar time = Calendar.getInstance();
		time.set(Calendar.YEAR, 2000);
		Random rand = new Random(System.currentTimeMillis());
		time.set(Calendar.DAY_OF_MONTH, rand.nextInt(25) + 1);

		CalendarEvent event = new CalendarEvent();
		event.setId(101);
		event.setCreateTime(time);
		event.setUpdateTime(time);
		return event;
	}
	
	private void assert共用_比较丢失毫秒数后的calendar(String field, Calendar expect, Calendar actual){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Assert.assertEquals(field, format.format(expect.getTime()), format.format(actual.getTime()));
	}
}
