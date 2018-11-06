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

/**
 * 支持Calendar在Oracle的date和MySQL的datetime间处理
 * @project aicai-dao
 * @author liuhui
 * @date 2011-12-7
 */
public class CalendarTypeHandler extends BaseTypeHandler<Calendar> {

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i,
			Calendar parameter, JdbcType jdbcType) throws SQLException {

		if (parameter != null) {
			ps.setTimestamp(i, new java.sql.Timestamp(parameter.getTimeInMillis()));
		} else {
			ps.setTimestamp(i, null);
		}
	}

	@Override
	public Calendar getNullableResult(ResultSet rs, String columnName)
			throws SQLException {
		Timestamp time = rs.getTimestamp(columnName);
		if(rs.wasNull()){
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
		if(cs.wasNull()){
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
		if(rs.wasNull()){
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
