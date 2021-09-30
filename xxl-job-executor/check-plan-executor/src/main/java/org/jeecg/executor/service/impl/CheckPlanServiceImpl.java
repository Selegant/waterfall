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

    /**
     * 拼接  校验规则方式   校验表信息  对象于dbInfo中
     * @param jobId
     * @return
     */
    @Override
    public JobDTO getExecutorParam(Integer jobId) {
        //获取数据库连接信息
        // 样例数据：jdbcUrl,dbName,driver,username,password,folderId,tableName
        JobDTO dbInfo = modelMapper.getDbInfo(jobId);
        //检验规则
        // 34	26	zxj	2				1	0	2021-09-28 14:45:29	2021-09-28 14:45:29
        List<WaterfallQualityRuleField> ruleFields = ruleFieldMapper.getInfo(jobId);
        ruleFields.stream().forEach(e -> {
            // 校验规则 不为空且长度不为0且不由空白符构成
            if (StringUtils.isNotBlank(e.getRegularExpression())) {
                // 不空，放入core map中       规则字段名:校验规则
                dbInfo.getCornCheck().put(e.getFieldName(), e.getRegularExpression());
            }else if (e.getEmptyFlag() != null && e.getEmptyFlag() == true) {
                // 校验规则是  判空校验，放入 Empty 列表中 规则字段名
                dbInfo.getEmptyCheck().add(e.getFieldName());
            }else if (StringUtils.isNotBlank(e.getCompareMode()) && StringUtils.isNotBlank(e.getExpectedValue())) {
                // 正则方式  放入对应的集合中
                dbInfo.getCompareCheck().put(e.getFieldName(), e.getCompareMode() + e.getExpectedValue());
            }
        });
        return dbInfo;
    }


}
