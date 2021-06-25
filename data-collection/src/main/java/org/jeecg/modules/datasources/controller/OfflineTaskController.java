package org.jeecg.modules.datasources.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.PageInfoResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.datasources.dto.DatabaseTreeDTO;
import org.jeecg.modules.datasources.dto.OfflineTaskDTO;
import org.jeecg.modules.datasources.dto.PageInfoDTO;
import org.jeecg.modules.datasources.dto.TableColumnInfoDTO;
import org.jeecg.modules.datasources.input.TableColumnInput;
import org.jeecg.modules.datasources.model.WaterfallDataSource;
import org.jeecg.modules.datasources.model.WaterfallDataSourceAmount;
import org.jeecg.modules.datasources.model.WaterfallDataSourceType;
import org.jeecg.modules.datasources.model.WaterfallOfflineTask;
import org.jeecg.modules.datasources.service.IDataSourceService;
import org.jeecg.modules.datasources.service.IOfflineTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @author selegant
 */
@RestController
@RequestMapping("offlineTask")
@Slf4j
public class OfflineTaskController {

    @Autowired
    IOfflineTaskService offlineTaskService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Result<PageInfoResponse> queryPageList(WaterfallOfflineTask task, @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
                                                  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize, HttpServletRequest req) {
        Result<PageInfoResponse> result = new Result<>();
        QueryWrapper<WaterfallOfflineTask> queryWrapper = new QueryWrapper<>();
        Page<WaterfallOfflineTask> page = new Page<>(pageNo, pageSize);
        IPage<WaterfallOfflineTask> pageList = offlineTaskService.page(page, queryWrapper);
        PageInfoResponse info = new PageInfoResponse();
        info.setData(pageList.getRecords());
        info.setTotalCount(pageList.getTotal());
        info.setPageNo(pageNo);
        info.setPageSize(pageSize);
        result.setSuccess(true);
        result.setResult(info);
        return result;
    }


    @PostMapping(value = "/saveOfflineTask")
    public Result<String> saveOfflineTask(@RequestBody OfflineTaskDTO offlineTask, HttpServletRequest request) {
        Result<String> result = new Result<>();
        try {
            offlineTaskService.saveOfflineTask(offlineTask);
            result.success("操作成功！");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.error500("操作失败");
        }
        return result;
    }


}
