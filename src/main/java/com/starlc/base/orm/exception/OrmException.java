/******************************************************************************
* Copyright (C) 2017 Starlc
*****************************************************************************/
 
package com.starlc.base.orm.exception; 

/**
 * 类注释信息
 * @Description:   TODO(用一句话描述该文件做什么) 
 * 
 * @author   starlc
 * @since	 jdk1.7
 * @version  V-0.1
 * @Date     2017年12月5日 starlc      
 */
public class OrmException extends RuntimeException{
    /** 默认序列 */
    private static final long serialVersionUID = 1L;
    
    /**
     * 构造方法
     *
     * @param message String
     */
    public OrmException(String message) {
        super(message);
    }
    
    /**
     * 构造方法
     *
     * @param message 异常信息
     * @param throwable Throwable
     */
    public OrmException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
 