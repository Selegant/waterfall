package org.jeecg.modules.datasources.model;

import java.util.Date;
import lombok.Data;

/**
    * 任务注册表
    */
@Data
public class WaterfallJobRegistry {
    private Integer id;

    private String registryGroup;

    private String registryKey;

    private String registryValue;

    private Date updateTime;
}