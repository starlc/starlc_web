/******************************************************************************
 * Copyright (C) 2017 Starlc
 *****************************************************************************/

package com.starlc.mybatis.extend.plugin;

/**
 * 类注释信息
 * 
 * @Description: 数据库类型
 * 
 * @author starlc
 * @since jdk1.7
 * @version V-0.1
 * @Date 2017年12月5日 starlc
 */
public abstract class Dialect {
    
    public static enum Type {
        /** MYSQL */
        MYSQL,
        /** ORACLE */
        ORACLE
    }
    
    public abstract String getLimitString(String sql, int skipResults, int maxResults);
}
