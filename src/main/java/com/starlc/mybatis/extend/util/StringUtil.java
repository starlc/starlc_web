/** <a href="http://www.cpupk.com/decompiler">Eclipse Class Decompiler</a> plugin, Copyright (c) 2017 Chen Chao. */
/******************************************************************************
 * Copyright (C) 2014 ShenZhen ComTop Information Technology Co.,Ltd
 * All Rights Reserved.
 * 本软件为深圳康拓普开发研制。未经本公司正式书面同意，其他任何个人、团体不得使用、
 * 复制、修改或发布本软件.
 *****************************************************************************/
package com.starlc.mybatis.extend.util;

import org.springframework.util.StringUtils;


/**
 * 字符串工具类
 * 
 * @author 郑重
 * @since 1.0
 * @version 2014-01-05 郑重
 */
public class StringUtil {
	public static String getFileName(String fileName) {
		fileName = StringUtils.cleanPath(fileName);
		if (fileName.charAt(0) == '/') {
			fileName = fileName.substring(1, fileName.length());
		}
		return fileName;
	}

	public static String getNamespace(String id) {
		String strResult = "";
		if (!"".equals(id)) {
			String[] strNamespace = id.split("\\.");
			for (int i = 0; i < strNamespace.length - 1; i++) {
				strResult += strNamespace[i] + ".";
			}
			if ("".equals(strResult)) {
				strResult = id;
			} else {
				strResult = strResult.substring(0, strResult.length() - 1);
			}
		}
		return strResult;
	}
}
