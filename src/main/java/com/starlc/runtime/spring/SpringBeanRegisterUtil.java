/**
 * 
 */
package com.starlc.runtime.spring;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import com.starlc.runtime.spring.util.SpringContextUtil;


/**
 * @author liucheng2
 *
 */
public class SpringBeanRegisterUtil {
    
    /***/
    private static final Pattern facadePattern = Pattern.compile("^com\\.comtop\\..*\\.facade\\..*Facade$");
    
    /** 日志对象 */
    private static final Logger LOGGER = LoggerFactory.getLogger(SpringBeanRegisterUtil.class);
    
    /**
     * 私有构造函数
     */
    private SpringBeanRegisterUtil() {
        // do nothing
    }
    
    /**
     * register the bean
     * 
     * 
     * @param beanId beanId
     * @param className 类名
     * 
     * */
    public static void registerBean(String beanId, String className) {
        
        BeanDefinitionRegistry beanDefinitionRegistry = getSpringBeanRegistry();
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(className);
        BeanDefinition beanDefinition = beanDefinitionBuilder.getBeanDefinition();
        registerBean(beanId, beanDefinitionRegistry, beanDefinition);
    }
    
    /**
     * @param beanId beanId
     * @param beanDefinitionRegistry 注册对项
     * @param beanDefinition bean定义对象
     */
    private static void registerBean(String beanId, BeanDefinitionRegistry beanDefinitionRegistry,
        BeanDefinition beanDefinition) {
        unregisterBean(beanId);
        beanDefinitionRegistry.registerBeanDefinition(beanId, beanDefinition);
    }
    
    /**
     * @return BeanDefinitionRegistry
     */
    private static BeanDefinitionRegistry getSpringBeanRegistry() {
        ApplicationContext context = SpringContextUtil.getApplicationContext();
        @SuppressWarnings("resource")
        ConfigurableApplicationContext configurableContext = (ConfigurableApplicationContext) context;
        BeanDefinitionRegistry beanDefinitionRegistry = (DefaultListableBeanFactory) configurableContext
            .getBeanFactory();
        // Spring的容器对象，不能关闭。关闭后，从容器中获取不了对象
        // configurableContext.close();
        return beanDefinitionRegistry;
    }
    
    /**
     * register the bean
     * 
     * 
     * @param beanType 类型
     * 
     * */
    public static void registerBean(Class<?> beanType) {
        
        BeanDefinitionRegistry beanDefinitionRegistry = getSpringBeanRegistry();
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(beanType);
        
        BeanDefinition beanDefinition = beanDefinitionBuilder.getBeanDefinition();
        
        String beanId = getServiceBeanId(beanType);
        
        registerBean(beanId, beanDefinitionRegistry, beanDefinition);
    }
    
    /**
     * register the bean
     * 
     * 
     * @param beanType 类型
     * 
     * */
    public static void registerSpringBean(Class<?> beanType) {
        BeanDefinitionRegistry beanDefinitionRegistry = getSpringBeanRegistry();
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(beanType);
        
        BeanDefinition beanDefinition = beanDefinitionBuilder.getBeanDefinition();
        
        String beanId = getSpringBeanId(beanType);
        
        if (beanId == null) {
            LOGGER.error("class:" + beanType.getName() + " is not a spring bean type");
            return;
        }
        
        registerBean(beanId, beanDefinitionRegistry, beanDefinition);
    }
    
    /**
     * 
     * @param beanType bean类型
     * @return beaId
     */
    private static String getSpringBeanId(Class<?> beanType) {
        Service objService = beanType.getAnnotation(Service.class);
        if (objService != null) {
            return getServiceBeanId(beanType);
        }
        RestController objRestController = beanType.getAnnotation(RestController.class);
        if (objRestController != null) {
            return getRestControllerBeanId(beanType);
        }
        Controller objController = beanType.getAnnotation(Controller.class);
        if (objController != null) {
            return getControllerBeanId(beanType);
        }
        Component objComponent = beanType.getAnnotation(Component.class);
        if (objComponent != null) {
            return getComponentBeanId(beanType);
        }
        Repository objRepository = beanType.getAnnotation(Repository.class);
        if (objRepository != null) {
            return getRepositoryBeanId(beanType);
        }
        return null;
    }
    
    /**
     * 
     * @param beanType bean类型
     * @return beanId
     */
    private static String getRepositoryBeanId(Class<?> beanType) {
        String beanId = "";
        Repository objService = beanType.getAnnotation(Repository.class);
        if (StringUtils.isBlank(objService.value())) {
            beanId = StringUtils.uncapitalize(beanType.getSimpleName());
        } else {
            beanId = objService.value();
        }
        return beanId;
    }
    
    /**
     * 
     * @param beanType bean类型
     * @return beanId
     */
    private static String getComponentBeanId(Class<?> beanType) {
        String beanId = "";
        Component objService = beanType.getAnnotation(Component.class);
        if (StringUtils.isBlank(objService.value())) {
            beanId = StringUtils.uncapitalize(beanType.getSimpleName());
        } else {
            beanId = objService.value();
        }
        return beanId;
    }
    
    /**
     * 
     * @param beanType bean类型
     * @return beanId
     */
    private static String getControllerBeanId(Class<?> beanType) {
        String beanId = "";
        Controller objService = beanType.getAnnotation(Controller.class);
        if (StringUtils.isBlank(objService.value())) {
            beanId = StringUtils.uncapitalize(beanType.getSimpleName());
        } else {
            beanId = objService.value();
        }
        return beanId;
    }
    
    /**
     * @param beanType bean类型
     * @return beanId
     */
    private static String getRestControllerBeanId(Class<?> beanType) {
        String beanId = "";
        RestController objRestController = beanType.getAnnotation(RestController.class);
        if (StringUtils.isBlank(objRestController.value())) {
            beanId = StringUtils.uncapitalize(beanType.getSimpleName());
        } else {
            beanId = objRestController.value();
        }
        return beanId;
    }
    
    /**
     * 
     * @param beanType bean类型
     * @return beanId
     */
    private static String getServiceBeanId(Class<?> beanType) {
        String beanId = "";
        Service objService = beanType.getAnnotation(Service.class);
        if (StringUtils.isBlank(objService.value())) {
            beanId = StringUtils.uncapitalize(beanType.getSimpleName());
        } else {
            beanId = objService.value();
        }
        return beanId;
    }
    
    /**
     * unregister the bean
     * 
     * @param beanType beanId
     * */
    public static void unregisterBean(Class<?> beanType) {
        String beanId = getSpringBeanId(beanType);
        unregisterBean(beanId);
    }
    
    /**
     * unregister the bean
     * 
     * @param beanId beanId
     * */
    public static void unregisterBean(String beanId) {
        BeanDefinitionRegistry beanDefinitionRegistry = getSpringBeanRegistry();
        if (beanDefinitionRegistry.containsBeanDefinition(beanId)) {
            beanDefinitionRegistry.removeBeanDefinition(beanId);
        }
    }
    
    /**
     * 是否为Spirng的bean
     * 
     * @param beanType 类型
     * @return true 是，false 不是
     */
    public static boolean isSpringFacadeBean(Class<?> beanType) {
        Matcher objMatch = facadePattern.matcher(beanType.getName());
        if (objMatch.find()) {
            Service objService = beanType.getAnnotation(Service.class);
            if (objService == null) {
                return false;
            }
            return true;
        }
        return false;
    }
    
    /**
     * 是否为Spirng的Controller
     * 
     * @param beanType 类型
     * @return true 是，false 不是
     */
    public static boolean isSpringBean(Class<?> beanType) {
        RestController objRestController = beanType.getAnnotation(RestController.class);
        if (objRestController != null) {
            return true;
        }
        Controller objController = beanType.getAnnotation(Controller.class);
        if (objController != null) {
            return true;
        }
        Service objService = beanType.getAnnotation(Service.class);
        if (objService != null) {
            return true;
        }
        Component objComponent = beanType.getAnnotation(Component.class);
        if (objComponent != null) {
            return true;
        }
        Repository objRepository = beanType.getAnnotation(Repository.class);
        if (objRepository != null) {
            return true;
        }
        return false;
    }
    
}

