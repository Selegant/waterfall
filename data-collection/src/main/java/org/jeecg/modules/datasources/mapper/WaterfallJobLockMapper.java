package org.jeecg.modules.datasources.mapper;

import org.jeecg.modules.datasources.model.WaterfallJobLock;

public interface WaterfallJobLockMapper {
    int deleteByPrimaryKey(String lockName);

    int insert(WaterfallJobLock record);

    int insertSelective(WaterfallJobLock record);
}