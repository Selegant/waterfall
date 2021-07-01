package org.jeecg.modules.datasources.model;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
    * 数据模型字段
    */
@Data
public class WaterfallModelField {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
    * 模型id
    */
    private Integer modelId;

    /**
    * 字段名称
    */
    private String fieldName;

    /**
    * 字段类型id
    */
    private Integer fieldTypeId;

    /**
    * 描述
    */
    private String remark;

    /**
    * 元数据id
    */
    private Integer metadataId;

    /**
    * 是否为主键
    */
    private Boolean primarykeyFlag;

    /**
    * 是否为空
    */
    private Boolean emptyFlag;

    /**
    * 字段长度
    */
    private Integer length;

    /**
    * 创建时间
    */
    private Date createTime;

    /**
    * 更新时间
    */
    private Date updateTime;

    /**
    * 字段序号
    */
    private Integer fieldSort;

    /**
     * 删除标识
     */
    private Boolean delFlag;
}