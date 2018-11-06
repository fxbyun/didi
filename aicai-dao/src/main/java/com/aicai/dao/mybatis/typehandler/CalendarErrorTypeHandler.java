/**
 *
 */
package com.aicai.dao.mybatis.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

@Deprecated
/**
 * 支持Calendar在Oracle的date字段类型的处理
 * deprecated因为同篇替换为setDate()/getDate()
 * @author zhoukerong
 * @date 2012-7-16
 */
public class CalendarErrorTypeHandler extends BaseTypeHandler<Calendar> {

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, Calendar parameter, JdbcType jdbcType)
			throws SQLException {
		if (parameter != null) {
			ps.setDate(i, new java.sql.Date(parameter.getTimeInMillis()));
		} else {
			ps.setDate(i, null);
		}
	}

	@Override
	public Calendar getNullableResult(ResultSet rs, String columnName) throws SQLException {
		java.sql.Date date = rs.getDate(columnName);
		if (rs.wasNull()) {
			return null;
		}
		if (date != null) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(convertSqlDate(date));
			return calendar;
		}
		return null;
	}

	@Override
	public Calendar getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		java.sql.Date date = cs.getDate(columnIndex);
		if (cs.wasNull()) {
			return null;
		}
		if (date != null) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(convertSqlDate(date));
			return calendar;
		}
		return null;
	}

	@Override
	public Calendar getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		java.sql.Date date = rs.getDate(columnIndex);
		if (rs.wasNull()) {
			return null;
		}
		if (date != null) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(convertSqlDate(date));
			return calendar;
		}
		return null;
	}

	private java.util.Date convertSqlDate(java.sql.Date sqlDate) {
		java.util.Date dbDate = null;
		if (sqlDate != null) {
			dbDate = new java.util.Date();
			dbDate.setTime(sqlDate.getTime());
		}
		return dbDate;
	}
}
