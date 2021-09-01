package org.jeecg.modules.datasources.model;

import java.util.Date;
import lombok.Data;

/**
    * 校验计划表
    */
@Data
public class WaterfallQualityCheckPlan {
    private Integer id;

    /**
    * 计划名称
    */
    private String planName;

    /**
    * 关联模型id
    */
    private Integer modelId;

    /**
    * 执行周期
    */
    private String planCycle;

    /**
    * 运行状态
    */
    private Boolean runFlag;

    /**
    * 删除标志
    */
    private Boolean delFlag;

    /**
    * 创建时间
    */
    private Date createTime;

    /**
    * 修改时间
    */
    private Date updateTime;
}