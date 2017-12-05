/******************************************************************************
* Copyright (C) 2017 Starlc
*****************************************************************************/
 
package com.starlc.mybatis.extend.mapping; 

/**
 * 类注释信息
 * @Description:   TODO(用一句话描述该文件做什么) 
 * 
 * @author   starlc
 * @since	 jdk1.7
 * @version  V-0.1
 * @Date     2017年12月5日 starlc      
 */
public class ResourceMapping {
private String path;
    
    private long fileTime;
    
    /**
     * 构造函数
     */
    public ResourceMapping() {
        
    }
    
    /**
     * 构造函数
     * 
     * @param path
     * @param fileTime
     */
    public ResourceMapping(String path, long fileTime) {
        this.path = path;
        this.fileTime = fileTime;
    }
    
    /**
     * @return 获取 path属性值
     */
    public String getPath() {
        return path;
    }
    
    /**
     * @param path 设置 path 属性值为参数值 path
     */
    public void setPath(String path) {
        this.path = path;
    }
    
    /**
     * @return 获取 fileTime属性值
     */
    public long getFileTime() {
        return fileTime;
    }
    
    /**
     * @param fileTime 设置 fileTime 属性值为参数值 fileTime
     */
    public void setFileTime(long fileTime) {
        this.fileTime = fileTime;
    }
}
 