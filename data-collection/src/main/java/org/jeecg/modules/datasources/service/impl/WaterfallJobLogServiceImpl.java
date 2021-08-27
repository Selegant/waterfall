package org.jeecg.modules.datasources.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.datasources.mapper.WaterfallDataSourceAmountMapper;
import org.jeecg.modules.datasources.mapper.WaterfallJobLogMapper;
import org.jeecg.modules.datasources.model.WaterfallDataSourceAmount;
import org.jeecg.modules.datasources.model.WaterfallJobLog;
import org.jeecg.modules.datasources.service.IWaterfallJobLogService;
import org.springframework.stereotype.Service;

/**
 * @author Detective
 * @date Created in 2021/8/26
 */
@Service
public class WaterfallJobLogServiceImpl extends
        ServiceImpl<WaterfallJobLogMapper, WaterfallJobLog> implements IWaterfallJobLogService {

}
