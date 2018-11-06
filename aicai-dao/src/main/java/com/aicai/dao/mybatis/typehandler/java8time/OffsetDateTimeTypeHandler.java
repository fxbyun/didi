package com.aicai.dao.mybatis.typehandler.java8time;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

@Deprecated
@MappedTypes(OffsetDateTime.class)
/**
 * @Deprecated 请直接用 mybatis-typehandlers-jsr310
 *
 */
public class OffsetDateTimeTypeHandler extends BaseTypeHandler<OffsetDateTime> {

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, OffsetDateTime parameter, JdbcType jdbcType) throws SQLException {
		ps.setTimestamp(i, Timestamp.from(parameter.toInstant()), GregorianCalendar.from(parameter.toZonedDateTime()));
	}

	@Override
	public OffsetDateTime getNullableResult(ResultSet rs, String columnName) throws SQLException {
		Timestamp ts = rs.getTimestamp(columnName, Calendar.getInstance());
		if (ts != null) {
			return OffsetDateTime.ofInstant(ts.toInstant(), ZoneId.systemDefault());
		}

		return null;
	}

	@Override
	public OffsetDateTime getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		Timestamp ts = rs.getTimestamp(columnIndex, Calendar.getInstance());
		if (ts != null) {
			return OffsetDateTime.ofInstant(ts.toInstant(), ZoneId.systemDefault());
		}

		return null;
	}

	@Override
	public OffsetDateTime getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		Timestamp ts = cs.getTimestamp(columnIndex, Calendar.getInstance());
		if (ts != null) {
			return OffsetDateTime.ofInstant(ts.toInstant(), ZoneId.systemDefault());
		}
		
		return null;
	}
}
