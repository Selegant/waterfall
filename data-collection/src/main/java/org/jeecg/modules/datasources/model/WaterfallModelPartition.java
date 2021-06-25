package org.jeecg.modules.datasources.model;

import java.util.Date;
import lombok.Data;

/**
    * 模型分区字段
    */
@Data
public class WaterfallModelPartition {
    private Integer id;

    /**
    * 模型id
    */
    private Integer modelId;

    /**
    * 分区名
    */
    private String partitionName;

    /**
    * 分区类型id
    */
    private Integer partitionTypeId;

    private String remark;

    /**
    * 元数据id
    */
    private Integer metadataId;

    /**
    * 创建时间
    */
    private Date createTime;

    /**
    * 更新时间
    */
    private Date updateTime;
}