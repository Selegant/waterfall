package org.jeecg.modules.datasources.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.datasources.mapper.WaterfallOfflineTaskMapper;
import org.jeecg.modules.datasources.model.WaterfallOfflineTask;
import org.jeecg.modules.datasources.service.IOfflineTaskService;
import org.springframework.stereotype.Service;

/**
 * @author selegant
 */
@Service
public class OfflineTaskServiceImpl extends
        ServiceImpl<WaterfallOfflineTaskMapper, WaterfallOfflineTask> implements
        IOfflineTaskService {

}
