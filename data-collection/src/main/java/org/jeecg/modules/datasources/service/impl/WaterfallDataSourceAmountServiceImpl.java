package org.jeecg.modules.datasources.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.datasources.mapper.WaterfallDataSourceAmountMapper;
import org.jeecg.modules.datasources.model.WaterfallDataSourceAmount;
import org.jeecg.modules.datasources.service.IWaterfallDataSourceAmountService;
import org.springframework.stereotype.Service;

@Service
public class WaterfallDataSourceAmountServiceImpl extends
        ServiceImpl<WaterfallDataSourceAmountMapper, WaterfallDataSourceAmount> implements
        IWaterfallDataSourceAmountService {

}
