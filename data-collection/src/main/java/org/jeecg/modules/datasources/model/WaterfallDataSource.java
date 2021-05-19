package org.jeecg.modules.datasources.model;

import lombok.Data;

@Data
public class WaterfallDataSource {
    /**
    * 自增主键
    */
    private Integer id;

    /**
    * 数据源名称
    */
    private String dataSourceName;

    /**
    * 数据库地址
    */
    private String jdbcurl;

    /**
    * 用户名
    */
    private String username;

    /**
    * 密码
    */
    private String password;

    /**
    * 备注
    */
    private String remark;

    /**
    * 数据库类型
    */
    private Integer typeId;
}