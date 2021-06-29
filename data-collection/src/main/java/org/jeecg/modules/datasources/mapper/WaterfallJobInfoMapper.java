package org.jeecg.modules.datasources.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.datasources.model.WaterfallJobInfo;

public interface WaterfallJobInfoMapper extends BaseMapper<WaterfallJobInfo> {
    int deleteByPrimaryKey(Integer id);

    int insert(WaterfallJobInfo record);

    int insertSelective(WaterfallJobInfo record);

    WaterfallJobInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WaterfallJobInfo record);

    int updateByPrimaryKey(WaterfallJobInfo record);
}
