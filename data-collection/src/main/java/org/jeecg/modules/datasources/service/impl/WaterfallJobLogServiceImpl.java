package org.jeecg.modules.datasources.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.jeecg.modules.datasources.input.WebsocketLogPageInput;
import org.jeecg.modules.datasources.mapper.WaterfallDataSourceAmountMapper;
import org.jeecg.modules.datasources.mapper.WaterfallJobInfoMapper;
import org.jeecg.modules.datasources.mapper.WaterfallJobLogMapper;
import org.jeecg.modules.datasources.model.WaterfallDataSourceAmount;
import org.jeecg.modules.datasources.model.WaterfallJobInfo;
import org.jeecg.modules.datasources.model.WaterfallJobLog;
import org.jeecg.modules.datasources.service.IWaterfallJobLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Detective
 * @date Created in 2021/8/26
 */
@Service
public class WaterfallJobLogServiceImpl extends
        ServiceImpl<WaterfallJobLogMapper, WaterfallJobLog> implements IWaterfallJobLogService {

    @Autowired
    private WaterfallJobInfoMapper waterfallJobInfoMapper;

    @Autowired
    private WaterfallJobLogMapper waterfallJobLogMapper;

    @Override
    public IPage<WaterfallJobLog> pages(WebsocketLogPageInput input) {
          WaterfallJobLog log = new WaterfallJobLog();
          log.setTaskName(input.getJobName());
          return waterfallJobLogMapper.wapperPageList(new Page<>(input.getPageNo(), input.getPageSize()),log);
//        QueryWrapper<WaterfallJobLog> logQueryWrapper = new QueryWrapper<>();
//        if (StringUtils.isBlank(input.getJobName())) {
//            return super.page(new Page<>(input.getPageNo(), input.getPageSize()), logQueryWrapper);
//        }
//        QueryWrapper<WaterfallJobInfo> infoQueryWrapper = new QueryWrapper<>();
//        infoQueryWrapper.like("task_name", input.getJobName());
//        List<Integer> ids = waterfallJobInfoMapper.selectList(infoQueryWrapper).stream().map(WaterfallJobInfo::getId)
//                .collect(Collectors.toList());
//        logQueryWrapper.in("job_id", ids);
//        return super.page(new Page<>(input.getPageNo(), input.getPageSize()), logQueryWrapper);
    }
}
