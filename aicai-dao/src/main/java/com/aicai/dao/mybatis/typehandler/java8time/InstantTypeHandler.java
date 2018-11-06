/**
 * 
 */
package com.aicai.dao.mybatis.typehandler.java8time;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

@Deprecated
@MappedTypes(Instant.class)
/**
 * @Deprecated 请直接用 mybatis-typehandlers-jsr310
 *
 */
public class InstantTypeHandler extends BaseTypeHandler<Instant> {

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, Instant instant, JdbcType jdbcType) throws SQLException {
		ps.setTimestamp(i, Timestamp.from(instant));
	}

	@Override
	public Instant getNullableResult(ResultSet rs, String columnName) throws SQLException {
		Timestamp ts = rs.getTimestamp(columnName);
		if (ts != null) {
			return ts.toInstant();
		} 
		
		return null;
	}

	@Override
	public Instant getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		Timestamp ts = rs.getTimestamp(columnIndex);
		if (ts != null) {
			return ts.toInstant();
		}
		return null;
	}

	@Override
	public Instant getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		Timestamp ts = cs.getTimestamp(columnIndex);
		if (ts != null) {
			return ts.toInstant();
		}
		return null;
	}

}
