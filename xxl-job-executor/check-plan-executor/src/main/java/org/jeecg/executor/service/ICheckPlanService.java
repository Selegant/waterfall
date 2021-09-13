package org.jeecg.executor.service;

import org.jeecg.executor.dto.JobDTO;

/**
 * @Author liansongye
 * @create 2021/9/9 11:53 上午
 */
public interface ICheckPlanService {
    JobDTO getExecutorParam(Integer jobId);
}
