package org.jeecg.modules.datasources.mapper;

import org.jeecg.modules.datasources.model.WaterfallJobLog;

public interface WaterfallJobLogMapper {
    int deleteByPrimaryKey(Long id);

    int insert(WaterfallJobLog record);

    int insertSelective(WaterfallJobLog record);

    WaterfallJobLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(WaterfallJobLog record);

    int updateByPrimaryKey(WaterfallJobLog record);
}