package org.jeecg.modules.datasources.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
public class WaterfallJobLog {
    private Long id;

    /**
     * 执行器主键ID
     */
    private Integer jobGroup;

    /**
     * 任务，主键ID
     */
    private Integer jobId;

    private String jobDesc;

    /**
     * 执行器地址，本次执行的地址
     */
    private String executorAddress;

    /**
     * 执行器任务handler
     */
    private String executorHandler;

    /**
     * 执行器任务参数
     */
    private String executorParam;

    /**
     * 执行器任务分片参数，格式如 1/2
     */
    private String executorShardingParam;

    /**
     * 失败重试次数
     */
    private Integer executorFailRetryCount;

    /**
     * 调度-时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date triggerTime;

    /**
     * 调度-结果
     */
    private int triggerCode;

    /**
     * 调度-日志
     */
    private String triggerMsg;

    /**
     * 执行-时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date handleTime;

    /**
     * 执行-状态
     */
    private int handleCode;

    /**
     * 执行-日志
     */
    private String handleMsg;

    /**
     * 告警状态：0-默认、1-无需告警、2-告警成功、3-告警失败
     */
    private Byte alarmStatus;

    /**
     * datax进程Id
     */
    private String processId;

    /**
     * 增量表max id
     */
    private Long maxId;

    /**
     * 任务名称
     */
    private String taskName;

    /**
     * 日志状态
     */
    private String logStatus;

    /**
     * 模型id
     */
    private Integer modelId;

    /**
     * 模型名
     */
    private String modelName;

    /**
     * 检查结果
     */
    private String checkResult;
}
