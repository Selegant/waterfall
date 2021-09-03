package org.jeecg.modules.warehouse.model;

import lombok.Data;

/**
    * 检验计划和质量规则中间表
    */
@Data
public class WaterfallQualityRuleWithJobInfo {
    private Integer id;

    /**
    * 检验计划id
    */
    private Integer jobInfoId;

    /**
    * 质量规则id
    */
    private Integer qualityRuleId;

    /**
    * 删除标志
    */
    private Boolean delFlag;
}