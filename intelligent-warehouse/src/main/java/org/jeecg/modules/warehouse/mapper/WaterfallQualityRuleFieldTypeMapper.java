package org.jeecg.modules.warehouse.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.warehouse.model.WaterfallQualityRuleFieldType;

public interface WaterfallQualityRuleFieldTypeMapper extends BaseMapper<WaterfallQualityRuleFieldType> {
    int deleteByPrimaryKey(Integer id);

    int insert(WaterfallQualityRuleFieldType record);

    int insertSelective(WaterfallQualityRuleFieldType record);

    WaterfallQualityRuleFieldType selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WaterfallQualityRuleFieldType record);

    int updateByPrimaryKey(WaterfallQualityRuleFieldType record);
}
