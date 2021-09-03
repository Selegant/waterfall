package org.jeecg.modules.warehouse.dto;

import lombok.Data;
import org.jeecg.modules.warehouse.model.WaterfallQualityRule;

import java.util.Date;
import java.util.List;

/**
 * 校验计划实体
 * @Author liansongye
 * @create 2021/9/2 10:35 上午
 */
@Data
public class WaterfallQualityCheckPlanDTO {
    private Integer jobInfoId;
    private Integer modelId;
    private String taskName;
    private String dataSourcetype;
    private String dbName;
    private String taskCorn;
    private Integer triggerStatus;
    private Date createTime;

    private List<WaterfallQualityRule> qualityRules;

}
