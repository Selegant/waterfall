package org.jeecg.modules.datasources.mapper;

import org.jeecg.modules.datasources.model.WaterfallJobRegistry;

public interface WaterfallJobRegistryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WaterfallJobRegistry record);

    int insertSelective(WaterfallJobRegistry record);

    WaterfallJobRegistry selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WaterfallJobRegistry record);

    int updateByPrimaryKey(WaterfallJobRegistry record);
}