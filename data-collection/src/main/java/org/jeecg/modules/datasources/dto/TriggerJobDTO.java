package org.jeecg.modules.datasources.dto;

import lombok.Data;

/**
 * 用于启动任务接收的实体
 *
 * @author jingwk
 * @ClassName TriggerJobDto
 * @Version 1.0
 * @since 2019/12/01 16:12
 */
@Data
public class TriggerJobDTO {

    private String executorParam;

    private int jobId;
}
