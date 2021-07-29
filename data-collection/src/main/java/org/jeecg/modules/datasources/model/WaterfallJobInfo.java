package org.jeecg.modules.datasources.model;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * 离线任务表
 */
@Data
public class WaterfallJobInfo {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
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
    private String taskExecuteJson;

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

    /**
     * 报警邮件
     */
    private String alarmEmail;

    /**
     * 任务执行超时时间
     */
    private Integer executorTimeout;

    /**
     * 失败重试次数
     */
    private Integer executorFailRetryCount;

    /**
     * 调度状态：0-停止，1-运行
     */
    private Integer triggerStatus;

    /**
     * 上次调度时间
     */
    private Long triggerLastTime;

    /**
     * 下次调度时间
     */
    private Long triggerNextTime;

    /**
     * 增量初始时间
     */
    private Date incStartTime;

    /**
     * jvm参数
     */
    private String jvmParam;

    /**
     * 动态参数
     */
    private String replaceParam;

    /**
     * 执行器主键ID
     */
    private Integer jobGroup;

    /**
     * 执行器路由策略
     */
    private String executorRouteStrategy;

    /**
     * 阻塞处理策略
     */
    private String executorBlockStrategy;

    /**
     * 执行器任务handler
     */
    private String executorHandler;

    /**
     * 执行器任务参数
     */
    private String executorParam;

    /**
     * GLUE类型
     */
    private String glueType;

    /**
     * GLUE源代码
     */
    private String glueSource;

    /**
     * GLUE备注
     */
    private String glueRemark;

    /**
     * GLUE更新时间
     */
    private Date glueUpdatetime;

    /**
     * 最近一次执行状态
     */
    private Integer lastHandleCode;

    /**
     * 所属项目ID
     */
    private Integer projectId;

    /**
     * 增量类型
     */
    private Integer incrementType;

    /**
     * 子任务ID,多个逗号分隔
     */
    private String childJobId;

    /**
     * 增量初始ID
     */
    private String incStartId;

    /**
     * 增量字段
     */
    private String incColumn;
}
