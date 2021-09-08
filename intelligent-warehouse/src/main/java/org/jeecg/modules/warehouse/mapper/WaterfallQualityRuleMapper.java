package org.jeecg.modules.warehouse.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.warehouse.model.WaterfallQualityRule;

import java.util.List;

public interface WaterfallQualityRuleMapper extends BaseMapper<WaterfallQualityRule> {
    int deleteByPrimaryKey(Integer id);

    int insert(WaterfallQualityRule record);

    int insertSelective(WaterfallQualityRule record);

    WaterfallQualityRule selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WaterfallQualityRule record);

    int updateByPrimaryKey(WaterfallQualityRule record);

    List<WaterfallQualityRule> getListWithJob(@Param(value = "jobId") Integer jobId);
}
