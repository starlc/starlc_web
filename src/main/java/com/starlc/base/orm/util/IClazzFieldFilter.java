/******************************************************************************
 * Copyright (C) 2017 Starlc
 *****************************************************************************/

package com.starlc.base.orm.util;

import java.lang.reflect.Field;

/**
 * 类注释信息
 * 
 * @Description: TODO(用一句话描述该文件做什么)
 * 
 * @author starlc
 * @since jdk1.7
 * @version V-0.1
 * @Date 2017年12月5日 starlc
 */
public interface IClazzFieldFilter {
    
    /**
     * FIXME 方法注释信息(此标记由Eclipse自动生成,请填写注释信息删除此标记)
     *
     * <pre>
     * 
     * </pre>
     * 
     * @param paramClass xx
     * @param paramField xx
     * @return
     */
    public boolean isFilter(Class<?> paramClass, Field paramField);
    
}
