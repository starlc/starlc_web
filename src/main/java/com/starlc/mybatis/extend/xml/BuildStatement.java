package com.starlc.mybatis.extend.xml;


import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Id;

import org.apache.ibatis.builder.BuilderException;
import org.apache.ibatis.builder.MapperBuilderAssistant;
import org.apache.ibatis.executor.keygen.KeyGenerator;
import org.apache.ibatis.executor.keygen.NoKeyGenerator;
import org.apache.ibatis.mapping.ResultFlag;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.mapping.ResultSetType;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.mapping.StatementType;
import org.apache.ibatis.parsing.XNode;
import org.apache.ibatis.scripting.LanguageDriver;
import org.apache.ibatis.scripting.LanguageDriverRegistry;
import org.apache.ibatis.scripting.xmltags.DynamicSqlSource;
import org.apache.ibatis.scripting.xmltags.MixedSqlNode;
import org.apache.ibatis.scripting.xmltags.SqlNode;
import org.apache.ibatis.scripting.xmltags.TextSqlNode;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.TypeAliasRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.starlc.mybatis.extend.executor.keygen.UUIDKeyGenerator;
import com.starlc.mybatis.extend.util.ClassUtils;
import com.starlc.mybatis.extend.util.MetaMapping;


/**
 * 构建CRUDQ SQL类
 * 
 * @author starlc
 * @since 1.0
 * @version 2018-2-5
 */
public class BuildStatement {
    
	private static Logger logger = LoggerFactory.getLogger(BuildStatement.class);
    
    private TypeAliasRegistry typeAliasRegistry;
    
    private MapperBuilderAssistant builderAssistant;
    
    private Configuration configuration;
    
    public BuildStatement(TypeAliasRegistry typeAliasRegistry, MapperBuilderAssistant builderAssistant,
        Configuration configuration) {
        
        this.typeAliasRegistry = typeAliasRegistry;
        this.builderAssistant = builderAssistant;
        this.configuration = configuration;
    }
    
    protected Class<?> resolveClass(String alias) {
        if (alias == null) {
            return null;
        }
        try {
            return resolveAlias(alias);
        } catch (Exception e) {
            throw new BuilderException("Error resolving class . Cause: " + e, e);
        }
    }
    
    protected Class<?> resolveAlias(String alias) {
        return typeAliasRegistry.resolveAlias(alias);
    }
    
    protected ResultSetType resolveResultSetType(String alias) {
        if (alias == null) {
            return null;
        }
        try {
            return ResultSetType.valueOf(alias);
        } catch (IllegalArgumentException e) {
            throw new BuilderException("Error resolving ResultSetType. Cause: " + e, e);
        }
    }
    
    /**
     * 建立结果集映射
     * 
     * @param resultType
     * @param namespace
     * @param additionalResultMappings
     * @throws Exception
     */
    public void resultTypeMapResolve(String resultType, String namespace) {
        try {
            if (resultType != null) {
                Collection<String> collection = builderAssistant.getConfiguration().getResultMapNames();
                // 如果VO类型映射不存在则根据VO字段注解构建映射对象
                if (!collection.contains(resultType)) {
                    Class typeClass = resolveClass(resultType);
                    List<ResultMapping> resultMappings = new ArrayList<ResultMapping>();
                    resultMappings.addAll(Collections.<ResultMapping> emptyList());
                    List<Field> lstField = MetaMapping.getFieldByClass(typeClass);
                    // 如果VO字段设置了映射注解则自动构建resultMap，否则使用Mybatis默认的映射模式
                    if (lstField.size() > 0) {
                        for (Field field : lstField) {
                            // 根据VO字段映射注解增加resultMap
                            Column column = field.getAnnotation(Column.class);
                            String property = field.getName();
                            String columnName = column.name();
                            Class javaTypeClass = field.getType();
                            ResultMapping objResultMapping = builderAssistant.buildResultMapping(typeClass, property,
                                columnName, javaTypeClass, null, null, null, null, null, null,
                                new ArrayList<ResultFlag>());
                            resultMappings.add(objResultMapping);
                        }
                        builderAssistant.addResultMap(resultType, typeClass, null, null, resultMappings, null);
                    }
                } else if (namespace != null && !collection.contains(namespace + "." + resultType)) {
                    ResultMap objResultMap = builderAssistant.getConfiguration().getResultMap(resultType);
                    builderAssistant.getConfiguration().addResultMap( objResultMap);//namespace + "." + resultType,
                }
                
            }
        } catch (Exception e) {
            logger.error("添加类型映射异常: " + resultType, e);
            throw new RuntimeException("添加类型映射异常: " + e, e);
        }
    }
    
    /**
     * 根据VO生成insert、read、update、delete方法
     * 
     * @param list
     * @throws Exception
     */
    public void generateOperateElement(List<XNode> list) throws Exception {
        if (list != null && !list.isEmpty()) {
            for (XNode context : list) {
                String strClass = context.getStringAttribute("class");
                String strFilter = context.getStringAttribute("filter");
                String strNameSpace = context.getStringAttribute("namespace");
                if (strFilter == null || strFilter.indexOf("insert") != -1) {
                    buildInsertStatement(strClass);
                }
                if (strFilter == null || strFilter.indexOf("update") != -1) {
                    buildUpdateStatement(strClass);
                }
                if (strFilter == null || strFilter.indexOf("read") != -1) {
                    buildReadStatement(strClass, strNameSpace);
                }
                if (strFilter == null || strFilter.indexOf("delete") != -1) {
                    buildDeleteStatement(strClass);
                }
            }
        }
    }
    
    /**
     * 构建新增Statement
     * 
     * @param className
     */
    public void buildInsertStatement(String className) {
        Class parameterTypeClass = resolveClass(className);
        String id = "insert" + ClassUtils.getShortName(className).replace("VO", "");
        Integer fetchSize = null;
        Integer timeout = null;
        String parameterMap = null;
        String resultMap = null;
        String resultType = null;
        Class resultTypeClass = resolveClass(resultType);
        String resultSetType = null;
        StatementType statementType = StatementType.PREPARED;
        ResultSetType resultSetTypeEnum = resolveResultSetType(resultSetType);
        // 构建INSERT SQL
        List<SqlNode> contents = new ArrayList<SqlNode>();
        String data = buildInsertSQL(parameterTypeClass);
        contents.add(new TextSqlNode(data));
        MixedSqlNode rootSqlNode = new MixedSqlNode(contents);
        SqlSource sqlSource = new DynamicSqlSource(configuration, rootSqlNode);
        SqlCommandType sqlCommandType = SqlCommandType.INSERT;
        boolean flushCache = true;
        boolean useCache = false;
        //结果排序
        boolean resultOrdered = false;
        // 设置主键自动生成规则
        String keyProperty = null;
        String strKeyGeneratedValue = MetaMapping.getKeyGeneratedValue(parameterTypeClass);
        KeyGenerator keyGenerator = new NoKeyGenerator();
        if (strKeyGeneratedValue != null) {
            keyProperty = MetaMapping.getPKName(parameterTypeClass);
            if ("uuid".equals(strKeyGeneratedValue)) {
                keyGenerator = new UUIDKeyGenerator(keyProperty);
            }
        }
        String keyColumn = null;
        String databaseId = null;
        //TODO
        LanguageDriver lang = new LanguageDriverRegistry().getDefaultDriver();
        builderAssistant.addMappedStatement(id, sqlSource, statementType, sqlCommandType, fetchSize, timeout,
            parameterMap, parameterTypeClass, resultMap, resultTypeClass, resultSetTypeEnum, flushCache, useCache,
            resultOrdered,keyGenerator, keyProperty, keyColumn, databaseId,lang);
    }
    
    /**
     * 构建修改Statement
     * 
     * @param className
     */
    public void buildUpdateStatement(String className) {
        String id = "update" + ClassUtils.getShortName(className).replace("VO", "");
        Integer fetchSize = null;
        Integer timeout = null;
        String parameterMap = null;
        Class parameterTypeClass = resolveClass(className);
        String resultMap = null;
        String resultType = null;
        Class resultTypeClass = resolveClass(resultType);
        String resultSetType = null;
        StatementType statementType = StatementType.PREPARED;
        ResultSetType resultSetTypeEnum = resolveResultSetType(resultSetType);
        // 构建UPDATE SQL
        List<SqlNode> contents = new ArrayList<SqlNode>();
        String data = buildUpdateSQL(parameterTypeClass);
        contents.add(new TextSqlNode(data));
        MixedSqlNode rootSqlNode = new MixedSqlNode(contents);
        SqlSource sqlSource = new DynamicSqlSource(configuration, rootSqlNode);
        SqlCommandType sqlCommandType = SqlCommandType.UPDATE;
        boolean flushCache = true;
        boolean useCache = false;
        String keyProperty = null;
        KeyGenerator keyGenerator = new NoKeyGenerator();
        String keyColumn = null;
        String databaseId = null;
        builderAssistant.addMappedStatement(id, sqlSource, statementType, sqlCommandType, fetchSize, timeout,
            parameterMap, parameterTypeClass, resultMap, resultTypeClass, resultSetTypeEnum, flushCache, useCache,
            keyGenerator, keyProperty, keyColumn, databaseId);
    }
    
    /**
     * 构建删除Statement
     * 
     * @param className
     */
    public void buildDeleteStatement(String className) {
        String id = "delete" + ClassUtils.getShortName(className).replace("VO", "");
        Class parameterTypeClass = MetaMapping.getPKType(resolveClass(className));
        Integer fetchSize = null;
        Integer timeout = null;
        String parameterMap = null;
        String resultMap = null;
        String resultType = null;
        Class resultTypeClass = resolveClass(resultType);
        String resultSetType = null;
        StatementType statementType = StatementType.PREPARED;
        ResultSetType resultSetTypeEnum = resolveResultSetType(resultSetType);
        // 构建DELETE SQL
        List<SqlNode> contents = new ArrayList<SqlNode>();
        String data = buildDeleteSQL(resolveClass(className));
        contents.add(new TextSqlNode(data));
        
        MixedSqlNode rootSqlNode = new MixedSqlNode(contents);
        SqlSource sqlSource = new DynamicSqlSource(configuration, rootSqlNode);
        SqlCommandType sqlCommandType = SqlCommandType.DELETE;
        boolean flushCache = true;
        boolean useCache = false;
        String keyProperty = null;
        KeyGenerator keyGenerator = new NoKeyGenerator();
        String keyColumn = null;
        String databaseId = null;
        builderAssistant.addMappedStatement(id, sqlSource, statementType, sqlCommandType, fetchSize, timeout,
            parameterMap, parameterTypeClass, resultMap, resultTypeClass, resultSetTypeEnum, flushCache, useCache,
            keyGenerator, keyProperty, keyColumn, databaseId);
    }
    
    /**
     * 构建读取Statement
     * 
     * @param className
     * @param namespace
     */
    public void buildReadStatement(String className, String namespace) {
        String id = "read" + ClassUtils.getShortName(className).replace("VO", "");
        Class parameterTypeClass = MetaMapping.getPKType(resolveClass(className));
        String resultMap = className;
        resultTypeMapResolve(resultMap, namespace);
        Class resultTypeClass = null;
        StatementType statementType = StatementType.PREPARED;
        // 构建DELETE SQL
        List<SqlNode> contents = new ArrayList<SqlNode>();
        String data = buildReadSQL(resolveClass(className));
        contents.add(new TextSqlNode(data));
        MixedSqlNode rootSqlNode = new MixedSqlNode(contents);
        SqlSource sqlSource = new DynamicSqlSource(configuration, rootSqlNode);
        SqlCommandType sqlCommandType = SqlCommandType.SELECT;
        KeyGenerator keyGenerator = new NoKeyGenerator();
        builderAssistant.addMappedStatement(id, sqlSource, statementType, sqlCommandType, null, null, null,
            parameterTypeClass, resultMap, resultTypeClass, null, true, false, keyGenerator, null, null, null);
    }
    
    /**
     * 构建新增SQL
     * 
     * @param parameterTypeClass
     *            VO类
     * @return SQL 字符串
     */
    public String buildInsertSQL(Class parameterTypeClass) {
        List<Field> lstField = MetaMapping.getFieldByClass(parameterTypeClass);
        String strTableName = MetaMapping.getTableName(parameterTypeClass);
        StringBuilder strSQL = new StringBuilder(100);
        strSQL.append("INSERT INTO ");
        strSQL.append(strTableName);
        String strParams = " (";
        String strValues = " (";
        for (Field field : lstField) {
            String strColumn = MetaMapping.getColumnName(field);
            strParams += strColumn + ",";
            strValues += "#{" + field.getName() + "},";
        }
        strParams = strParams.substring(0, strParams.length() - 1);
        strValues = strValues.substring(0, strValues.length() - 1);
        strParams += ")";
        strValues += ")";
        strSQL.append(strParams);
        strSQL.append(" VALUES ");
        strSQL.append(strValues);
        return strSQL.toString();
    }
    
    /**
     * 构建修改SQL
     * 
     * @param parameterTypeClass
     *            VO类
     * @return SQL 字符串
     */
    public String buildUpdateSQL(Class parameterTypeClass) {
        List<Field> lstField = MetaMapping.getFieldByClass(parameterTypeClass);
        String strTableName = MetaMapping.getTableName(parameterTypeClass);
        StringBuilder strSQL = new StringBuilder(100);
        strSQL.append("UPDATE ");
        strSQL.append(strTableName);
        strSQL.append(" SET ");
        String strPK = "";
        String strPKValue = "";
        for (Field field : lstField) {
            String strColumn = MetaMapping.getColumnName(field);
            if (field.isAnnotationPresent(Id.class)) {
                strPK = strColumn;
                strPKValue = "#{" + field.getName() + "}";
            } else {
                strSQL.append(strColumn);
                strSQL.append("=");
                strSQL.append("#{" + field.getName() + "},");
            }
        }
        strSQL = strSQL.delete(strSQL.length() - 1, strSQL.length());
        strSQL.append(" WHERE ");
        strSQL.append(strPK);
        strSQL.append("=");
        strSQL.append(strPKValue);
        return strSQL.toString();
    }
    
    /**
     * 构建删除SQL
     * 
     * @param parameterTypeClass
     *            VO类
     * @return SQL 字符串
     */
    public String buildDeleteSQL(Class parameterTypeClass) {
        List<Field> lstField = MetaMapping.getFieldByClass(parameterTypeClass);
        String strTableName = MetaMapping.getTableName(parameterTypeClass);
        StringBuilder strSQL = new StringBuilder(100);
        strSQL.append("DELETE FROM ");
        strSQL.append(strTableName);
        strSQL.append(" WHERE ");
        String strPK = "";
        String strPKValue = "";
        for (Field field : lstField) {
            if (field.isAnnotationPresent(Id.class)) {
                strPK = MetaMapping.getColumnName(field);
                strPKValue = "#{" + field.getName() + "}";
            }
        }
        strSQL.append(strPK);
        strSQL.append("=");
        strSQL.append(strPKValue);
        return strSQL.toString();
    }
    
    /**
     * 构建读取SQL
     * 
     * @param parameterTypeClass
     *            VO类
     * @return SQL 字符串
     */
    public String buildReadSQL(Class parameterTypeClass) {
        List<Field> lstField = MetaMapping.getFieldByClass(parameterTypeClass);
        String strTableName = MetaMapping.getTableName(parameterTypeClass);
        StringBuilder strSQL = new StringBuilder(100);
        strSQL.append("SELECT ");
        String strPK = "";
        String strPKValue = "";
        for (Field field : lstField) {
            String strColumn = MetaMapping.getColumnName(field);
            if (field.isAnnotationPresent(Id.class)) {
                strPK = strColumn;
                strPKValue = "#{" + field.getName() + "}";
            }
            strSQL.append(strColumn);
            strSQL.append(",");
        }
        strSQL = strSQL.delete(strSQL.length() - 1, strSQL.length());
        strSQL.append(" FROM ");
        strSQL.append(strTableName);
        strSQL.append(" WHERE ");
        strSQL.append(strPK);
        strSQL.append("=");
        strSQL.append(strPKValue);
        return strSQL.toString();
    }
}

