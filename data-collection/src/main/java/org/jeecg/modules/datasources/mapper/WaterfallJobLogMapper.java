package org.jeecg.modules.datasources.mapper;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.datasources.model.WaterfallJobLog;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface WaterfallJobLogMapper {
    int deleteByPrimaryKey(Long id);

    int insert(WaterfallJobLog record);

    int insertSelective(WaterfallJobLog record);

    WaterfallJobLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(WaterfallJobLog record);

    int updateByPrimaryKey(WaterfallJobLog record);

    List<Long> findFailJobLogIds(@Param("pagesize") int pagesize);

    int updateAlarmStatus(@Param("logId") long logId,
                          @Param("oldAlarmStatus") int oldAlarmStatus,
                          @Param("newAlarmStatus") int newAlarmStatus);

    WaterfallJobLog load(@Param("id") long id);

    int updateTriggerInfo(WaterfallJobLog jobLog);

    Map<String, Object> findLogReport(@Param("from") Date from,
                                      @Param("to") Date to);

    List<Long> findClearLogIds(@Param("jobGroup") int jobGroup,
                               @Param("jobId") int jobId,
                               @Param("clearBeforeTime") Date clearBeforeTime,
                               @Param("clearBeforeNum") int clearBeforeNum,
                               @Param("pagesize") int pagesize);

    int clearLog(@Param("logIds") List<Long> logIds);

    int updateProcessId(@Param("id") long id,
                        @Param("processId") String processId);

    int updateHandleInfo(WaterfallJobLog jobLog);

    long save(WaterfallJobLog jobLog);
}
