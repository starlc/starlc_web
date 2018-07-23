/**
 * 
 */
package com.starlc.cache.mongdb.util;

/**
 * @author liucheng2
 *
 */
public enum MongodbOperator {
	/** 大于 */
    GREATER_THAN("$gt"),
    
    /** 小于 */
    LESS_THAN("$lt"),
    
    /** 大于等于 */
    GREATER_THAN_EQUALS("$gte"),
    
    /** 小于等于 */
    LESS_THAN_EQUALS("$lte"),
    
    /** 不等于 */
    NOT_EQUALS("$ne"),
    
    /** 并且 */
    AND("$and"),
    
    /** IN条件 */
    IN("$in"),
    
    /** 或者 */
    OR("$or");
    
    /** 值 */
    private String value;
    
    /**
     * 构造方法
     *
     * @param value 值
     */
    MongodbOperator(String value) {
        setValue(value);
    }
    
    /**
     * @return 获取 value属性值
     */
    public String getValue() {
        return value;
    }
    
    /**
     * @param value 设置 value 属性值为参数值 value
     */
    public void setValue(String value) {
        this.value = value;
    }
    
}
