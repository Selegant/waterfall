package org.jeecg.modules.datasources.mapper;

import org.jeecg.modules.datasources.model.WaterfallDataSourceAmount;

public interface WaterfallDataSourceAmountMapper {

    int insert(WaterfallDataSourceAmount record);

    int insertSelective(WaterfallDataSourceAmount record);
}