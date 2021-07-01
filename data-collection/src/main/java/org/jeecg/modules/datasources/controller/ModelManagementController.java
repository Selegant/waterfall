package org.jeecg.modules.datasources.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.datasources.dto.DataModuleDTO;
import org.jeecg.modules.datasources.model.WaterfallFolder;
import org.jeecg.modules.datasources.model.WaterfallModel;
import org.jeecg.modules.datasources.service.IModelManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


/**
 * @Author liansongye
 * @create 2021/6/25 5:58 下午
 */
@RestController
@RequestMapping("modelManagement")
@Slf4j
public class ModelManagementController {


    @Autowired
    private IModelManagementService modelManagementService;

    /**
     * 根据父层级查询直接子层级
     *
     * @param parentId
     * @return
     */
    @GetMapping("/folder")
    public Result<Object> listFolder(@RequestParam(value = "parentId", required = false, defaultValue = "0") Integer parentId) {
        Result<Object> result = new Result<>();
        try {
            result.setResult(modelManagementService.queryListWithParentId(parentId));
            result.success("query success！");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.error500("操作失败");
        }
        return result;
    }

    /**
     * 添加文件夹
     *
     * @param waterfallFolder
     * @return
     */
    @PostMapping("/folder")
    public Result<Object> addFolder(@RequestBody WaterfallFolder waterfallFolder) {
        Result<Object> result = new Result<>();
        try {
            waterfallFolder.setDelFlag(false);
            modelManagementService.saveFolder(waterfallFolder);
            result.success("add success！");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.error500(e.getMessage());
        }
        return result;
    }

    /**
     * 更新文件夹
     *
     * @return
     */
    @PutMapping("/folder")
    public Result<Object> updateFolder(@RequestBody WaterfallFolder folder) {
        Result<Object> result = new Result<>();

        try {
            if (modelManagementService.exsitById(folder.getId())) {
                modelManagementService.updateFolderWithConditionById(folder);
                result.success("update success！");
            } else {
                result.setCode(HttpStatus.BAD_REQUEST.value());
                result.setMessage("no exist！");
            }

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.error500(e.getMessage());
        }

        return result;
    }

    @DeleteMapping("/folder/{id}")
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
                modelManagementService.updateFolderWithConditionById(delFolder);
                result.success("delete success！");
            }

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.error500(e.getMessage());
        }

        return result;
    }

    /**
     * 添加数据模型
     */
    @PostMapping("/data-module")
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

    /**
     * 数据模型列表
     */
    @GetMapping("/data-module")
    public Result<IPage<WaterfallModel>> dataModuleList(@RequestParam(value = "folderId") Integer folderId,
                                                        @RequestParam(value = "modelName", required = false) String modelName,
                                                        @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                                        @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        Result<IPage<WaterfallModel>> result = new Result<>();

        try {
            Page<WaterfallModel> page = new Page<>(pageNo, pageSize);
            WaterfallModel waterfallModel = new WaterfallModel();
            waterfallModel.setFolderId(folderId);
            waterfallModel.setModelName(modelName);
            result = Result.OK("query success!", modelManagementService.queryDataModulePage(page, waterfallModel));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.error500(e.getMessage());
        }
        return result;
    }


    /**
     * 修改数据模型
     */
    @PutMapping("/data-module")
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


    /**
     * 删除数据模型
     */
    @DeleteMapping("/data-module/{id}")
    public Result<Object> deleteDataModule(@PathVariable Integer id) {
        Result<Object> result = new Result<>();
        DataModuleDTO dataModuleDTO = new DataModuleDTO();
        dataModuleDTO.setId(id);
        dataModuleDTO.setDelFlag(true);
        try {
            modelManagementService.updateModuleWithConditionById(dataModuleDTO);
            result.success("delete success!");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.error500(e.getMessage());
        }
        return result;
    }


    /**
     * 数据模型详情
     *
     * @return
     */
    @GetMapping("/data-module/{id}")
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


}
