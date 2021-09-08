package org.jeecg.modules.warehouse.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

/**
    * 质量规则字段类型表
    */
@Data
public class WaterfallQualityRuleFieldType {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
    * 规则字段类型代码
    */
    private Integer typeCode;

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