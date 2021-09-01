package org.jeecg.modules.datasources.model;

import java.util.Date;
import lombok.Data;

/**
    * 模型和计划中间表
    */
@Data
public class WaterfallQualityModelWithJobInfo {
    private Integer id;

    /**
    * 模型id
    */
    private Integer modelId;

    /**
    * 计划id
    */
    private Integer jobInfoId;

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