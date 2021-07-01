package org.jeecg.modules.datasources.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.datasources.model.WaterfallJobInfo;

import java.util.Date;
import java.util.List;

public interface WaterfallJobInfoMapper extends BaseMapper<WaterfallJobInfo> {
    int deleteByPrimaryKey(Integer id);

    int insert(WaterfallJobInfo record);

    int insertSelective(WaterfallJobInfo record);

    WaterfallJobInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WaterfallJobInfo record);

    int updateByPrimaryKey(WaterfallJobInfo record);

    List<WaterfallJobInfo> scheduleJobQuery(@Param("maxNextTime") long maxNextTime, @Param("pagesize") int pagesize);

    int scheduleUpdate(WaterfallJobInfo xxlJobInfo);

    WaterfallJobInfo loadById(@Param("id") int id);

    int updateLastHandleCode(@Param("id") int id, @Param("lastHandleCode") int lastHandleCode);

    void incrementIdUpdate(@Param("id") int id, @Param("incStartId") Long incStartId);

    int incrementTimeUpdate(@Param("id") int id, @Param("incStartTime") Date incStartTime);
}
