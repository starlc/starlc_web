/******************************************************************************
* Copyright (C) 2017 Starlc
*****************************************************************************/
 
package com.starlc.mybatis.extend.plugin; 

import java.sql.Connection;
import java.sql.Statement;
import java.util.Properties;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.plugin.Intercepts;

/**
 * 类注释信息
 * @Description:   StatementInterceptor方法拦截器
 * 
 * @author   starlc
 * @since	 jdk1.7
 * @version  V-0.1
 * @Date     2017年12月5日 starlc      
 */
@Intercepts({ @Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class }),
    @Signature(type = StatementHandler.class, method = "batch", args = { Statement.class }) })
    public class StatementInterceptor implements Interceptor {
    
    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        //物理分页插件
        PaginationInterceptor objPaginationInterceptor = new PaginationInterceptor();
        objPaginationInterceptor.intercept(statementHandler);
        //SQL日志打印插件
        LogInterceptor objLogInterceptor = new LogInterceptor();
        objLogInterceptor.intercept(statementHandler);
        return invocation.proceed();
    }
    
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }
    
    public void setProperties(Properties properties) {
    }
}

 