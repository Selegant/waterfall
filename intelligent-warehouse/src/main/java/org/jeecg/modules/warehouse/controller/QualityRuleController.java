package org.jeecg.modules.warehouse.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.warehouse.model.WaterfallQualityRule;
import org.jeecg.modules.warehouse.service.IQualityRuleService;
import org.jeecg.modules.warehouse.dto.WaterfallQualityRuleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author liansongye
 * @create 2021/9/2 5:35 下午
 */
@RestController
@RequestMapping("/quality")
@Api(tags = "质量规则管理")
@Slf4j
public class QualityRuleController {

    @Autowired
    private IQualityRuleService qualityRuleService;

    @GetMapping("/rule/pageList")
    @ApiOperation("质量规则分页列表")
    public Result<Object> rulePageList(@RequestParam Integer modelId,
                                   @RequestParam(value = "ruleName", required = false) String ruleName,
                                   @RequestParam(value = "ruleType", required = false) Integer ruleType,
                                   @RequestParam(value = "enableFlag", required = false) Boolean enableFlag,
                                   @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                   @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        Result<Object> result = new Result<>();

        try {
            Page<WaterfallQualityRule> page = new Page<>(pageNo, pageSize);
            WaterfallQualityRule rule = new WaterfallQualityRule();
            rule.setModelId(modelId);
            rule.setRuleName(ruleName);
            rule.setRuleType(ruleType);
            rule.setEnableFlag(enableFlag);
            rule.setDelFlag(false);
            result = Result.OK("query success!",  qualityRuleService.queryRulePage(page, rule));

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.error500(e.getMessage());
        }

        return result;
    }

    @PostMapping("/rule")
    @ApiOperation("添加质量规则")
    public Result<Object> addRule(@RequestBody WaterfallQualityRuleDTO rule) {
        Result<Object> result = new Result<>();

        try {
            qualityRuleService.saveRule(rule);
            result.setMessage("add success！");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.error500(e.getMessage());
        }
        return result;
    }

    @PutMapping("/rule")
    @ApiOperation("修改质量规则")
    public Result<Object> updateRule(@RequestBody WaterfallQualityRuleDTO rule) {
        Result<Object> result = new Result<>();

        try {
            qualityRuleService.updateRule(rule);
            result.setMessage("update success！");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.error500(e.getMessage());
        }
        return result;
    }

    @PutMapping("/rule/enable/{id}")
    @ApiOperation("启用/停用质量规则")
    public Result<Object> enableRule(@PathVariable Integer id,
                                     @RequestParam Boolean enable) {
        Result<Object> result = new Result<>();

        try {
            qualityRuleService.updateRuleEnableSatus(id, enable);
            result.setMessage("update success！");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.error500(e.getMessage());
        }
        return result;
    }

    @DeleteMapping("/rule/{id}")
    @ApiOperation("删除质量规则")
    public Result<Object> deleteRule(@PathVariable Integer id) {
        Result<Object> result = new Result<>();

        try {
            qualityRuleService.deleteRule(id);
            result.setMessage("delete success！");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.error500(e.getMessage());
        }
        return result;
    }

    @GetMapping("/rule/{id}")
    @ApiOperation("质量规则详情")
    public Result<Object> ruleInfo(@PathVariable Integer id) {
        Result<Object> result = new Result<>();

        try {
            result.setResult(qualityRuleService.ruleInfo(id));
            result.setMessage("query success！");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.error500(e.getMessage());
        }
        return result;
    }

    @GetMapping("/ruleFieldType")
    @ApiOperation("质量规则字段类型列表")
    public Result<Object> ruleFieldTypeList() {
        Result<Object> result = new Result<>();

        try {
            result.setResult(qualityRuleService.queryRuleFieldTypeList());
            result.setMessage("query success！");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.error500(e.getMessage());
        }
        return result;
    }
}
