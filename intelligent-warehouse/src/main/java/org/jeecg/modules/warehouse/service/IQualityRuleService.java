package org.jeecg.modules.warehouse.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.warehouse.model.WaterfallQualityRule;
import org.jeecg.modules.warehouse.model.WaterfallQualityRuleFieldType;
import org.jeecg.modules.warehouse.dto.WaterfallQualityRuleDTO;

import java.util.List;

/**
 * @Author liansongye
 * @create 2021/9/2 5:37 下午
 */
public interface IQualityRuleService {


    IPage<WaterfallQualityRule> queryRulePage(Page<WaterfallQualityRule> page, WaterfallQualityRule rule);

    void saveRule(WaterfallQualityRuleDTO rule);

    void updateRule(WaterfallQualityRuleDTO rule);

    void deleteRule(Integer id);

    List<WaterfallQualityRuleFieldType> queryRuleFieldTypeList();

    WaterfallQualityRuleDTO ruleInfo(Integer id);
}
