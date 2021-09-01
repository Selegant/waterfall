package org.jeecg.modules.datasources.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.datasources.model.WaterfallQualityCheckPlan;

public interface WaterfallQualityCheckPlanMapper  extends BaseMapper<WaterfallQualityCheckPlan> {
    int deleteByPrimaryKey(Integer id);

    int insert(WaterfallQualityCheckPlan record);

    int insertSelective(WaterfallQualityCheckPlan record);

    WaterfallQualityCheckPlan selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WaterfallQualityCheckPlan record);

    int updateByPrimaryKey(WaterfallQualityCheckPlan record);
}
