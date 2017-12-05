/******************************************************************************
* Copyright (C) 2017 Starlc
*****************************************************************************/
 
package com.starlc.mybatis.extend.plugin; 

/**
 * 类注释信息
 * @Description:   TODO(用一句话描述该文件做什么) 
 * 
 * @author   starlc
 * @since	 jdk1.7
 * @version  V-0.1
 * @Date     2017年12月5日 starlc      
 */
public abstract class Dialect {
    public static enum Type {
        MYSQL, ORACLE
    }

    public abstract String getLimitString(String sql, int skipResults, int maxResults);
}
 