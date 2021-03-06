package org.jeecg.modules.warehouse.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.warehouse.model.WaterfallQualityRuleWithJobInfo;

public interface WaterfallQualityRuleWithJobInfoMapper extends BaseMapper<WaterfallQualityRuleWithJobInfo> {
    int deleteByPrimaryKey(Integer id);

    int insert(WaterfallQualityRuleWithJobInfo record);

    int insertSelective(WaterfallQualityRuleWithJobInfo record);

    WaterfallQualityRuleWithJobInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WaterfallQualityRuleWithJobInfo record);

    int updateByPrimaryKey(WaterfallQualityRuleWithJobInfo record);
}
