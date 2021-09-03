package org.jeecg.modules.warehouse.model;

import lombok.Data;

import java.util.Date;

/**
    * 质量规则表
    */
@Data
public class WaterfallQualityRule {
    private Integer id;

    /**
    * 质量规则名
    */
    private String ruleName;

    /**
    * 质量规则类型
    */
    private Integer ruleType;

    /**
    * 所属模型id
    */
    private Integer modelId;

    /**
    * 是否启用
    */
    private Boolean enableFlag;

    /**
    * 删除标志
    */
    private Boolean delFlag;

    /**
    * 创建时间
    */
    private Date createTime;

    /**
    * 删除时间
    */
    private Date updateTime;
}