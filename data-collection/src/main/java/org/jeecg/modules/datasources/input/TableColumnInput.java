package org.jeecg.modules.datasources.input;

import lombok.Data;

@Data
public class TableColumnInput {

  /**
   * 数据源id
   */
  private Integer id;

  /**
   * 表名
   */
  private String tableName;
}
