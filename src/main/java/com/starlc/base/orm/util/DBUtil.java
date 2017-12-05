/******************************************************************************
* Copyright (C) 2017 Starlc
*****************************************************************************/
 
package com.starlc.base.orm.util; 

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 类注释信息
 * @Description:   TODO(用一句话描述该文件做什么) 
 * 
 * @author   starlc
 * @since	 jdk1.7
 * @version  V-0.1
 * @Date     2017年12月5日 starlc      
 */
public final class DBUtil {
    /**  */
    private static final Logger OBJLOGGER = LoggerFactory.getLogger(DBUtil.class);
    
    /**  */
    public static final String DATASOURCE_JNDI = "CTDataSource";
    
    /**  */
    public static final String CONTAINER_PREFIX = "java:comp/env/";
    
    /**
     * @return xx
     */
    public static Connection getConnection() {
        DataSource objDs = null;
        Connection objConnection = null;
        try {
            objDs = lookupDataSource();
            objConnection = objDs.getConnection();
        } catch (NamingException e) {
            OBJLOGGER.error("获取数据源出错，jndi名称：CTDataSource\n异常堆栈", e);
        } catch (SQLException e) {
            OBJLOGGER.error("获取数据源出错，jndi名称：CTDataSource\n异常堆栈", e);
        }
        
        return objConnection;
    }
    
    /**
     * @return xx
     * @throws NamingException xx
     */
    private static DataSource lookupDataSource() throws NamingException {
        Context objContext = null;
        DataSource objDs = null;
        try {
            objContext = new InitialContext();
            objDs = (DataSource) objContext.lookup("java:comp/env/CTDataSource");
        } catch (NamingException e) {
            OBJLOGGER.warn("根据\"java:comp/env/CTDataSource\"未找到数据源，尝试采用\"CTDataSource\"查找。");
            
            if (objContext != null) {
                objDs = (DataSource) objContext.lookup("CTDataSource");
            }
        }
        return objDs;
    }
    
    /**
     * @param connection xx
     * @param stmt xx
     * @param rs xx
     */
    public static void closeConnection(Connection connection, Statement stmt, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                OBJLOGGER.error("关闭游标池出错：", e);
            }
        }
        
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                OBJLOGGER.error("关闭statement出错：", e);
            }
        }
        if (connection == null)
            return;
        try {
            connection.close();
        } catch (SQLException e) {
            OBJLOGGER.error("关闭数据库连接出错：", e);
        }
    }
}
 