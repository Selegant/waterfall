package org.jeecg.modules.datasources.mapper;

import org.jeecg.modules.datasources.model.WaterfallJobLogReport;

public interface WaterfallJobLogReportMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WaterfallJobLogReport record);

    int insertSelective(WaterfallJobLogReport record);

    WaterfallJobLogReport selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WaterfallJobLogReport record);

    int updateByPrimaryKey(WaterfallJobLogReport record);

    int update(WaterfallJobLogReport xxlJobLogReport);

    int save(WaterfallJobLogReport xxlJobLogReport);
}
