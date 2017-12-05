/******************************************************************************
* Copyright (C) 2017 Starlc
*****************************************************************************/
 
package com.starlc.base.orm.util; 

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 类注释信息
 * @Description:   TODO(用一句话描述该文件做什么) 
 * 
 * @author   starlc
 * @since	 jdk1.7
 * @version  V-0.1
 * @Date     2017年12月5日 starlc      
 */
public class SpringBeanContextUtil implements ApplicationContextAware{
    /**  */
    private static ApplicationContext applicationContext;
    
    @Override
    public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
        if (SpringBeanContextUtil.applicationContext == null) {
            SpringBeanContextUtil.applicationContext = applicationContext;
        }
    }
    
    /**
     * @return xx
     */
    public static ApplicationContext getApplicationContext() {
        return SpringBeanContextUtil.applicationContext;
    }
    
    /**
     * @param name xx
     * @return xx
     */
    public static Object getBean(final String name) {
        Object obj = null;
        try {
            obj = getApplicationContext().getBean(name);
        } catch (BeansException e) {
            return obj;
        }
        return obj;
    }
    
    /**
     * @param <T> xx
     * @param clazz xx
     * @return xx
     */
    public static <T> T getBean(final Class<T> clazz) {
        T obj = null;
        try {
            obj = getApplicationContext().getBean(clazz);
        } catch (BeansException e) {
            return obj;
        }
        return obj;
    }
    
    /**
     * @param <T> xx
     * @param name xx
     * @param clazz xx
     * @return xx
     */
    public static <T> T getBean(final String name, final Class<T> clazz) {
        T obj = null;
        try {
            obj = getApplicationContext().getBean(name, clazz);
        } catch (BeansException e) {
            return obj;
        }
        return obj;
    }
}
 