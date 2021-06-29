package org.jeecg.modules.datasources.mapper;

import org.jeecg.modules.datasources.model.WaterfallJobGroup;

public interface WaterfallJobGroupMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WaterfallJobGroup record);

    int insertSelective(WaterfallJobGroup record);

    WaterfallJobGroup selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WaterfallJobGroup record);

    int updateByPrimaryKey(WaterfallJobGroup record);
}