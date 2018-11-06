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
@ContextConfiguration(locations = { "classpath:spring-db-mysql-calendarevent.xml", "classpath:spring-dao-allevent.xml" })
public class CalendarToDateTimeTest extends AbstractJUnit4SpringContextTests{

	private final static String table_calendar_to_datetime = "calendar_to_datetime";
	private final static String table_calendar_to_datetime_autoid = "calendar_to_datetime_autoid";
	
	@Autowired
	private GenericDao  eventDao;
	
	private QueryRunner dbUnit;
	
	@Before
	public void before() {
		DataSource dataSource = (DataSource)applicationContext.getBean("dataSourceTest");
		dbUnit = new QueryRunner(dataSource);
	}
	
	private void prepareEnv(long id) throws Exception {
		String sql = "delete from calendar_to_datetime where id = ? ";
		dbUnit.update(sql, id);
	}
	
	private void prepareEnv_delAll() throws Exception {
		String sql = "delete from calendar_to_datetime_autoid";
		dbUnit.update(sql);
	}
	
	private void clearEnv(long id, String tableName) throws Exception {
		String sql = "delete from " + tableName + " where id = ?";
		dbUnit.update(sql, id);	
	}

	@Test
	public void test_insertAndReturnAffectedCount() throws Exception{
		prepareEnv(100);
		
		CalendarEvent event = method共用_buildEvent();
		event.setId(100);
		
		int affectedCount = eventDao.insertAndReturnAffectedCount("CalendarEventDao.insertAndReturnAffectedCount", event);
		Assert.assertEquals("affectedCount", 1, affectedCount);
		
		CalendarEvent dbEvent = eventDao.queryOne("CalendarEventDao.selectByPrimaryKey", 100);
		assert共用_比较丢失毫秒数后的calendar("createTime", event.getCreateTime(), dbEvent.getCreateTime());
		assert共用_比较丢失毫秒数后的calendar("updateTime", event.getUpdateTime(), dbEvent.getUpdateTime());
		
		clearEnv(100, table_calendar_to_datetime);
	}

	@Test
	public void test_insertAndSetupId() throws Exception{
		prepareEnv_delAll();
		
		CalendarEvent event = method共用_buildEvent();
		
		int affectedCount = eventDao.insertAndSetupId("CalendarEventDao.insertAndSetupId", event);
		Assert.assertEquals("affectedCount", 1, affectedCount);
		Assert.assertTrue("should return id", event.getId() != 100);

		CalendarEvent dbEvent = eventDao.queryOne("CalendarEventDao.selectByPrimaryKey_table2", event.getId());
		assert共用_比较丢失毫秒数后的calendar("createTime", event.getCreateTime(), dbEvent.getCreateTime());
		assert共用_比较丢失毫秒数后的calendar("updateTime", event.getUpdateTime(), dbEvent.getUpdateTime());

		clearEnv(event.getId(), table_calendar_to_datetime_autoid);
	}
	
	@Test
	public void test_updateByMap() throws Exception{
		prepareEnv_delAll();
		
		CalendarEvent event = method共用_buildEvent();
		eventDao.insertAndSetupId("CalendarEventDao.insertAndSetupId", event);

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", event.getId());
		event.setUpdateTime(Calendar.getInstance());
		paramMap.put("updateTime", event.getUpdateTime());
		
		int affectedCount = eventDao.update("CalendarEventDao.updateByMap", paramMap);
		Assert.assertEquals("affectedCount", 1, affectedCount);
		
		CalendarEvent dbEvent = eventDao.queryOne("CalendarEventDao.selectByPrimaryKey_table2", event.getId());
		assert共用_比较丢失毫秒数后的calendar("updateTime", event.getUpdateTime(), dbEvent.getUpdateTime());
		
		clearEnv(dbEvent.getId(), table_calendar_to_datetime_autoid);
	}

	@Test
	public void test_updateByObj() throws Exception{
		prepareEnv_delAll();
		
		CalendarEvent event = method共用_buildEvent();
		eventDao.insertAndSetupId("CalendarEventDao.insertAndSetupId", event);
		
		int affectedCount = eventDao.updateByObj("CalendarEventDao.updateByObj", event);
		Assert.assertEquals("affectedCount", 1, affectedCount);
		
		CalendarEvent dbEvent = eventDao.queryOne("CalendarEventDao.selectByPrimaryKey_table2", event.getId());
		assert共用_比较丢失毫秒数后的calendar("updateTime", event.getUpdateTime(), dbEvent.getUpdateTime());
		
		clearEnv(dbEvent.getId(), table_calendar_to_datetime_autoid);
	}
	
	@Test
	public void test_updateByMapMix() throws Exception{
		prepareEnv_delAll();
		
		CalendarEvent event = method共用_buildEvent();
		eventDao.insertAndSetupId("CalendarEventDao.insertAndSetupId", event);

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("event", event);
		Calendar now = Calendar.getInstance();
		paramMap.put("updateTime", now);

		
		int affectedCount = eventDao.update("CalendarEventDao.updateByMapMix", paramMap);
		Assert.assertEquals("affectedCount", 1, affectedCount);
		
		CalendarEvent dbEvent = eventDao.queryOne("CalendarEventDao.selectByPrimaryKey_table2", event.getId());

		event.setUpdateTime(now);
		assert共用_比较丢失毫秒数后的calendar("updateTime", event.getUpdateTime(), dbEvent.getUpdateTime());
		
		clearEnv(dbEvent.getId(), table_calendar_to_datetime_autoid);
	}

	private CalendarEvent method共用_buildEvent(){
		Calendar time = Calendar.getInstance();
		time.set(Calendar.YEAR, 2001);
		Random rand = new Random(System.currentTimeMillis());
		time.set(Calendar.DAY_OF_MONTH, rand.nextInt(25) + 1);

		CalendarEvent event = new CalendarEvent();
		event.setId(100);
		event.setCreateTime(time);
		event.setUpdateTime(time);
		
		return event;
	}
	
	private void assert共用_比较丢失毫秒数后的calendar(String field, Calendar expect, Calendar actual){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Assert.assertEquals(field, format.format(expect.getTime()), format.format(actual.getTime()));
	}
}
