package org.jeecg.modules.datasources.model;

import lombok.Data;

/**
    * 执行器表
    */
@Data
public class WaterfallJobGroup {
    private Integer id;

    /**
    * 执行器AppName
    */
    private String appName;

    /**
    * 执行器名称
    */
    private String title;

    /**
    * 排序
    */
    private Integer order;

    /**
    * 执行器地址类型：0=自动注册、1=手动录入
    */
    private Byte addressType;

    /**
    * 执行器地址列表，多地址逗号分隔
    */
    private String addressList;
}