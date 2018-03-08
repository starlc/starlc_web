package com.starlc.mybatis.extend.executor.keygen;

import java.sql.Statement;
import java.util.UUID;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.keygen.KeyGenerator;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;

public class UUIDKeyGenerator implements KeyGenerator {
	/**
	 * 主键名称
	 */
	private String keyProperty;

	public UUIDKeyGenerator(String keyProperty) {
		this.keyProperty = keyProperty;
	}

	public void processBefore(Executor executor, MappedStatement ms, Statement stmt, Object parameter) {
		final Configuration configuration = ms.getConfiguration();
		final MetaObject metaParam = configuration.newMetaObject(parameter);
		metaParam.setValue(keyProperty, UUID.randomUUID().toString());
	}

	public void processAfter(Executor executor, MappedStatement ms, Statement stmt, Object parameter) {
	}

}
