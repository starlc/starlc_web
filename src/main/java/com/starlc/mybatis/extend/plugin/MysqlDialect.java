/******************************************************************************
* Copyright (C) 2017 Starlc
*****************************************************************************/
 
package com.starlc.mybatis.extend.plugin; 

/**
 * 类注释信息
 * @Description:   MYSQL物理分页SQL处理 
 * 
 * @author   starlc
 * @since	 jdk1.7
 * @version  V-0.1
 * @Date     2017年12月5日 starlc      
 */
public class MysqlDialect extends Dialect{
    /**
     * MYSQL 分页处理
     */
    @Override
    public String getLimitString(String sql, int offset, int limit) {
        sql = sql.trim();
        StringBuffer pagingSelect = new StringBuffer(sql.length() + 100);
        pagingSelect.append(sql);
        pagingSelect.append(" limit ").append(offset).append(" , ").append(limit);
        return pagingSelect.toString();
    }
}
 