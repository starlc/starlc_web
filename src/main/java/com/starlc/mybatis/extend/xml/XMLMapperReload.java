package com.starlc.mybatis.extend.xml;

/** <a href="http://www.cpupk.com/decompiler">Eclipse Class Decompiler</a> plugin, Copyright (c) 2017 Chen Chao. */
/******************************************************************************
 * Copyright (C) 2014 ShenZhen ComTop Information Technology Co.,Ltd
 * All Rights Reserved.
 * 本软件为深圳康拓普开发研制。未经本公司正式书面同意，其他任何个人、团体不得使用、
 * 复制、修改或发布本软件.
 *****************************************************************************/

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;

import com.starlc.mybatis.extend.util.StringUtil;


/**
 * XML RELOAD
 * 
 * @author starlc
 * @since 1.0
 * @version 2018-2-5
 */
public class XMLMapperReload {
    
	private static Logger logger = LoggerFactory.getLogger(XMLMapperReload.class);
    
    /**
     * resource和namespace映射，用来在重载配置文件时候删除现有Mapperstatement
     */
    private static final Map<String, Resource> namespaceMap = new HashMap<String, Resource>();
    
    private static final Map<Resource, Long> fileModifyMap = new HashMap<Resource, Long>();
    
    private static Configuration configuration;
    
    private XMLMapperReload() {
        
    }
    
    public static void init(Configuration config) {
        configuration = config;
    }
    
    public static void addnamespaceMap(String namespace, Resource resource) throws IOException {
        namespaceMap.put(namespace, resource);
    }
    
    /**
     * 解析配置文件
     * 
     * @param namespace
     */
    public static void parseSqlXml(String namespace) {
        try {
        	Resource objXmlResource = namespaceMap.get(namespace);
            if (isModify(objXmlResource)) {
                String strPath = StringUtil.getFileName(objXmlResource.getURL().getPath());
                InputStream inputStream = objXmlResource.getInputStream();
                XMLMapperBuilder mapperParser = new XMLMapperBuilder(inputStream, configuration, strPath, configuration
                    .getSqlFragments(), objXmlResource);
                mapperParser.parse();
                inputStream.close();
                logger.info("重新加载" + strPath + "成功");
            }
            
        } catch (Exception e) {
            logger.error("根据命名空间" + namespace + "重新加载配置文件失败", e);
        }
    }
    
    /**
     * 更新文件时间
     * 
     * @param resource
     */
    public static void updateModify(Resource resource) {
        try {
            fileModifyMap.put(resource, resource.lastModified());
        } catch (Exception e) {
            logger.error("找不到指定文件" + resource, e);
        }
    }
    
    /**
     * 解析配置文件
     * 
     * @param namespace
     */
    public static MappedStatement parseSqlXml(MappedStatement ms) {
        // 如果是开发模式，则增加SQL Map文件监控，以支持文件热加载
        if ("develop".equals(configuration.getVariables().get("mode"))) {
            String strId = ms.getId();
            XMLMapperReload.parseSqlXml(StringUtil.getNamespace(strId));
            return configuration.getMappedStatement(strId);
        } else {
            return ms;
        }
    }
    
    /**
     * 
     * 获取最新的BoundSql
     * 
     * @return BoundSql
     */
    public static BoundSql getBoundSql(MappedStatement ms, Object parameter, BoundSql boundSql) {
        // 如果是开发模式，则增加SQL Map文件监控，以支持文件热加载
        if ("develop".equals(configuration.getVariables().get("mode"))) {
            return ms.getBoundSql(parameter);
        } else {
            return boundSql;
        }
    }
    
    /**
     * 是否修改
     * 
     * @param resource
     * @return
     */
    public static boolean isModify(Resource resource) {
        boolean result = false;
        try {
            Long lastModified = fileModifyMap.get(resource);
            if (lastModified == null) {
                result = true;
                fileModifyMap.put(resource, resource.lastModified());
                configuration.removeResourceLoaded(StringUtil.getFileName(resource.getURL().getPath()));
            } else if (lastModified != resource.lastModified()) {
                result = true;
                fileModifyMap.put(resource, resource.lastModified());
                configuration.removeResourceLoaded(StringUtil.getFileName(resource.getURL().getPath()));
            }
        } catch (Exception e) {
            logger.error("找不到指定文件" + resource, e);
        }
        return result;
    }
}

