package org.jeecg.modules.datasources.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
        return jobInfoMapper.insertSelective(task) > 0;
    }

    @Override
    public JSONObject getJobJson(OfflineTaskDTO input) {
        WaterfallDataSource original = waterfallDataSourceMapper.selectById(input.getOriginalId());
        WaterfallDataSource target = waterfallDataSourceMapper.selectById(input.getTargetId());
        return JobJsonUtil.assembleJobJson(original, target, input);
    }
}
