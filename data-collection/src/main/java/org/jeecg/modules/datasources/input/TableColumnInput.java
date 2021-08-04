package org.jeecg.modules.datasources.input;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
