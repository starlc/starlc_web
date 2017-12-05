/******************************************************************************
* Copyright (C) 2017 Starlc
*****************************************************************************/
 
package com.starlc.mybatis.metadata; 

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * 类注释信息
 * @Description:   TODO(用一句话描述该文件做什么) 
 * 
 * @author   starlc
 * @since	 jdk1.7
 * @version  V-0.1
 * @Date     2017年12月5日 starlc      
 */
public class Database {
    private final String catalog;
    private final String schema;

    private final Map<String, Table> tables = new HashMap<String, Table>();

    public Database(String catalog, String schema) {
      this.catalog = catalog;
      this.schema = schema;
    }

    public String getCatalog() {
      return catalog;
    }

    public String getSchema() {
      return schema;
    }

    public void addTable(Table table) {
      tables.put(table.getName().toUpperCase(Locale.ENGLISH), table);
    }

    public Table getTable(String name) {
      return tables.get(name.toUpperCase(Locale.ENGLISH));
    }

    public String[] getTableNames() {
      return tables.keySet().toArray(new String[tables.size()]);
    }

    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;

      final Database database = (Database) o;

      if (catalog != null ? !catalog.equals(database.catalog) : database.catalog != null) return false;
      if (schema != null ? !schema.equals(database.schema) : database.schema != null) return false;

      return true;
    }

    public int hashCode() {
      int result;
      result = (catalog != null ? catalog.hashCode() : 0);
      result = 29 * result + (schema != null ? schema.hashCode() : 0);
      return result;
    }

}
 