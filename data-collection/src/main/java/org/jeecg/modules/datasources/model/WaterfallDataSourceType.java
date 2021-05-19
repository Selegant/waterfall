package org.jeecg.modules.datasources.model;

import lombok.Data;

@Data
public class WaterfallDataSourceType {
    /**
    * 自增主键
    */
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
}