package org.jeecg.executor.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.jeecg.executor.dto.JobDTO;
import org.jeecg.executor.dto.WaterfallQualityRuleField;
import org.jeecg.executor.mapper.WaterfallModelMapper;
import org.jeecg.executor.mapper.WaterfallQualityRuleFieldMapper;
import org.jeecg.executor.service.ICheckPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author liansongye
 * @create 2021/9/2 5:50 下午
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class CheckPlanServiceImpl implements ICheckPlanService {


    @Autowired
    private WaterfallModelMapper modelMapper;

    @Autowired
    private WaterfallQualityRuleFieldMapper ruleFieldMapper;


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


}
