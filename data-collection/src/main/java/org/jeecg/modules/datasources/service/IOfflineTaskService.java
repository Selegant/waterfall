package org.jeecg.modules.datasources.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.datasources.dto.OfflineTaskDTO;
import org.jeecg.modules.datasources.model.WaterfallJobInfo;

/**
 * @author selegant
 */
public interface IOfflineTaskService extends IService<WaterfallJobInfo> {

    /**
     * 保存离线任务
     *
     * @param offlineTask
     * @return
     */
    Boolean saveOfflineTask(OfflineTaskDTO offlineTask);

    String getJobJson(OfflineTaskDTO input);
}
