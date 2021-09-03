package org.jeecg.modules.warehouse.controller;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
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

//    @GetMapping("/checkPlanList/{modelId}")
//    @ApiOperation("校验计划列表")
//    public Result<Object> checkPlanList(@PathVariable Integer modelId) {
//        Result<Object> result = new Result<>();
//
//        try {
//            result.setResult(checkPlanService.checkPlanList(modelId));
//            result.setMessage("delete success！");
//        } catch (Exception e) {
//            log.error(e.getMessage(), e);
//            result.error500(e.getMessage());
//        }
//        return result;
//    }
//
//    @PostMapping("/checkPlan")
//    @ApiOperation("新建校验计划")
//    public Result<Object> addCheckPlan(@RequestBody WaterfallQualityCheckPlanDTO checkPlanDTO) {
//        Result<Object> result = new Result<>();
//
//        try {
//            result.setResult(checkPlanService.addCheckPlan(checkPlanDTO));
//            result.setMessage("delete success！");
//        } catch (Exception e) {
//            log.error(e.getMessage(), e);
//            result.error500(e.getMessage());
//        }
//        return result;
//    }
//
//    @PutMapping("/checkPlan")
//    @ApiOperation("修改校验计划")
//    public Result<Object> addCheckPlan(@RequestBody WaterfallQualityCheckPlanDTO checkPlanDTO) {
//        Result<Object> result = new Result<>();
//
//        try {
//            result.setResult(checkPlanService.addCheckPlan(checkPlanDTO));
//            result.setMessage("delete success！");
//        } catch (Exception e) {
//            log.error(e.getMessage(), e);
//            result.error500(e.getMessage());
//        }
//        return result;
//    }
//
//    @DeleteMapping("/checkPlan")
//    @ApiOperation("删除校验计划")
//    public Result<Object> addCheckPlan(@RequestBody WaterfallQualityCheckPlanDTO checkPlanDTO) {
//        Result<Object> result = new Result<>();
//
//        try {
//            result.setResult(checkPlanService.addCheckPlan(checkPlanDTO));
//            result.setMessage("delete success！");
//        } catch (Exception e) {
//            log.error(e.getMessage(), e);
//            result.error500(e.getMessage());
//        }
//        return result;
//    }
//
//    @GetMapping("/checkPlan")
//    @ApiOperation("校验计划详情")
//    public Result<Object> addCheckPlan(@RequestBody WaterfallQualityCheckPlanDTO checkPlanDTO) {
//        Result<Object> result = new Result<>();
//
//        try {
//            result.setResult(checkPlanService.addCheckPlan(checkPlanDTO));
//            result.setMessage("delete success！");
//        } catch (Exception e) {
//            log.error(e.getMessage(), e);
//            result.error500(e.getMessage());
//        }
//        return result;
//    }

}
