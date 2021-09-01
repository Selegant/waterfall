package org.jeecg.modules.datasources.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.datasources.model.WaterfallQualityModelWithJobInfo;

public interface WaterfallQualityModelWithJobInfoMapper extends BaseMapper<WaterfallQualityModelWithJobInfo> {
    int deleteByPrimaryKey(Integer id);

    int insert(WaterfallQualityModelWithJobInfo record);

    int insertSelective(WaterfallQualityModelWithJobInfo record);

    WaterfallQualityModelWithJobInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WaterfallQualityModelWithJobInfo record);

    int updateByPrimaryKey(WaterfallQualityModelWithJobInfo record);
}
