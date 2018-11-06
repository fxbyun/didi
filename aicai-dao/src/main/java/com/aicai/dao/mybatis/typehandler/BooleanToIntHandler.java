/**
 *
 */
package com.aicai.dao.mybatis.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

@Deprecated
/**
 * deprecated是因为mybatis直接支持boolean型，请用mybatis的，不行再提需求
 * 
 * @author liuhui
 * @date 2011-12-5
 */
public class BooleanToIntHandler extends BaseTypeHandler<Boolean> {

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, Boolean parameter, JdbcType jdbcType) throws SQLException {
		if (parameter) {
			ps.setByte(i, (byte) 1);
		} else {
			ps.setByte(i, (byte) 0);
		}
	}

	@Override
	public Boolean getNullableResult(ResultSet rs, String columnName) throws SQLException {
		Integer value = rs.getInt(columnName);
		if (rs.wasNull()) {
			return false;
		}
		if (value == 0) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public Boolean getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		Integer value = cs.getInt(columnIndex);
		if (cs.wasNull()) {
			return false;
		}
		if (value == 0) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public Boolean getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		Integer value = rs.getInt(columnIndex);
		if (rs.wasNull()) {
			return false;
		}
		if (value == 0) {
			return false;
		} else {
			return true;
		}
	}

}
