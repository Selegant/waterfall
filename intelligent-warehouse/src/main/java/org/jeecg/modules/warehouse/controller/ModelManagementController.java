package org.jeecg.modules.warehouse.controller;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.warehouse.model.WaterfallModel;
import org.jeecg.modules.warehouse.service.IModelManagementService;
import org.jeecg.modules.warehouse.dto.DataModuleDTO;
import org.jeecg.modules.warehouse.model.WaterfallFolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/**
 * @Author liansongye
 * @create 2021/6/25 5:58 下午
 */
@RestController
@RequestMapping("modelManagement")
@Api(tags = "模型管理")
@Slf4j
public class ModelManagementController {

    @Autowired
    private IModelManagementService modelManagementService;

    @GetMapping("/folder")
    @ApiOperation("查询层级树")
    public Result<Object> folderTree() {
        Result<Object> result = new Result<>();
        try {
            result.setResult(modelManagementService.queryList());
            result.success("query success！");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.error500("操作失败");
        }
        return result;
    }

    @PostMapping("/addFolder")
    @ApiOperation("添加文件夹")
    public Result<Object> addFolder(@RequestBody WaterfallFolder waterfallFolder) {
        Result<Object> result = new Result<>();
        try {
            modelManagementService.saveFolder(waterfallFolder);
            result.success("add success！");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.error500(e.getMessage());
        }
        return result;
    }

    @PutMapping("/folder")
    @ApiOperation("更新文件夹")
    public Result<Object> updateFolder(@RequestBody WaterfallFolder folder) {
        Result<Object> result = new Result<>();
        try {
            modelManagementService.updateFolderWithConditionById(folder, false);
            result.success("update success！");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.error500(e.getMessage());
        }
        return result;
    }

    @DeleteMapping("/folder/{id}")
    @ApiOperation("删除文件夹")
    public Result<Object> deleteFolder(@PathVariable Integer id) {
        Result<Object> result = new Result<>();
        try {
            if (modelManagementService.queryListWithParentId(id).size() > 0) {
                result.setCode(HttpStatus.BAD_REQUEST.value());
                result.setMessage("请先清空文件夹！");
            } else {
                WaterfallFolder delFolder = new WaterfallFolder();
                delFolder.setId(id);
                delFolder.setDelFlag(true);
                modelManagementService.updateFolderWithConditionById(delFolder, true);
                result.success("delete success！");
            }

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.error500(e.getMessage());
        }

        return result;
    }

    @PostMapping("/data-module")
    @ApiOperation("添加数据模型")
    public Result<Object> addDataModule(@RequestBody DataModuleDTO dataModuleDTO) {
        Result<Object> result = new Result<>();

        try {
            modelManagementService.saveDataModule(dataModuleDTO);
            result.success("add success!");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.error500(e.getMessage());
        }
        return result;
    }


    @GetMapping("/dataModuleList")
    @ApiOperation("数据模型列表")
    public Result<IPage<WaterfallModel>> dataModuleList(@RequestParam(value = "folderId", required = false) Integer folderId,
                                                        @RequestParam(value = "modelName", required = false) String modelName,
                                                        @RequestParam(value = "modelStatusCode", required = false) Integer modelStatusCode,
                                                        @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                                        @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        Result<IPage<WaterfallModel>> result = new Result<>();

        try {
            Page<WaterfallModel> page = new Page<>(pageNo, pageSize);
            WaterfallModel waterfallModel = new WaterfallModel();
            waterfallModel.setFolderId(folderId);
            waterfallModel.setModelName(modelName);
            waterfallModel.setModelStatusCode(modelStatusCode);
            waterfallModel.setDelFlag(false);
            result = Result.OK("query success!", modelManagementService.queryDataModulePage(page, waterfallModel));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.error500(e.getMessage());
        }
        return result;
    }


    @PutMapping("/data-module")
    @ApiOperation("修改数据模型")
    public Result<Object> updateDataModule(@RequestBody DataModuleDTO dataModuleDTO) {
        Result<Object> result = new Result<>();
        try {
            modelManagementService.updateModuleWithConditionById(dataModuleDTO);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.error500(e.getMessage());
        }
        return result;
    }

    @DeleteMapping("/data-module/{id}")
    @ApiOperation("删除数据模型")
    public Result<Object> deleteDataModule(@PathVariable Integer id) {
        Result<Object> result = new Result<>();
        try {
            modelManagementService.deleteModelById(id);
            result.success("delete success!");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.error500(e.getMessage());
        }
        return result;
    }

    @GetMapping("/data-module/{id}")
    @ApiOperation("数据模型详情")
    public Result<Object> dataModuleInfo(@PathVariable Integer id) {
        Result<Object> result = new Result<>();
        try {
            result.setResult(modelManagementService.queryDataMoudle(id));
            result.setMessage("query success!");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.error500(e.getMessage());
        }

        return result;
    }


    @PostMapping("/data-module/ddl")
    @ApiOperation("DDL生成数据模型")
    public Result<Object> ddlToModel(@RequestBody DataModuleDTO dto) {
        Result<Object> result = new Result<>();
        try {
            result.setResult(modelManagementService.ddlToModel(dto));
            result.setMessage("add success!");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.error500(e.getMessage());
        }

        return result;
    }

    @PostMapping("/data-module/{folderId}/{source}/{tableName}")
    @ApiOperation("单表或视图生成数据模型")
    public Result<Object> tableOrViewToModel(@PathVariable Integer folderId,
                                             @PathVariable Integer source,
                                             @PathVariable String tableName) {
        Result<Object> result = new Result<>();
        try {
            result.setResult(modelManagementService.tableOrViewToModel(folderId, source, tableName));
            result.setMessage("query success!");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.error500(e.getMessage());
        }

        return result;
    }

    @PostMapping("/data-module/{folderId}/{source}")
    @ApiOperation("数据库生成数据模型")
    public Result<Object> dbToModel(@PathVariable Integer folderId,
                                    @PathVariable Integer source) {
        Result<Object> result = new Result<>();
        try {
            modelManagementService.dbToModel(folderId, source);
            result.setMessage("add success!");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.error500(e.getMessage());
        }

        return result;
    }

    @PostMapping("/data-module/publish/{modelId}")
    @ApiOperation("发布数据模型")
    public Result<Object> modelPublish(@PathVariable Integer modelId) {
        Result<Object> result = new Result<>();
        try {
            modelManagementService.modelPublish(modelId);
            result.setMessage("publish success!");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.error500(e.getMessage());
        }

        return result;
    }

    @PostMapping("/data-module/off/{modelId}")
    @ApiOperation("下架数据模型")
    public Result<Object> modelOff(@PathVariable Integer modelId) {
        Result<Object> result = new Result<>();
        try {
            modelManagementService.modelOff(modelId);
            result.setMessage("off model success!");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.error500(e.getMessage());
        }

        return result;
    }

    @PostMapping("/data-module/physical/{dbId}")
    @ApiOperation("数据模型物理化")
    public Result<Object> modelToDb(@PathVariable Integer dbId,
                                    @RequestBody List<Integer> modelIds) {
        Result<Object> result = new Result<>();
        try {
            Map<String, String> errorModels = modelManagementService.modelToDb(dbId, modelIds);
            if (errorModels.size() > 0) {
                result.error500(JSON.toJSONString(errorModels));
            } else {
                result.setMessage("physical success!");
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.error500(e.getMessage());
        }

        return result;
    }


    @GetMapping("/data-module/fields/{modelId}")
    @ApiOperation("数据模型字段")
    public Result<Object> queryDataMoudleFields(@PathVariable Integer modelId) {
        Result<Object> result = new Result<>();
        try {
            result.setResult(modelManagementService.queryDataMoudleFields(modelId));
            result.setMessage("query success!");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.error500(e.getMessage());
        }

        return result;
    }


}
