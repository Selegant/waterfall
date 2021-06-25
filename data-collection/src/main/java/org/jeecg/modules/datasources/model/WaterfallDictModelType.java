package org.jeecg.modules.datasources.model;

import java.util.Date;
import lombok.Data;

/**
    * 数据模型类目
    */
@Data
public class WaterfallDictModelType {
    private Integer id;

    /**
    * 数据模型类目代码
    */
    private Integer modelTypecode;

    /**
    * 数据模型类目名
    */
    private String modelTypename;

    /**
    * 创建时间
    */
    private Date createTime;

    /**
    * 更新时间
    */
    private Date updateTime;
}