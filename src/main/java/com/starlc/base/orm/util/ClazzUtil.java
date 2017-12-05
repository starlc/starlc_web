/******************************************************************************
* Copyright (C) 2017 Starlc
*****************************************************************************/
 
package com.starlc.base.orm.util; 

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.starlc.base.orm.exception.OrmException;
import com.starlc.base.orm.model.CoreVO;

/**
 * 类注释信息
 * @Description:   TODO(用一句话描述该文件做什么) 
 * 
 * @author   starlc
 * @since	 jdk1.7
 * @version  V-0.1
 * @Date     2017年12月5日 starlc      
 */
public final class ClazzUtil {
    /**  */
    private static final Logger LOGGER = LoggerFactory.getLogger(ClazzUtil.class);
    
    /**
     * @param byClass xx
     * @return xx
     */
    public static List<Field> getFieldByClass(Class<?> byClass) {
        return getFieldByClass(byClass, null);
    }
    
    /**
     * @param byClass xx
     * @param classFieldFilter xx
     * @return xx
     */
    public static List<Field> getFieldByClass(Class<?> byClass, IClazzFieldFilter classFieldFilter) {
        List<Field> lstField = new ArrayList<Field>(10);
        if (byClass.getSuperclass() != Object.class) {
            lstField.addAll(getFieldByClass(byClass.getSuperclass()));
        }
        Field[] objFields = byClass.getDeclaredFields();
        for (Field objField : objFields) {
            if (classFieldFilter != null) {
                if (classFieldFilter.isFilter(byClass, objField))
                    lstField.add(objField);
            } else {
                lstField.add(objField);
            }
        }
        return lstField;
    }
    
    /**
     * @param byClass xx
     * @param fieldName xx
     * @return xx
     */
    public static Field getFieldByName(Class<?> byClass, String fieldName) {
        Field objResultField = null;
        try {
            objResultField = byClass.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            LOGGER.debug(byClass + "类中找不到字段" + fieldName, e);
        }
        if ((objResultField == null) && (byClass.getSuperclass() != Object.class)) {
            objResultField = getFieldByName(byClass.getSuperclass(), fieldName);
        }
        
        return objResultField;
    }
    
    /**
     * @param <T> xx
     * @param t xx
     * @return xx
     */
    public static <T extends CoreVO> String getTableName(T t) {
        return getTableName(t.getClass());
    }
    
    /**
     * @param <T> xx
     * @param clazz xx
     * @return xx
     */
    public static <T extends CoreVO> String getTableName(Class<T> clazz) {
        String objResult = null;
        if (clazz.isAnnotationPresent(Table.class)) {
            Table objTableAnnotation = clazz.getAnnotation(Table.class);
            objResult = objTableAnnotation.name();
            if (StringUtils.isEmpty(objResult)) {
                objResult = clazz.getSimpleName();
            }
        }
        return objResult;
    }
    
    /**
     * @param <T> xx
     * @param t xx
     * @return xx
     */
    public static <T extends CoreVO> String getEntityName(T t) {
        return getEntityName(t.getClass());
    }
    
    /**
     * @param <T> xx
     * @param clazz xx
     * @return xx
     */
    public static <T extends CoreVO> String getEntityName(Class<T> clazz) {
        String objResult = null;
        if (clazz.isAnnotationPresent(Entity.class)) {
            Entity objEntityAnnotation = clazz.getAnnotation(Entity.class);
            objResult = objEntityAnnotation.name();
            if (StringUtils.isEmpty(objResult)) {
                objResult = clazz.getSimpleName();
            }
        }
        return objResult;
    }
    
    /**
     * @param <T> xx
     * @param t xx
     * @return xx
     * @throws OrmException xx
     */
    public static <T extends CoreVO> String getPrimaryKeyName(T t) throws OrmException {
        return getPrimaryKeyName(t.getClass());
    }
    
    /**
     * @param <T> xx
     * @param clazz xx
     * @return xx
     * @throws OrmException xx
     */
    public static <T extends CoreVO> String getPrimaryKeyName(Class<T> clazz) throws OrmException {
        Object objResult = null;
        Field[] objFields = clazz.getDeclaredFields();
        for (Field objField : objFields) {
            if (!(objField.isAnnotationPresent(Id.class)))
                continue;
            try {
                objField.setAccessible(true);
                objResult = objField.getName();
            } catch (SecurityException e) {
                throw new OrmException("反射获取主键属性名称" + objField.getName() + "时出错", e);
            }
        }
        
        return ((objResult != null) ? objResult.toString() : "");
    }
    
    /**
     * @param <T> xx
     * @param t xx
     * @return xx
     * @throws OrmException xx
     */
    public static <T extends CoreVO> String getPrimaryKeyValue(T t) throws OrmException {
        Object objResult = null;
        Field[] objFields = t.getClass().getDeclaredFields();
        for (Field objField : objFields) {
            if (!(objField.isAnnotationPresent(Id.class)))
                continue;
            try {
                objField.setAccessible(true);
                objResult = objField.get(t);
            } catch (IllegalAccessException e) {
                throw new OrmException("反射获取主键属性" + objField.getName() + "值时出错", e);
            }
        }
        
        return ((objResult != null) ? objResult.toString() : "");
    }
}
 