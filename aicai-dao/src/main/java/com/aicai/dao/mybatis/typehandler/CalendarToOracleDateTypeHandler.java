/**
 *
 */
package com.aicai.dao.mybatis.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

@Deprecated
/**
 * deprecated是因为，type handler不但用于where，也用于普通用途。
 * 还是to_date()的方案能确保正确
 * 
 * 支持Calendar在Oracle的date字段类型的处理
 * 参考资料：
 * http://singleant.iteye.com/blog/1310395
 * http://databaseperformance.blogspot.com/2009/02/java-data-types-and-oracle-2.html
 * blogspot的，都要翻墙。
 * 但他最底下的评论有些参考价值：
 * 
 * milus said...
 *     Hi Roshan
 *     Not so long time ago i described a few ideas how to map java.util.Date to Oracle DATE on my blog.
 *     The problem is that my blog is in Polish:)
 *     To give you a few approaches:
 *     - use to_date()
 *     - use cast() remember about rounding!!!
 *     - using implicit type oracle.sql.DATE
 *     - using preparedStatement.setObject(1,new Timestamp(t.getD().getTime()) ,java.sql.Types.DATE ) - works only on 11g
 *     - using preparedStatement.setTime() as "undocumented oracle feature" - and it worked the best for me
 * 24 November 2009 13:28
 * 
 * @author zhoufeng
 * @date 2012-7-16
 */
public class CalendarToOracleDateTypeHandler extends BaseTypeHandler<Calendar> {

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i,
			Calendar parameter, JdbcType jdbcType) throws SQLException {

		if (parameter != null) {
			ps.setTimestamp(i, new Timestamp(parameter.getTimeInMillis()));
			//ps.setObject(i, new Timestamp(parameter.getTimeInMillis()), java.sql.Types.DATE);
			//ps.setDate(i, new Date(parameter.getTimeInMillis()));
			//ps.setObject(i, new oracle.sql.DATE(parameter.getTimeInMillis()), java.sql.Types.DATE);
			//ps.setTime(i, new Time(parameter.getTimeInMillis()));
			
			/*
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			ps.setObject(i, sdf.format(parameter.getTime()),java.sql.Types.DATE);*/
		} else {
			ps.setObject(i, null);
		}
	}

	@Override
	public Calendar getNullableResult(ResultSet rs, String columnName)
			throws SQLException {
		Timestamp time = rs.getTimestamp(columnName);
		if (rs.wasNull()) {
			return null;
		}
		if (time != null) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(time);
			return calendar;
		}
		return null;
	}

	@Override
	public Calendar getNullableResult(CallableStatement cs, int columnIndex)
			throws SQLException {
		Timestamp time = cs.getTimestamp(columnIndex);
		if (cs.wasNull()) {
			return null;
		}
		if (time != null) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(time);
			return calendar;
		}
		return null;
	}

	@Override
	public Calendar getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		Timestamp time = rs.getTimestamp(columnIndex);
		if (rs.wasNull()) {
			return null;
		}
		if (time != null) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(time);
			return calendar;
		}
		return null;
	}
}
