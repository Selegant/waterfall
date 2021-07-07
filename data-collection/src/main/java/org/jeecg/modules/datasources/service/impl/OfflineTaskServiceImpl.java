package org.jeecg.modules.datasources.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.text.ParseException;
import org.jeecg.datax.enums.ExecutorBlockStrategyEnum;
import org.jeecg.modules.datasources.core.cron.CronExpression;
import org.jeecg.modules.datasources.core.route.ExecutorRouteStrategyEnum;
import org.jeecg.modules.datasources.core.util.I18nUtil;
import org.jeecg.modules.datasources.dto.OfflineTaskDTO;
import org.jeecg.modules.datasources.mapper.WaterfallDataSourceMapper;
import org.jeecg.modules.datasources.mapper.WaterfallJobInfoMapper;
import org.jeecg.modules.datasources.model.WaterfallDataSource;
import org.jeecg.modules.datasources.model.WaterfallJobInfo;
import org.jeecg.modules.datasources.service.IOfflineTaskService;
import org.jeecg.modules.datasources.util.JobJsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;

/**
 * @author selegant
 */
@Service
public class OfflineTaskServiceImpl extends
        ServiceImpl<WaterfallJobInfoMapper, WaterfallJobInfo> implements
        IOfflineTaskService {

    @Autowired
    private WaterfallJobInfoMapper jobInfoMapper;

    @Autowired
    private WaterfallDataSourceMapper waterfallDataSourceMapper;

    private static final Integer GROUP_ID = 1;

    private static final Integer PROJECT_ID = 1;

    /**
     * 保存离线任务
     *
     * @param offlineTask
     * @return
     */
    @Override
    public Boolean saveOfflineTask(OfflineTaskDTO offlineTask) {
        WaterfallJobInfo task = new WaterfallJobInfo();
        task.setTaskName(offlineTask.getName());
        task.setTaskDesc(offlineTask.getDesc());
        task.setMappingColumns(JSONObject.toJSONString(offlineTask.getMappingColumns()));
        task.setCollectionType(offlineTask.getCollectionType());
        task.setOriginalId(offlineTask.getOriginalId());
        task.setOriginalTable(offlineTask.getOriginalTable());
        task.setTargetId(offlineTask.getTargetId());
        task.setTargetTable(offlineTask.getTargetTable());
        task.setCreateTime(new Date());
        task.setUpdateTime(new Date());

        task.setTaskExecuteJson(getJobJson(offlineTask));
        task.setTaskExecuteTime(offlineTask.getTaskExecuteTime());
        if (!CronExpression.isValidExpression(offlineTask.getTaskCorn())) {
            throw new RuntimeException("CORN表达式不合法");
        }
        try {
            CronExpression cronExpression = new CronExpression(offlineTask.getTaskCorn());
            Date lastTime = new Date();
            lastTime = cronExpression.getNextValidTimeAfter(lastTime);
            task.setTriggerNextTime(lastTime.toInstant().getEpochSecond());
        } catch (ParseException e) {
            throw new RuntimeException(e.getMessage());
        }
        task.setIncStartId(offlineTask.getIncStartId());
        task.setTaskCorn(offlineTask.getTaskCorn());
        task.setAlarmEmail(offlineTask.getAlarmEmail());
        task.setExecutorTimeout(offlineTask.getExecutorTimeout());
        task.setExecutorFailRetryCount(offlineTask.getExecutorFailRetryCount());
        task.setJobGroup(GROUP_ID);
        task.setIncStartTime(offlineTask.getIncStartTime());
        task.setExecutorRouteStrategy(ExecutorRouteStrategyEnum.FIRST.getTitle());
        task.setExecutorBlockStrategy(ExecutorBlockStrategyEnum.SERIAL_EXECUTION.getTitle());
        task.setProjectId(PROJECT_ID);
        return jobInfoMapper.insertSelective(task) > 0;
    }

    @Override
    public String getJobJson(OfflineTaskDTO input) {
        WaterfallDataSource original = waterfallDataSourceMapper.selectById(input.getOriginalId());
        WaterfallDataSource target = waterfallDataSourceMapper.selectById(input.getTargetId());
        if (original == null || target == null) {
            throw new RuntimeException("源数据表或目标表不存在");
        }
        return JobJsonUtil.assembleJobJson(original, target, input).toString();
    }

    @Override
    public void removeTask(Integer id) {
        WaterfallJobInfo jobInfo = jobInfoMapper.loadById(id);
        if (jobInfo == null) {
            return;
        }
        jobInfoMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void stop(Integer id) {
        WaterfallJobInfo jobInfo = jobInfoMapper.loadById(id);

        jobInfo.setTriggerStatus(0);
        jobInfo.setTriggerLastTime(0L);
        jobInfo.setTriggerNextTime(0L);

        jobInfo.setUpdateTime(new Date());
        jobInfoMapper.updateByPrimaryKey(jobInfo);
    }
}
