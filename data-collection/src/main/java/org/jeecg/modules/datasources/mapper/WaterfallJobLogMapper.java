package org.jeecg.modules.datasources.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.datasources.model.WaterfallDataSourceAmount;
import org.jeecg.modules.datasources.model.WaterfallJobLog;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface WaterfallJobLogMapper extends BaseMapper<WaterfallJobLog> {

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

    List<WaterfallJobLog> pageList(@Param("offset") int offset,
            @Param("pagesize") int pagesize,
            @Param("jobGroup") int jobGroup,
            @Param("jobId") int jobId,
            @Param("triggerTimeStart") Date triggerTimeStart,
            @Param("triggerTimeEnd") Date triggerTimeEnd,
            @Param("logStatus") int logStatus);

    int pageListCount(@Param("offset") int offset,
            @Param("pagesize") int pagesize,
            @Param("jobGroup") int jobGroup,
            @Param("jobId") int jobId,
            @Param("triggerTimeStart") Date triggerTimeStart,
            @Param("triggerTimeEnd") Date triggerTimeEnd,
            @Param("logStatus") int logStatus);

    IPage<WaterfallJobLog> wapperPageList(IPage<WaterfallJobLog> page,@Param(Constants.WRAPPER) WaterfallJobLog log);
}
