package org.jeecg.modules.datasources.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.datasources.model.WaterfallQualityRule;

public interface WaterfallQualityRuleMapper extends BaseMapper<WaterfallQualityRule> {
    int deleteByPrimaryKey(Integer id);

    int insert(WaterfallQualityRule record);

    int insertSelective(WaterfallQualityRule record);

    WaterfallQualityRule selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WaterfallQualityRule record);

    int updateByPrimaryKey(WaterfallQualityRule record);
}
