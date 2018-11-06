/**
 *
 */
package com.aicai.dao.mybatis.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.joda.time.DateTime;

@Deprecated
/**
 * @Deprecated 请直接用 java8 time
 * @author liuhui
 * @date 2011-12-6
 */
public class JodaTypehandler extends BaseTypeHandler<DateTime> {

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i,
			DateTime parameter, JdbcType jdbcType) throws SQLException {
		if (parameter != null) {
			ps.setTimestamp(i, new Timestamp(parameter.getMillis()));
		}
		else {
			ps.setTimestamp(i, null);
		}
	}

	@Override
	public DateTime getNullableResult(ResultSet rs, String columnName)
			throws SQLException {
		Timestamp ts = rs.getTimestamp(columnName);
		if(rs.wasNull()){
			return null;
		}
		if (ts != null) {
			return new DateTime(ts.getTime());
		}
		else {
			return null;
		}
	}

	@Override
	public DateTime getNullableResult(CallableStatement cs, int columnIndex)
			throws SQLException {
		Timestamp ts = cs.getTimestamp(columnIndex);
		if(cs.wasNull()){
			return null;
		}
		if (ts != null) {
			return new DateTime(ts.getTime());
		}
		else {
			return null;
		}
	}

	@Override
	public DateTime getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		Timestamp ts = rs.getTimestamp(columnIndex);
		if(rs.wasNull()){
			return null;
		}
		if (ts != null) {
			return new DateTime(ts.getTime());
		}
		else {
			return null;
		}
	}
}
