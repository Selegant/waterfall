package org.jeecg.modules.datasources.model;

import java.util.Date;
import lombok.Data;

@Data
public class WaterfallOfflineTask {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 名称
     */
    private String taskName;

    /**
     * 描述
     */
    private String taskDesc;

    /**
     * 任务执行参数
     */
    private String taskExecuteParams;

    /**
     * 任务执行调度时间
     */
    private String taskCorn;

    /**
     * 任务执行时间
     */
    private Date taskExecuteTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 字段映射关系
     */
    private String mappingColumns;

    /**
     * 采集类型 全量:1 增量:2
     */
    private Integer collectionType;

    /**
     * 源数据源ID
     */
    private Integer originalId;

    /**
     * 源数据表
     */
    private String originalTable;

    /**
     * 目标源ID
     */
    private Integer targetId;

    /**
     * 目标端表
     */
    private String targetTable;
}