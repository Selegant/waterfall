package org.jeecg.modules.datasources.model;

import java.util.Date;
import lombok.Data;

/**
    * 质量规则字段表
    */
@Data
public class WaterfallQualityRuleField {
    private Integer id;

    /**
    * 质量规则id
    */
    private Integer ruleId;

    /**
    * 规则字段名
    */
    private String fieldName;

    /**
    * 规则类别代码
    */
    private Integer ruleFieldTypeCode;

    /**
    * 校验规则
    */
    private String regularExpression;

    /**
    * 比较方式
    */
    private String compareMode;

    /**
    * 期望值
    */
    private String expectedValue;

    /**
    * 是否判空
    */
    private Boolean emptyFlag;

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