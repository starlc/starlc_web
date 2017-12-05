/******************************************************************************
* Copyright (C) 2017 Starlc
*****************************************************************************/
 
package com.starlc.mybatis.extend.plugin; 

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;

/**
 * 类注释信息
 * @Description:   打印SQL插件
 * 
 * @author   starlc
 * @since	 jdk1.7
 * @version  V-0.1
 * @Date     2017年12月5日 starlc      
 */
public class LogInterceptor {
    private static final Log logger = LogFactory.getLog(LogInterceptor.class);

    /**
     * 打印SQL
     * 
     * @param statementHandler
     * @throws Throwable
     */
    public void intercept(StatementHandler statementHandler) throws Throwable {
        MetaObject metaStatementHandler = MetaObject.forObject(statementHandler,new DefaultObjectFactory(),new DefaultObjectWrapperFactory());
        String originalSql = (String) metaStatementHandler.getValue("delegate.boundSql.sql");
        Object parame = metaStatementHandler.getValue("delegate.boundSql.parameterObject");
        logger.debug("SQL:" + originalSql);
        logger.debug("parame:" + parame);
    }
}
 