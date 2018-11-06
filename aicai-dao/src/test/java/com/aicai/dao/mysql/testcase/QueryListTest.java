/**
 *
 */
package com.aicai.dao.mysql.testcase;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.aicai.appmodel.page.DataPage;
import com.aicai.dao.GenericDao;
import com.aicai.dao.example.domain.Event;
import com.aicai.unit.util.CsvUtil;
import com.aicai.unit.util.PathUtil;

/**
 * 
 * @author liuhui
 * @date 2011-12-1
 */
@ContextConfiguration(locations = { "classpath:spring-db-mysql-event.xml", "classpath:spring-dao.xml" })
public class QueryListTest extends AbstractJUnit4SpringContextTests {

	private final static String csvDir = PathUtil.getResourcePath("/spring-db-mysql-event.xml") + "/csv/query_list";

	@Autowired
	private GenericDao  eventDao;

	@Autowired
	private DataSource dataSource;

	@Test
	public void test_queryAll() throws Exception {
		List<Event> eventList = eventDao.queryList("EventDao.selectAll");
		Assert.assertTrue("size", eventList.size() == 26);
	}

	@Test
	public void test_queryCount() throws Exception {
		CsvUtil.updateCsvToDb(dataSource, csvDir);
		
		Calendar createTime = Calendar.getInstance();
		createTime.set(Calendar.YEAR, 2001);
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("createTime", createTime);
		paramMap.put("status", 1);

		int count = eventDao.queryCount("EventDao.selectCountByStatus", paramMap);
		Assert.assertEquals("size", 26, count);
	}
	
	@Test
	public void test_queryPage() throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		Calendar createTime = Calendar.getInstance();
		createTime.set(Calendar.YEAR, 2001);
		paramMap.put("createTime", createTime);
		paramMap.put("status", 1);
		DataPage<Event> page = new DataPage<Event>(2, 4);

		List<Event> eventList = eventDao.queryList("EventDao.selectPageByStatus", paramMap, page);
		
		int i = 5;
		for(Event event : eventList){
			System.out.print(event.getId() + ", ");
			Assert.assertEquals("id", i++, event.getId());
		}
		System.out.println();
	}
	
	@Test
	public void test_queryIdIn() throws Exception {
		CsvUtil.updateCsvToDb(dataSource, csvDir);
		long[] idList = new long[]{1, 3};
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("idList", idList);

		List<Event> eventList = eventDao.queryList("EventDao.selectByIdList", paramMap);
		Assert.assertEquals("eventList.size", 2, eventList.size());
	}
}
