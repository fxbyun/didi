/**
 *
 */
package com.aicai.dao.mysql.testcase;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.aicai.dao.GenericDao;
import com.aicai.dao.example.domain.BoolEvent;

/**
 * @project aicai-dao
 * @author liuhui
 * @date 2011-12-1
 */
@ContextConfiguration(locations = { "classpath:spring-db-mysql-boolevent.xml", "classpath:spring-dao-allevent.xml" })
public class BooleanToIntTest extends AbstractJUnit4SpringContextTests {

	private final static String table_bool_to_int = "bool_to_int";

	@Autowired
	private GenericDao  eventDao;

	private QueryRunner dbUnit;
	private ResultSetHandler<IntEvent> queryOneHandler;

	@Before
	public void before() {
		DataSource dataSource = (DataSource) applicationContext.getBean("dataSourceTest");
		dbUnit = new QueryRunner(dataSource);
		queryOneHandler = new ResultSetHandler<IntEvent>() {
			public IntEvent handle(ResultSet rs) throws SQLException {
				IntEvent event = null;
				if (rs.next()) {
					event = new IntEvent();
					event.id = rs.getInt("id");
					event.event = rs.getInt("event");
				}
				return event;
			}
		};
	}

	@Test
	public void test_insertAndReturnAffectedCount() throws Exception {
		prepareEnv_delAll();

		BoolEvent event = new BoolEvent();
		event.setEvent(true);
		int affectedCount = eventDao.insertAndSetupId("BoolEventDao.insertAndSetupId", event);
		Assert.assertEquals("affectedCount", 1, affectedCount);
		IntEvent dbEvent = queyOneByDbUnit(event.getId());
		Assert.assertEquals("event should be true", 1, dbEvent.event);

		clearEnv(dbEvent.id);
	}

	@Test
	public void test_queryOne() throws Exception {
		prepareEnv_delAll();
		long idForFalse = insertAndReturnIdByDbUnit(0);
		long idForTrue = insertAndReturnIdByDbUnit(1);
		
		BoolEvent eventForFalse = eventDao.queryOne("BoolEventDao.selectByPrimaryKey", idForFalse);
		Assert.assertEquals("id", idForFalse, eventForFalse.getId());
		Assert.assertFalse("eventForFalse", eventForFalse.isEvent());
		
		BoolEvent eventForTrue = eventDao.queryOne("BoolEventDao.selectByPrimaryKey", idForTrue);
		Assert.assertEquals("id", idForTrue, eventForTrue.getId());
		Assert.assertTrue("eventForTrue", eventForTrue.isEvent());

		clearEnv(idForFalse);
		clearEnv(idForTrue);
	}

	private IntEvent queyOneByDbUnit(long id) throws Exception {
		return dbUnit.query("select * from bool_to_int where id = ?", queryOneHandler, id);
	}

	private long insertAndReturnIdByDbUnit(int event) throws Exception {
		Connection conn = dbUnit.getDataSource().getConnection();
		try {
			int affectedCount = dbUnit.update(conn, "insert bool_to_int(event) values(?)", event);
			Assert.assertEquals("insertByDbUnit", 1, affectedCount);

			long id = dbUnit.query(conn, "select last_insert_id()", new ResultSetHandler<Long>() {
				public Long handle(ResultSet rs) throws SQLException {
					rs.next();
					return rs.getLong(1);
				}
			});
			return id;
		} finally {
			conn.close();
		}
	}

	private void clearEnv(long id) throws Exception {
		String sql = "delete from bool_to_int where id = ?";
		dbUnit.update(sql, id);
	}

	private void prepareEnv_delAll() throws Exception {
		String sql = "delete from bool_to_int";
		dbUnit.update(sql);
	}

	private class IntEvent {
		public int id;
		public int event;
	}
}
