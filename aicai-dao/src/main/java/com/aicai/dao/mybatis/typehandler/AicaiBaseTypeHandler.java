package com.aicai.dao.mybatis.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeException;
import org.apache.ibatis.type.TypeHandler;

/**
 * 代码全部来自mybatis.BaseTypeHandler，但去掉了extends TypeReference，
 * 不会抛TypeReference()的那个异常。
 * 
 * @author 周锋
 *
 * @param <T>
 */
public abstract class AicaiBaseTypeHandler<T> implements TypeHandler<T> {
	
	public void setParameter(PreparedStatement ps, int i, T parameter, JdbcType jdbcType) throws SQLException {
		if (parameter == null) {
			if (jdbcType == null) {
				throw new TypeException("JDBC requires that the JdbcType must be specified for all nullable parameters.");
			}
			try {
				ps.setNull(i, jdbcType.TYPE_CODE);
			} catch (SQLException e) {
				throw new TypeException("Error setting null for parameter #" + i + " with JdbcType " + jdbcType + " . " +
						"Try setting a different JdbcType for this parameter or a different jdbcTypeForNull configuration property. " +
						"Cause: " + e, e);
			}
		} else {
			setNonNullParameter(ps, i, parameter, jdbcType);
		}
	}

	public T getResult(ResultSet rs, String columnName) throws SQLException {
		T result = getNullableResult(rs, columnName);
		return result;
	}

	public T getResult(ResultSet rs, int columnIndex) throws SQLException {
		T result = getNullableResult(rs, columnIndex);
		if(rs.wasNull()){
			return null;
		}
		return result;
	}

	public T getResult(CallableStatement cs, int columnIndex) throws SQLException {
		T result = getNullableResult(cs, columnIndex);
		if(cs.wasNull()){
			return null;
		}
		return result;
	}

	public abstract void setNonNullParameter(PreparedStatement ps, int i, T parameter, JdbcType jdbcType) throws SQLException;

	public abstract T getNullableResult(ResultSet rs, String columnName) throws SQLException;

	public abstract T getNullableResult(ResultSet rs, int columnIndex) throws SQLException;

	public abstract T getNullableResult(CallableStatement cs, int columnIndex) throws SQLException;

}
