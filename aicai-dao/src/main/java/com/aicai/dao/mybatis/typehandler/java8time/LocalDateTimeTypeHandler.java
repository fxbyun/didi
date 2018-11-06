package com.aicai.dao.mybatis.typehandler.java8time;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

@Deprecated
@MappedTypes(LocalDateTime.class)
/**
 * @Deprecated 请直接用 mybatis-typehandlers-jsr310
 */
public class LocalDateTimeTypeHandler extends BaseTypeHandler<LocalDateTime> {

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, LocalDateTime parameter, JdbcType jdbcType) throws SQLException {
		ps.setTimestamp(i, Timestamp.valueOf(parameter));
	}

	@Override
	public LocalDateTime getNullableResult(ResultSet rs, String columnName) throws SQLException {
		Timestamp timestamp = rs.getTimestamp(columnName);
		if (timestamp != null) {
			return timestamp.toLocalDateTime();
		} 
		
		return null;
	}

	@Override
	public LocalDateTime getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		Timestamp timestamp = rs.getTimestamp(columnIndex);
		if (timestamp != null) {
			return timestamp.toLocalDateTime();
		}

		return null;
	}

	@Override
	public LocalDateTime getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		Timestamp timestamp = cs.getTimestamp(columnIndex);
		if (timestamp != null) {
			return timestamp.toLocalDateTime();
		}
		
		return null;
	}

}
