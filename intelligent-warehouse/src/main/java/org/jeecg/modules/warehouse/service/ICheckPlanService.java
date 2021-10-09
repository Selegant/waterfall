package org.jeecg.modules.warehouse.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.warehouse.dto.JobDTO;
import org.jeecg.modules.warehouse.dto.WaterfallQualityCheckPlanDTO;
import org.jeecg.modules.warehouse.model.WaterfallQualityRule;

import java.util.List;

/**
 * @Author liansongye
 * @create 2021/9/2 5:49 下午
 */
public interface ICheckPlanService {

    void addCheckPlan(WaterfallQualityCheckPlanDTO checkPlanDTO);

    JobDTO getExecutorParam(Integer jobId);

    WaterfallQualityCheckPlanDTO checkPlanInfo(Integer jobId);

    void updateCheckPlan(WaterfallQualityCheckPlanDTO checkPlanDTO);

    String checkPlanResult(Integer jobLogId);

    IPage<WaterfallQualityCheckPlanDTO> queryCheckPlanPage(Integer modelId, Page<WaterfallQualityCheckPlanDTO> page);

    void deleteCheckPlan(Integer id);

    List<WaterfallQualityRule> queryRuleList(Integer modelId, Integer jobId);
}
