package org.jeecg.modules.datasources.model;

import java.util.Date;
import lombok.Data;

@Data
public class WaterfallJobRegistry {
    private Integer id;

    private String registryGroup;

    private String registryKey;

    private String registryValue;

    private Double cpuUsage;

    private Double memoryUsage;

    private Double loadAverage;

    private Date updateTime;
}
