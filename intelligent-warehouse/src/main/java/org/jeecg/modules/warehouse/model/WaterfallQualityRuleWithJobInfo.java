package org.jeecg.modules.warehouse.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
    * 检验计划和质量规则中间表
    */
@Data
public class WaterfallQualityRuleWithJobInfo {
    @TableId(value = "id", type = IdType.AUTO)
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