package org.jeecg.modules.warehouse.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.jeecg.modules.datasources.core.cron.CronExpression;
import org.jeecg.modules.datasources.mapper.WaterfallJobInfoMapper;
import org.jeecg.modules.datasources.model.WaterfallJobInfo;
import org.jeecg.modules.warehouse.dto.JobDTO;
import org.jeecg.modules.warehouse.dto.WaterfallQualityCheckPlanDTO;

import org.jeecg.modules.warehouse.mapper.*;
import org.jeecg.modules.warehouse.model.WaterfallQualityModelWithJobInfo;
import org.jeecg.modules.warehouse.model.WaterfallQualityRule;
import org.jeecg.modules.warehouse.model.WaterfallQualityRuleField;
import org.jeecg.modules.warehouse.model.WaterfallQualityRuleWithJobInfo;
import org.jeecg.modules.warehouse.service.ICheckPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @Author liansongye
 * @create 2021/9/2 5:50 下午
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class CheckPlanServiceImpl implements ICheckPlanService {

    private static final Integer GROUP_ID = 2;

    private static final Integer PROJECT_ID = 2;

    private static final Integer DEFAULT_RETRY_COUNT = 0;

    private static final Integer DEFAULT_TIMEOUT = 1000 * 20;

    @Autowired
    private WaterfallQualityModelWithJobInfoMapper modelWithJobInfoMapper;

    @Autowired
    private WaterfallJobInfoMapper waterfallJobInfoMapper;

    @Autowired
    private WaterfallModelMapper modelMapper;

    @Autowired
    private WaterfallQualityRuleFieldMapper ruleFieldMapper;

    @Autowired
    private WaterfallQualityRuleMapper ruleMapper;

    @Autowired
    private WaterfallQualityRuleWithJobInfoMapper ruleWithJobInfoMapper;


    @Override
    public List<WaterfallQualityCheckPlanDTO> checkPlanList(Integer modelId) {
        return modelWithJobInfoMapper.checkPlanList(modelId);
    }


    @Override
    public void addCheckPlan(WaterfallQualityCheckPlanDTO checkPlanDTO) {

        //保存job
        WaterfallJobInfo jobInfo = new WaterfallJobInfo();
        jobInfo.setJobGroup(GROUP_ID);
        if (!CronExpression.isValidExpression(checkPlanDTO.getTaskCorn())) {
            throw new RuntimeException("CORN表达式不合法");
        }
        jobInfo.setTaskCorn(checkPlanDTO.getTaskCorn());
        jobInfo.setTaskName(checkPlanDTO.getTaskName());
//        jobInfo.setTaskDesc();
        jobInfo.setExecutorTimeout(DEFAULT_TIMEOUT);
        jobInfo.setExecutorFailRetryCount(DEFAULT_RETRY_COUNT);
        jobInfo.setGlueType("BEAN");
        jobInfo.setTriggerStatus(0);
        jobInfo.setTriggerLastTime(0L);
        jobInfo.setTriggerNextTime(0L);

        jobInfo.setExecutorRouteStrategy("RANDOM");
//        jobInfo.setExecutorBlockStrategy("SERIAL_EXECUTION");
        jobInfo.setExecutorHandler("checkPlanJobHandler");
        jobInfo.setUpdateTime(new Date());
        waterfallJobInfoMapper.insert(jobInfo);

        //保存model-job中间表
        WaterfallQualityModelWithJobInfo modelWithJobInfo = new WaterfallQualityModelWithJobInfo();
        modelWithJobInfo.setJobInfoId(jobInfo.getId());
        modelWithJobInfo.setModelId(checkPlanDTO.getModelId());
        modelWithJobInfoMapper.insert(modelWithJobInfo);

        //保存job-rule中间表
        checkPlanDTO.getQualityRules().stream().forEach(e -> {
            WaterfallQualityRuleWithJobInfo ruleWithJobInfo = new WaterfallQualityRuleWithJobInfo();
            ruleWithJobInfo.setJobInfoId(jobInfo.getId());
            ruleWithJobInfo.setQualityRuleId(e.getId());
            ruleWithJobInfoMapper.insert(ruleWithJobInfo);
        });

    }

    @Override
    public void tryRunCheckPlan(Integer jobId) {

    }

    @Override
    public JobDTO getExecutorParam(Integer jobId) {
        //获取数据库连接信息
        JobDTO dbInfo = modelMapper.getDbInfo(jobId);
        //检验规则
        List<WaterfallQualityRuleField> ruleFields = ruleFieldMapper.getInfo(jobId);
        ruleFields.stream().forEach(e -> {
            if (StringUtils.isNotBlank(e.getRegularExpression())) {
                dbInfo.getCornCheck().put(e.getFieldName(), e.getRegularExpression());
            }else if (e.getEmptyFlag() != null && e.getEmptyFlag() == true) {
                dbInfo.getEmptyCheck().add(e.getFieldName());
            }else if (StringUtils.isNotBlank(e.getCompareMode()) && StringUtils.isNotBlank(e.getExpectedValue())) {
                dbInfo.getCompareCheck().put(e.getFieldName(), e.getCompareMode() + e.getExpectedValue());
            }
        });
        return dbInfo;
    }

    @Override
    public WaterfallQualityCheckPlanDTO checkPlanInfo(Integer jobId) {
         WaterfallQualityCheckPlanDTO res = modelWithJobInfoMapper.checkPlanInfo(jobId);
         if (res != null) {
             res.setQualityRules(ruleMapper.getListWithJob(jobId));
         }
        return res;
    }
}
