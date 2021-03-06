package org.jeecg.modules.datasources.dto;

import com.alibaba.fastjson.JSONObject;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.List;

/**
 * @author selegant
 */
@NoArgsConstructor
@Data
public class OfflineTaskDTO {

    private Integer id;
    private String name;
    private String desc;
    private Integer originalId;
    private String originalTable;
    private Integer collectionType;
    private Integer targetId;
    private String targetTable;
    private List<MappingColumnsBean> mappingColumns;
    private String incColumn;

    private String defaultFS;

    private String path;

    private JSONObject hadoopConfig;

    /**
     * 任务执行调度时间
     */
    private String taskCorn;

    /**
     * 任务执行时间
     */
    private Date taskExecuteTime;

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
     * 增量初始时间
     */
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date incStartTime;


    /**
     * 动态参数
     */
    private String replaceParam;


    /**
     * 增量类型
     */
    private Integer incrementType;

    /**
     * 增量初始ID
     */
    private String incStartId;

    @NoArgsConstructor
    @Data
    public static class MappingColumnsBean {

        private String originalColumn;

        private String targetColumn;

        private Integer originalColumnIndex;

        private Integer targetColumnIndex;

        private String targetColumnType;
    }
}
