package org.jeecg.modules.datasources.model;

import java.util.Date;
import lombok.Data;

/**
    * 数据模型
    */
@Data
public class WaterfallModel {
    private Integer id;

    /**
    * 层级id
    */
    private Integer folderId;

    /**
    * 数据模型名称
    */
    private String modelName;

    /**
    * 类目代码
    */
    private Integer modelTypeCode;

    /**
    * 发布状态代码
    */
    private Integer modelStatusCode;

    /**
    * 发布状态名
    */
    private String modelStatusName;

    /**
    * 创建方式代码
    */
    private Integer exportTypeCode;

    /**
    * 创建方式名
    */
    private String exportTypeName;

    /**
    * 模型表述
    */
    private String ramark;

    /**
    * 创建时间
    */
    private Date createTime;

    /**
    * 更新时间
    */
    private Date updateTime;
}