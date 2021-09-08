package org.jeecg.modules.warehouse.controller;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.datasources.core.thread.JobTriggerPoolHelper;
import org.jeecg.modules.datasources.core.trigger.TriggerTypeEnum;
import org.jeecg.modules.datasources.service.IOfflineTaskService;
import org.jeecg.modules.warehouse.dto.JobDTO;
import org.jeecg.modules.warehouse.dto.WaterfallQualityCheckPlanDTO;
import org.jeecg.modules.warehouse.service.ICheckPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author liansongye
 * @create 2021/9/2 5:43 下午
 */
@RestController
@RequestMapping("/quality")
@Api(tags = "检验计划管理")
@Slf4j
public class CheckPlanController {

    @Autowired
    private ICheckPlanService checkPlanService;

    @Autowired
    private IOfflineTaskService offlineTaskService;

    @GetMapping("/checkPlanList/{modelId}")
    @ApiOperation("校验计划列表")
    public Result<Object> checkPlanList(@PathVariable Integer modelId) {
        Result<Object> result = new Result<>();

        try {
            result.setResult(checkPlanService.checkPlanList(modelId));
            result.setMessage("query success！");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.error500(e.getMessage());
        }
        return result;
    }

    @PostMapping("/checkPlan")
    @ApiOperation("新建校验计划")
    public Result<Object> addCheckPlan(@RequestBody WaterfallQualityCheckPlanDTO checkPlanDTO) {
        Result<Object> result = new Result<>();

        try {
            checkPlanService.addCheckPlan(checkPlanDTO);
            result.setMessage("add success！");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.error500(e.getMessage());
        }
        return result;
    }

    @PostMapping("/tryRunCheckPlan/{jobId}")
    @ApiOperation("试跑校验计划")
    public Result tryRunCheckPlan(@PathVariable Integer jobId) {
        Result result = new Result<>();

        try {
            final JobDTO checkPlanDTO = checkPlanService.getExecutorParam(jobId);

            // force cover job param
            String executorParam = JSON.toJSONString(checkPlanDTO);
            if (executorParam == null) {
                executorParam = "";
            }
            JobTriggerPoolHelper.trigger(jobId, TriggerTypeEnum.MANUAL, -1, null, executorParam);
            result.setResult(checkPlanDTO);
            result.success("操作成功！");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.error500("操作失败");
        }
        return result;
    }

    @GetMapping("/checkPlan/{jobId}")
    @ApiOperation("校验计划详情")
    public Result<Object> addCheckPlan(@PathVariable Integer jobId) {
        Result<Object> result = new Result<>();

        try {
            result.setResult(checkPlanService.checkPlanInfo(jobId));
            result.setMessage("query success！");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.error500(e.getMessage());
        }
        return result;
    }

    @PostMapping(value = "/checkPlan/stop/{id}")
    @ApiOperation("停止任务")
    public Result<String> pause(@PathVariable Integer id) {
        Result<String> result = new Result<>();
        try {
            offlineTaskService.stop(id);
            result.success("操作成功！");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.error500("操作失败");
        }
        return result;
    }

    @PostMapping(value = "/checkPlan/start/{id}")
    @ApiOperation("开启任务")
    public Result<String> start(@PathVariable Integer id) {
        Result<String> result = new Result<>();
        try {
            offlineTaskService.start(id);
            result.success("操作成功！");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.error500("操作失败");
        }
        return result;
    }

}
