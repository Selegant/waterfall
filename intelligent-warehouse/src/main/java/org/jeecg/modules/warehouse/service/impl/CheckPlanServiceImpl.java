package org.jeecg.modules.warehouse.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.warehouse.dto.WaterfallQualityCheckPlanDTO;

import org.jeecg.modules.warehouse.mapper.WaterfallQualityModelWithJobInfoMapper;
import org.jeecg.modules.warehouse.service.ICheckPlanService;
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
    private WaterfallQualityModelWithJobInfoMapper modelWithJobInfoMapper;

    @Override
    public List<WaterfallQualityCheckPlanDTO> checkPlanList(Integer modelId) {
        return modelWithJobInfoMapper.checkPlanList(modelId);
    }


    @Override
    public void addCheckPlan(WaterfallQualityCheckPlanDTO checkPlanDTO) {
        checkPlanDTO.getJobInfoId();
        checkPlanDTO.getModelId();

//        WaterfallJobInfo jobInfo = new WaterfallJobInfo();
        //保存任务

        //保存中间表


    }
}
