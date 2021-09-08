package org.jeecg.modules.warehouse.dto;

import lombok.Data;

import java.util.Date;

/**
 * 校验计划日志列表返回类
 * @Author liansongye
 * @create 2021/9/8 10:22 上午
 */
@Data
public class CheckPlanLogDTO {
    private Integer id;
    private String modelName;
    private Date taskExecuteTime;
    private Date startTime;
    private Date endTime;
    private Integer handleCode;
}
