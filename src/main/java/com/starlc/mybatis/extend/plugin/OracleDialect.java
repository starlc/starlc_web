/******************************************************************************
* Copyright (C) 2017 Starlc
*****************************************************************************/
 
package com.starlc.mybatis.extend.plugin; 

/**
 * 类注释信息
 * @Description:   Oracle分页处理 
 * 
 * @author   starlc
 * @since	 jdk1.7
 * @version  V-0.1
 * @Date     2017年12月5日 starlc      
 */
public class OracleDialect  extends Dialect {
    /**
     * oracle分页
     */
    @Override
    public String getLimitString(String sql, int offset, int limit) {
        sql = sql.trim();
        StringBuffer pagingSelect = new StringBuffer(sql.length() + 100);
        
        pagingSelect.append("SELECT * FROM(SELECT INNER_TABLE.*,ROWNUM OUTER_TABLE_ROWNUM FROM (");
        pagingSelect.append(sql);
        pagingSelect.append(")INNER_TABLE WHERE ROWNUM <=").append(offset + limit);
        pagingSelect.append(") OUTER_TABLE WHERE OUTER_TABLE_ROWNUM >");
        pagingSelect.append(offset);
        
        return pagingSelect.toString();
    }
}
 