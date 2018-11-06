package com.aicai.dao.mybatis.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;

import com.aicai.dao.domain.DescriptionId;

public abstract class EnumDescriptionIdHandler extends AicaiBaseTypeHandler<DescriptionId> implements TypeHandler<DescriptionId> {

	private Class<DescriptionId> type;
	private DescriptionId[] instanceList;

	@SuppressWarnings("unchecked")
	protected EnumDescriptionIdHandler(){
		//this.type = getClass().getAnnotations()[0].value()[0];
		this.type = (Class<DescriptionId>)(((MappedTypes)(getClass().getAnnotations()[0])).value()[0]);
		this.instanceList = type.getEnumConstants();
	}

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, DescriptionId parameter, JdbcType jdbcType) throws SQLException {
		ps.setInt(i, parameter.getIndex());
	}

	@Override
	public DescriptionId getNullableResult(ResultSet rs, String columnName) throws SQLException {
		Integer value = rs.getInt(columnName);
		if(rs.wasNull()){
			return null;
		}
		for (DescriptionId instance : instanceList) {
			if (value == instance.getIndex()) {
				return instance;
			}
		}
		throw new SQLException("can't get instance for class[" + type.getName() + "] with value[" + value + "]");
	}

	@Override
	public DescriptionId getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		Integer value = cs.getInt(columnIndex);
		if (cs.wasNull()) {
			return null;
		}
		for (DescriptionId instance : instanceList) {
			if (value == instance.getIndex()) {
				return instance;
			}
		}
		throw new SQLException("can't get instance for class[" + type.getName() + "] with value[" + value + "]");
	}

	@Override
	public DescriptionId getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		Integer value = rs.getInt(columnIndex);
		if(rs.wasNull()){
			return null;
		}
		for (DescriptionId instance : instanceList) {
			if (value == instance.getIndex()) {
				return instance;
			}
		}
		throw new SQLException("can't get instance for class[" + type.getName() + "] with value[" + value + "]");
	}

}
