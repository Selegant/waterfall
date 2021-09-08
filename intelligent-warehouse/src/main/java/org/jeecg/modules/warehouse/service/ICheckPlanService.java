package org.jeecg.modules.warehouse.service;

import org.jeecg.modules.warehouse.dto.JobDTO;
import org.jeecg.modules.warehouse.dto.WaterfallQualityCheckPlanDTO;

import java.util.List;

/**
 * @Author liansongye
 * @create 2021/9/2 5:49 下午
 */
public interface ICheckPlanService {

    List<WaterfallQualityCheckPlanDTO> checkPlanList(Integer modelId);

    void addCheckPlan(WaterfallQualityCheckPlanDTO checkPlanDTO);

    void tryRunCheckPlan(Integer jobId);

    JobDTO getExecutorParam(Integer jobId);

    WaterfallQualityCheckPlanDTO checkPlanInfo(Integer jobId);
}
