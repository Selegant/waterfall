package org.jeecg.modules.datasources.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.datasources.model.WaterfallQualityCheckPlanWithRule;

public interface WaterfallQualityCheckPlanWithRuleMapper extends BaseMapper<WaterfallQualityCheckPlanWithRule> {
    int deleteByPrimaryKey(Integer id);

    int insert(WaterfallQualityCheckPlanWithRule record);

    int insertSelective(WaterfallQualityCheckPlanWithRule record);

    WaterfallQualityCheckPlanWithRule selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WaterfallQualityCheckPlanWithRule record);

    int updateByPrimaryKey(WaterfallQualityCheckPlanWithRule record);
}
