package org.jeecg.modules.datasources.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class WaterfallDataSourceType {

  /**
   * 自增主键
   */
  @TableId(value = "id",type = IdType.AUTO)
  private Integer id;

  /**
   * 数据源类型名称
   */
  private String dataSourceTypeName;

  /**
   * 数据源驱动
   */
  private String dataSourceDriver;

  /**
   * 备注
   */
  private String remark;

  /**
   * 数据源默认驱动url
   */
  private String dataSourceDefaultJdbcUrl;
}