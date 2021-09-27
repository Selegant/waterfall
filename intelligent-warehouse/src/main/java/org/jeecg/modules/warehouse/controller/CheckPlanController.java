package org.jeecg.modules.warehouse.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.datasources.core.thread.JobTriggerPoolHelper;
import org.jeecg.modules.datasources.core.trigger.TriggerTypeEnum;
import org.jeecg.modules.datasources.service.IOfflineTaskService;
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
    public Result checkPlanList(@PathVariable Integer modelId,
                                @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        Result<IPage<WaterfallQualityCheckPlanDTO>> result = new Result<>();

        try {
            Page<WaterfallQualityCheckPlanDTO> page = new Page<>(pageNo, pageSize);
            result.setResult(checkPlanService.queryCheckPlanPage(modelId, page));
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
            JobTriggerPoolHelper.trigger(jobId, TriggerTypeEnum.MANUAL, -1, null, "");
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

    @GetMapping("/checkPlanResult/{jobLogId}")
    @ApiOperation("校验计划结果")
    public Result<Object> checkPlanResult(@PathVariable Integer jobLogId) {
        Result<Object> result = new Result<>();

        try {
            result.setResult(checkPlanService.checkPlanResult(jobLogId));
            result.setMessage("query success！");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.error500(e.getMessage());
        }
        return result;
    }


    @PutMapping("/checkPlan")
    @ApiOperation("编辑校验计划")
    public Result<Object> updateCheckPlan(@RequestBody WaterfallQualityCheckPlanDTO checkPlanDTO) {
        Result<Object> result = new Result<>();

        try {
            checkPlanService.updateCheckPlan(checkPlanDTO);
            result.setMessage("update success！");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.error500(e.getMessage());
        }
        return result;
    }

    @DeleteMapping("/checkPlan/{id}")
    @ApiOperation("删除校验计划")
    public Result<Object> deleteCheckPlan(@PathVariable Integer id) {
        Result<Object> result = new Result<>();

        try {
            checkPlanService.deleteCheckPlan(id);
            result.setMessage("delete success！");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.error500(e.getMessage());
        }
        return result;
    }

    @PutMapping(value = "/checkPlan/{action}/{planId}")
    @ApiOperation("开启/关闭任务")
    public Result<String> start(@PathVariable String action,
                                @PathVariable Integer planId) {
        Result<String> result = new Result<>();
        try {
            result.success("操作成功！");
            if (action.equals("start")) {
                offlineTaskService.start(planId);
            } else if (action.equals("stop")) {
                offlineTaskService.stop(planId);
            } else {
                result.error500("不支持操作：" + action);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.error500("操作失败");
        }
        return result;
    }

}
