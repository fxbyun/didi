/**
 *
 */
package com.aicai.dao.mybatis.typehandler.java8time.testcase;

import static org.junit.Assert.assertTrue;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.aicai.dao.GenericDao;
import com.aicai.dao.mybatis.typehandler.java8time.domain.Java8Time;

@ContextConfiguration(locations = { "classpath:typehandler/java8time/mysql/spring-context.xml" })
public class Java8TimeInMysqlTest extends AbstractJUnit4SpringContextTests{

	@Autowired
	private GenericDao  genericDao;
	
	

	@Test
	public void test_insertAndReturnAffectedCount() throws Exception{
		Java8Time java8Time = new Java8Time();

		Instant instant = Instant.now();
		java8Time.setInstant(instant);
	
		LocalDateTime localDateTime = LocalDateTime.now();
		java8Time.setLocalDateTime(localDateTime);
		
		LocalDate localDate = LocalDate.now();
		java8Time.setLocalDate(localDate);
		
		LocalTime localTime = LocalTime.now();
		java8Time.setLocalTime(localTime);
		
		OffsetDateTime offsetDateTime = OffsetDateTime.now();
		java8Time.setOffsetDateTime(offsetDateTime);
		
		ZonedDateTime zonedDateTime = ZonedDateTime.now();
		java8Time.setZonedDateTime(zonedDateTime);
		
		
		int affectedCount = genericDao.insertAndReturnAffectedCount("Java8TimeDao.insertAndReturnAffectedCount", java8Time);
		Assert.assertEquals("affectedCount", 1, affectedCount);
	}
	
	@Test
	public void test_insertAndReturnAffectedCount2() throws Exception{
		Java8Time java8Time = new Java8Time();

		java8Time.setInstant(null);
		java8Time.setLocalDateTime(null);
		java8Time.setLocalDate(null);
		java8Time.setLocalTime(null);
		java8Time.setOffsetDateTime(null);
		java8Time.setZonedDateTime(null);
		
		int affectedCount = genericDao.insertAndReturnAffectedCount("Java8TimeDao.insertAndReturnAffectedCount", java8Time);
		Assert.assertEquals("affectedCount", 1, affectedCount);
	}
	
	@Test
	public void query() {
		
		Java8Time java8Time = genericDao.queryUnique("Java8TimeDao.selectByPrimaryKey", "10");
		
		assertTrue(java8Time != null);
	}
	
}
