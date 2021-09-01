package org.jeecg.modules.datasources.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.datasources.model.WaterfallQualityRuleField;

public interface WaterfallQualityRuleFieldMapper extends BaseMapper<WaterfallQualityRuleField> {
    int deleteByPrimaryKey(Integer id);

    int insert(WaterfallQualityRuleField record);

    int insertSelective(WaterfallQualityRuleField record);

    WaterfallQualityRuleField selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WaterfallQualityRuleField record);

    int updateByPrimaryKey(WaterfallQualityRuleField record);
}
