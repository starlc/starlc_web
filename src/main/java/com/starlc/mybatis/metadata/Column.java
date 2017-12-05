/******************************************************************************
* Copyright (C) 2017 Starlc
*****************************************************************************/
 
package com.starlc.mybatis.metadata; 

/**
 * 类注释信息
 * @Description:   TODO(用一句话描述该文件做什么) 
 * 
 * @author   starlc
 * @since	 jdk1.7
 * @version  V-0.1
 * @Date     2017年12月5日 starlc      
 */
public class Column {
    private String name;
    private int type;

    public Column(String name, int type) {
      this.name = name;
      this.type = type;
    }

    public String getName() {
      return name;
    }

    public int getType() {
      return type;
    }

    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;

      final Column column = (Column) o;

      if (type != column.type) return false;
      if (name != null ? !name.equals(column.name) : column.name != null) return false;

      return true;
    }

    public int hashCode() {
      int result;
      result = (name != null ? name.hashCode() : 0);
      result = 29 * result + type;
      return result;
    }
}
 