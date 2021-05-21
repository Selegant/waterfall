package org.jeecg.modules.datasources.input;

import lombok.Data;

@Data
public class TableColumnInput {
  /**
   * 数据库地址
   */
  private String jdbcUrl;

  /**
   * 用户名
   */
  private String username;

  /**
   * 密码
   */
  private String password;

  /**
   * 数据库名
   */
  private String database;

  /**
   * 表名
   */
  private String tableName;
}
