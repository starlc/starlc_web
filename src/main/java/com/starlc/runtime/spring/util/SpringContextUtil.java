/**
 * 
 */
package com.starlc.runtime.spring.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 上下文获取工具类
 * @author liucheng2
 *
 */

@Component
public class SpringContextUtil implements ApplicationContextAware {
    
    /** spring context */
    private static ApplicationContext applicationContext = null;
    
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        
        if (SpringContextUtil.applicationContext == null) {
            SpringContextUtil.applicationContext = applicationContext;
        }
    }
    
    /**
     * 获取applicationContext
     * 
     * @return applicationContext
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }
    
    /**
     * 通过name获取 Bean
     * 
     * @param name Bean
     * @return Bean
     * @throws BeansException 异常
     */
    public static Object getBean(String name) {
        Object obj = null;
        try {
            obj = getApplicationContext().getBean(name);
        } catch (BeansException e) {
            return obj;
        }
        return obj;
    }
    
    /**
     * 通过class获取Bean.
     * 
     * @param <T> T
     * @param clazz 类名
     * @return Bean
     * @throws BeansException 异常
     */
    public static <T> T getBean(Class<T> clazz) {
        
        T obj = null;
        try {
            obj = getApplicationContext().getBean(clazz);
        } catch (BeansException e) {
            return obj;
        }
        return obj;
    }
    
    /**
     * 通过name,以及Clazz返回指定的Bean
     * 
     * @param <T> T
     * @param name name
     * @param clazz name
     * @return Bean
     * @throws BeansException 异常
     */
    public static <T> T getBean(String name, Class<T> clazz) {
        
        T obj = null;
        try {
            obj = getApplicationContext().getBean(name, clazz);
        } catch (BeansException e) {
            return obj;
        }
        return obj;
        
    }
}

