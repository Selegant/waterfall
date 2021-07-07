package org.jeecg.modules.datasources.dto;

import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author selegant
 */
@NoArgsConstructor
@Data
public class OfflineTaskDTO {


    private String name;
    private String desc;
    private Integer originalId;
    private String originalTable;
    private Integer collectionType;
    private Integer targetId;
    private String targetTable;
    private List<MappingColumnsBean> mappingColumns;
    private String incColumn;

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
    }
}
