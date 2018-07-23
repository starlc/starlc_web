/**
 * 
 */
package com.starlc.cache.mongdb.util;

/**
 * @author liucheng2
 *
 */


import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;



/**
 * MongoDB查询工具类
 * 
 * @author starlc
 * @since JDK1.7
 * @version 2018年7月18日 starlc 新建
 */
public final class MongoDBQueryUtil {
    
    /**
     * 构造方法
     */
    private MongoDBQueryUtil() {
        
    }
    
    /** 包名称属性 */
    public static String MODEL_PACKAGE_NAME = "modelPackage";
    
    /**
     * 根据id加载元数据对象
     * 
     * @param <T> 元数据对象类型
     * @param modelId 元数据id
     * @param modelType 元数据类型
     * @return 元数据对象
     */
    public static <T> T loadMetadata(String modelId, Class<T> modelType) {
        return (T) null;
    }
    
    /**
     * 根据id加载元数据对象信息
     * 
     * @param modelId 元数据id
     * @param keys 指定列
     * @return 元数据对象
     */
    public static Map<String, Object> loadMetadata(String modelId, List<String> keys) {
        return null;
    }
    
    
    /**
     * 获取左模糊匹配正则
     * 
     * @param value 值
     * @return 正则
     */
    public static Pattern getLeftLikePattern(Object value) {
        // 正则.转义
        return Pattern.compile("^.*" + strConvert(value) + "$", Pattern.CASE_INSENSITIVE);
    }
    
    /**
     * 获取右模糊匹配正则
     * 
     * @param value 值
     * @return 正则
     */
    public static Pattern getRightLikePattern(Object value) {
        return Pattern.compile("^" + strConvert(value) + ".*$", Pattern.CASE_INSENSITIVE);
    }
    
    /**
     * 获取全模糊匹配正则
     * 
     * @param value 值
     * @return 获取全模糊匹配正则
     */
    public static Pattern getAllLikePattern(Object value) {
        return Pattern.compile("^.*" + strConvert(value) + ".*$", Pattern.CASE_INSENSITIVE);
    }
    
    /**
     * 正则.转义
     * 
     * @param value 值
     * @return 正则.转义
     */
    private static String strConvert(Object value) {
        String strValue = value + "";
        if (strValue.contains(".")) {
            strValue = strValue.replace(".", "\\.");
        }
        return strValue;
    }
    
}

