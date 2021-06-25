package org.jeecg.modules.datasources.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.datasources.dto.OfflineTaskDTO;
import org.jeecg.modules.datasources.mapper.WaterfallDataSourceMapper;
import org.jeecg.modules.datasources.mapper.WaterfallOfflineTaskMapper;
import org.jeecg.modules.datasources.model.WaterfallOfflineTask;
import org.jeecg.modules.datasources.service.IOfflineTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author selegant
 */
@Service
public class OfflineTaskServiceImpl extends
        ServiceImpl<WaterfallOfflineTaskMapper, WaterfallOfflineTask> implements
        IOfflineTaskService {

    @Autowired
    private WaterfallOfflineTaskMapper offlineTaskMapper;

    /**
     * 保存离线任务
     *
     * @param offlineTask
     * @return
     */
    @Override
    public Boolean saveOfflineTask(OfflineTaskDTO offlineTask) {
        WaterfallOfflineTask task = new WaterfallOfflineTask();
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
        return offlineTaskMapper.insertSelective(task)>0;
    }
}
