package org.jeecg.modules.datasources.model;

import java.util.Date;
import lombok.Data;

/**
    * 质量规则字段类型表
    */
@Data
public class WaterfallQualityRuleFieldType {
    private Integer id;

    /**
    * 规则字段类型代码
    */
    private String typeCode;

    /**
    * 规则字段类别名
    */
    private String typeName;

    /**
    * 是否删除
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