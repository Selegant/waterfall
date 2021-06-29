package org.jeecg.modules.datasources.model;

import java.util.Date;
import lombok.Data;

/**
    * 离线任务统计
    */
@Data
public class WaterfallJobLogReport {
    private Integer id;

    /**
    * 调度-时间
    */
    private Date triggerDay;

    /**
    * 运行中-日志数量
    */
    private Integer runningCount;

    /**
    * 执行成功-日志数量
    */
    private Integer sucCount;

    /**
    * 执行失败-日志数量
    */
    private Integer failCount;
}