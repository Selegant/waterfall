package org.jeecg.modules.datasources.mapper;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.datasources.model.WaterfallJobGroup;

import java.util.List;

public interface WaterfallJobGroupMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WaterfallJobGroup record);

    int insertSelective(WaterfallJobGroup record);

    WaterfallJobGroup selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WaterfallJobGroup record);

    int updateByPrimaryKey(WaterfallJobGroup record);

    WaterfallJobGroup load(@Param("id") int id);

    List<WaterfallJobGroup> findByAddressType(@Param("addressType") int addressType);

    int update(WaterfallJobGroup jobGroup);
}
