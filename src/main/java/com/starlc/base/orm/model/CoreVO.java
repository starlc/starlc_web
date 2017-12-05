/******************************************************************************
* Copyright (C) 2017 Starlc
*****************************************************************************/
 
package com.starlc.base.orm.model; 

import java.io.Serializable;
import java.lang.reflect.Field;

import javax.persistence.Column;
import javax.persistence.Transient;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.starlc.base.orm.util.ClazzUtil;

/**
 * 类注释信息
 * @Description:   TODO(用一句话描述该文件做什么) 
 * 
 * @author   starlc
 * @since	 jdk1.7
 * @version  V-0.1
 * @Date     2017年12月5日 starlc      
 */
public class CoreVO implements Serializable{
    /** 默认序列 */
    private static final long serialVersionUID = 1L;
    
    /**  */
    @Transient
    protected String _id;
    
    /**  */
    @Transient
    protected String actionType;
    
    /**  */
    @Transient
    protected String[] idList;
    
    /**  */
    @Transient
    protected int pageNo;
    
    /**  */
    @Transient
    protected int pageSize;
    
    /**  */
    @Transient
    protected String sortFieldName;
    
    /**  */
    @Transient
    protected String sortType;
    
    /**
     * 构造方法
     */
    public CoreVO() {
        this.pageNo = 1;
        this.pageSize = 20;
    }
    
    /**
     * @return xx
     */
    public String get_id() {
        return this._id;
    }
    
    /**
     * @return xx
     */
    public String getActionType() {
        return this.actionType;
    }
    
    /**
     * @return xx
     */
    public String[] getIdList() {
        return this.idList;
    }
    
    /**
     * @return xx
     */
    public int getPageNo() {
        return this.pageNo;
    }
    
    /**
     * @return xx
     */
    public int getPageSize() {
        return this.pageSize;
    }
    
    /**
     * @return xx
     */
    public String getSortFieldName() {
        if (this.sortFieldName == null) {
            return null;
        }
        
        Field objField = ClazzUtil.getFieldByName(super.getClass(), this.sortFieldName);
        if (objField == null) {
            return null;
        }
        if (objField.isAnnotationPresent(Column.class)) {
            Column objColumn = objField.getAnnotation(Column.class);
            return objColumn.name();
        }
        return objField.getName();
    }
    
    /**
     * @return xx
     */
    public String getSortType() {
        if ((this.sortType != null) && (this.sortType.length() > 5)) {
            return "";
        }
        
        return this.sortType;
    }
    
    /**
     * @param id xx
     */
    public void set_id(String id) {
        this._id = id;
    }
    
    /**
     * @param actionType xx
     */
    public void setActionType(String actionType) {
        this.actionType = actionType;
    }
    
    /**
     * @param idList xx
     */
    public void setIdList(String[] idList) {
        this.idList = idList;
    }
    
    /**
     * @param pageNo xx
     */
    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }
    
    /**
     * @param pageSize xx
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
    
    /**
     * @param sortFieldName xx
     */
    public void setSortFieldName(String sortFieldName) {
        this.sortFieldName = sortFieldName;
    }
    
    /**
     * @param sortType xx
     */
    public void setSortType(String sortType) {
        this.sortType = sortType;
    }
    
    @Override
    public boolean equals(Object objValue) {
        boolean bEqual = super.equals(objValue);
        if (super.equals(objValue))
            bEqual = true;
        else {
            bEqual = EqualsBuilder.reflectionEquals(this, objValue);
        }
        return bEqual;
    }
    
    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }
    
    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
 