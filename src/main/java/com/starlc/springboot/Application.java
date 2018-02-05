/******************************************************************************
 * Copyright (C) 2017 starlc
 *****************************************************************************/

package com.starlc.springboot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * xx
 *
 * @author starlc
 * @since jdk1.7
 * @version 2017年11月30日 starlc
 */
@SpringBootApplication
public class Application {
	 /** 日志 */
    private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);
    /**
     * xx
     * 
     * @param args xx
     */
    public static void main(String[] args) {
    	ConfigurableApplicationContext ctx =  SpringApplication.run(Application.class, args);
        LOGGER.info("A total of 【" + ctx.getBeanDefinitionCount() + "】beans.");
    }
    
}
