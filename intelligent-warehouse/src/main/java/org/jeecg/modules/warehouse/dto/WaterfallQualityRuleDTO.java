package org.jeecg.modules.warehouse.dto;

import lombok.Data;
import org.jeecg.modules.warehouse.model.WaterfallQualityRule;
import org.jeecg.modules.warehouse.model.WaterfallQualityRuleField;

import java.util.Date;
import java.util.List;

/**
 * 质量规则实体
 * @Author liansongye
 * @create 2021/9/2 9:17 上午
 */
@Data
public class WaterfallQualityRuleDTO {
    public static Integer RULE_PRIVATE = 1;
    public static Integer RULE_PUBLIC = 2;


    private Integer id;
    private String ruleName;
    private Integer ruleType;
    private Integer modelId;
    private Boolean enableFlag;
    private Boolean delFlag;
    private Date createTime;
    private Date updateTime;

    List<WaterfallQualityRuleField> ruleFields;

    public WaterfallQualityRule toEntity() {
        WaterfallQualityRule rule = new WaterfallQualityRule();
        rule.setId(id);
        rule.setRuleName(ruleName);
        rule.setRuleType(ruleType);
        rule.setModelId(modelId);
        rule.setEnableFlag(enableFlag);
        rule.setDelFlag(delFlag);
        rule.setCreateTime(createTime);
        rule.setUpdateTime(updateTime);
        return rule;
    }

    public void toDto(WaterfallQualityRule rule) {
        this.id = rule.getId();
        this.ruleName = rule.getRuleName();
        this.ruleType = rule.getRuleType();
        this.modelId = rule.getModelId();
        this.enableFlag = rule.getEnableFlag();
        this.delFlag = rule.getDelFlag();
        this.createTime = rule.getCreateTime();
        this.updateTime = rule.getUpdateTime();
    }

}
