/**
 * 
 */
package com.starlc.mybatis.extend.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 实体类注解元数据
 * @author liucheng2
 *
 */
public class MetaMapping {
    
    /**
     * 返回VO表映射
     * 
     * @param byClass
     * @return
     */
    public static String getTableName(Class byClass) {
        Table objTable = (Table) byClass.getAnnotation(Table.class);
        String strTableName = objTable.name();
        return strTableName;
    }
    
    /**
     * 返回主键名
     * 
     * @param byClass
     * @return
     */
    public static String getPKName(Class byClass) {
        String strColumn = null;
        List<Field> lstField = MetaMapping.getFieldByClass(byClass);
        for (Field field : lstField) {
            if (field.isAnnotationPresent(Id.class)) {
                strColumn = field.getName();
                break;
            }
        }
        return strColumn;
    }
    
    /**
     * 返回主键映射名
     * 
     * @param byClass
     * @return
     */
    public static String getPKColumnName(Class byClass) {
        String strColumn = null;
        List<Field> lstField = MetaMapping.getFieldByClass(byClass);
        for (Field field : lstField) {
            if (field.isAnnotationPresent(Id.class)) {
                strColumn = getColumnName(field);
                break;
            }
        }
        return strColumn;
    }
    
    /**
     * 返回主键映射名
     * 
     * @param byClass
     * @return
     */
    public static Class getPKType(Class byClass) {
        Class objTypeClass = null;
        List<Field> lstField = MetaMapping.getFieldByClass(byClass);
        for (Field field : lstField) {
            if (field.isAnnotationPresent(Id.class)) {
                objTypeClass = field.getType();
                break;
            }
        }
        return objTypeClass;
    }
    
    /**
     * 返回建生产规则
     * 
     * @param byClass
     * @return
     */
    public static String getKeyGeneratedValue(Class byClass) {
        String strGenerator = null;
        List<Field> lstField = MetaMapping.getFieldByClass(byClass);
        for (Field field : lstField) {
            if (field.isAnnotationPresent(GeneratedValue.class)) {
                GeneratedValue objGeneratedValue = field.getAnnotation(GeneratedValue.class);
                strGenerator = objGeneratedValue.generator();
                break;
            }
        }
        return strGenerator;
    }
    
    /**
     * 返回VO字段映射表字段名
     * 
     * @param field
     * @return
     */
    public static String getColumnName(Field field) {
        Column objColumn = field.getAnnotation(Column.class);
        String strColumn = objColumn.name();
        return strColumn;
    }
    
    /**
     * 返回VO字段和数据库映射
     * 
     * getSuperclass
     * 此 Class 表示 Object 类、一个接口、一个基本类型或 void，则返回 null
     * 
     * @param byClass
     * @return
     */
    public static List<Field> getFieldByClass(Class byClass) {
        List<Field> lstField = new ArrayList<Field>(10);
        Class<?> objClass = byClass.getSuperclass();
        if (objClass != Object.class && objClass != null) {
            lstField.addAll(getFieldByClass(byClass.getSuperclass()));
        }
        Field[] fields = byClass.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Column.class)) {
                lstField.add(field);
            }
        }
        return lstField;
    }
}
