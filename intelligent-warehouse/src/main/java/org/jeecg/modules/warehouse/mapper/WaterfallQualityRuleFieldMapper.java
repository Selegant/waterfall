package org.jeecg.modules.warehouse.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.warehouse.model.WaterfallQualityRuleField;

import java.util.List;

public interface WaterfallQualityRuleFieldMapper extends BaseMapper<WaterfallQualityRuleField> {
    int deleteByPrimaryKey(Integer id);

    int insert(WaterfallQualityRuleField record);

    int insertSelective(WaterfallQualityRuleField record);

    WaterfallQualityRuleField selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WaterfallQualityRuleField record);

    int updateByPrimaryKey(WaterfallQualityRuleField record);

    List<WaterfallQualityRuleField> getInfo(@Param(value = "jobId") Integer jobId);
}
