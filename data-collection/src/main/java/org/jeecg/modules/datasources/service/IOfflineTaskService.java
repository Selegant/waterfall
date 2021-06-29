package org.jeecg.modules.datasources.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.datasources.dto.OfflineTaskDTO;
import org.jeecg.modules.datasources.model.WaterfallJobInfo;
import org.jeecg.modules.datasources.model.WaterfallOfflineTask;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;

/**
 * @author selegant
 */
public interface IOfflineTaskService extends IService<WaterfallJobInfo> {

    /**
     * 保存离线任务
     * @param offlineTask
     * @return
     */
    Boolean saveOfflineTask(OfflineTaskDTO offlineTask);
}
