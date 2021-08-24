package org.jeecg.modules.datasources.controller;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.datasources.dto.DatabaseTreeDTO;
import org.jeecg.modules.datasources.dto.PageInfoDTO;
import org.jeecg.modules.datasources.dto.TableColumnInfoDTO;
import org.jeecg.modules.datasources.dto.TargetTypeColumnDTO;
import org.jeecg.modules.datasources.input.CreateHiveTableInput;
import org.jeecg.modules.datasources.input.TableColumnInput;
import org.jeecg.modules.datasources.model.WaterfallDataSource;
import org.jeecg.modules.datasources.model.WaterfallDataSourceAmount;
import org.jeecg.modules.datasources.model.WaterfallDataSourceType;
import org.jeecg.modules.datasources.service.IDataSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author selegant
 */
@RestController
@RequestMapping("datasource")
@Slf4j
public class DataSourceController {

    @Autowired
    IDataSourceService dataSourceService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Result<List<WaterfallDataSource>> list(@RequestParam String purpose, HttpServletRequest request) {
        Result<List<WaterfallDataSource>> result = new Result<>();
        try {
            result.setResult(dataSourceService.list(purpose));
            result.success("查询成功！");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.error500("操作失败");
        }
        return result;
    }


    @RequestMapping(value = "/tree/list", method = RequestMethod.GET)
    public Result<DatabaseTreeDTO> treeList(@RequestParam String purpose, HttpServletRequest request) {
        Result<DatabaseTreeDTO> result = new Result<>();
        try {
            result.setResult(dataSourceService.treeList(purpose));
            result.success("查询成功！");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.error500("操作失败");
        }
        return result;
    }


    @RequestMapping(value = "/type/list", method = RequestMethod.GET)
    public Result<List<WaterfallDataSourceType>> dataSourceTypeList(HttpServletRequest request) {
        Result<List<WaterfallDataSourceType>> result = new Result<>();
        try {
            result.setResult(dataSourceService.dataSourceTypeList());
            result.success("查询成功！");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.error500("操作失败");
        }
        return result;
    }


    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result<WaterfallDataSource> add(@RequestBody WaterfallDataSource dataSource, HttpServletRequest request) {
        Result<WaterfallDataSource> result = new Result<>();
        try {
            dataSourceService.saveDataSource(dataSource);
            result.success("添加成功！");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.error500(e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Result<WaterfallDataSource> update(@RequestBody WaterfallDataSource dataSource, HttpServletRequest request) {
        Result<WaterfallDataSource> result = new Result<>();
        try {
            dataSourceService.updateDataSource(dataSource);
            result.success("修改成功！");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.error500(e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Result<String> delete(@RequestBody List<Integer> ids, HttpServletRequest request) {
        Result<String> result = new Result<>();
        try {
            dataSourceService.deleteDataSource(ids);
            result.success("删除成功！");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.error500("操作失败");
        }
        return result;
    }

    @RequestMapping(value = "/type/add", method = RequestMethod.POST)
    public Result<WaterfallDataSourceType> addDataSourceType(@RequestBody WaterfallDataSourceType dataSourceType,
            HttpServletRequest request) {
        Result<WaterfallDataSourceType> result = new Result<>();
        try {
            dataSourceService.addDataSourceType(dataSourceType);
            result.success("添加成功！");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.error500("操作失败");
        }
        return result;
    }

    @RequestMapping(value = "/type/update", method = RequestMethod.POST)
    public Result<WaterfallDataSourceType> updateDataSourceType(@RequestBody WaterfallDataSourceType dataSourceType,
            HttpServletRequest request) {
        Result<WaterfallDataSourceType> result = new Result<>();
        try {
            dataSourceService.updateDataSourceType(dataSourceType);
            result.success("修改成功！");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.error500("操作失败");
        }
        return result;
    }

    @RequestMapping(value = "/type/delete", method = RequestMethod.POST)
    public Result<String> deleteDataSourceType(@RequestBody List<Integer> ids, HttpServletRequest request) {
        Result<String> result = new Result<>();
        try {
            dataSourceService.deleteDataSourceType(ids);
            result.success("删除成功！");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.error500("操作失败");
        }
        return result;
    }

    @RequestMapping(value = "/connection", method = RequestMethod.POST)
    public Result<Boolean> connection(@RequestBody WaterfallDataSource dataSource, HttpServletRequest request) {
        Result<Boolean> result = new Result<>();
        try {
            result.setResult(dataSourceService.connection(dataSource));
            result.success("操作成功！");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.error500(e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/getTables/{id}", method = RequestMethod.GET)
    public Result<List<String>> getTables(@PathVariable(value = "id") String id, HttpServletRequest request) {
        Result<List<String>> result = new Result<>();
        if (id.length() == 1) {
            result.setResult(new ArrayList<>(16));
            return result.success("操作成功！");
        }
        try {
            Integer dbId = Integer.valueOf(id.split("-")[0]);
            Integer typeId = Integer.valueOf(id.split("-")[1]);
            result.setResult(dataSourceService.getTables(dbId, typeId));
            result.success("操作成功！");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.error500("操作失败");
        }
        return result;
    }

    @RequestMapping(value = "/getTableColumns", method = RequestMethod.POST)
    public Result<List<TableColumnInfoDTO>> getTableColumns(@RequestBody TableColumnInput input,
            HttpServletRequest request) {
        Result<List<TableColumnInfoDTO>> result = new Result<>();
        try {
            result.setResult(dataSourceService.getTableColumns(input));
            result.success("操作成功！");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.error500("操作失败");
        }
        return result;
    }

    @RequestMapping(value = "/amountList/{dbId}/{type}", method = RequestMethod.GET)
    public Result<PageInfoDTO> getAmountList(@PathVariable(value = "dbId") Integer dbId,
            @PathVariable(value = "type") Integer type,
            HttpServletRequest request) {
        int pageNo = 1;
        int pageSize = 1000;
        Result<PageInfoDTO> result = new Result<>();
        PageInfoDTO page = new PageInfoDTO();
        page.setTotalPage(pageNo);
        page.setTotalCount(0);
        page.setPageNo(pageNo);
        page.setPageSize(pageSize);
        page.setData(new Object());
        if (dbId < 0) {
            page.setData(new ArrayList<>());
            result.setResult(page);
            result.success("操作成功！");
            return result;
        }
        try {
            List<WaterfallDataSourceAmount> list = dataSourceService.getAmountList(dbId, type);
            page.setData(list);
            page.setTotalCount(list.size());
            result.setResult(page);
            result.success("操作成功！");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.error500(e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/asyncAmount/{dbId}/{type}", method = RequestMethod.GET)
    public Result<String> asyncAmount(@PathVariable(value = "dbId") Integer dbId,
            @PathVariable(value = "type") Integer type,
            HttpServletRequest request) {
        Result<String> result = new Result<>();
        if (dbId < 0) {
            result.error500("资源ID错误");
            return result;
        }
        try {
            dataSourceService.asyncUpdateAmount(dbId, type);
            result.success("操作成功！");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.error500(e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/targetTypeColumns/{sourceId}/{targetId}/{tableName}", method = RequestMethod.GET)
    public Result<List<TargetTypeColumnDTO>> getTargetTypeColumns(@PathVariable(value = "sourceId") Integer sourceId,
            @PathVariable(value = "targetId") Integer targetId,
            @PathVariable(value = "tableName", required = false) String tableName,
            HttpServletRequest request) {
        Result<List<TargetTypeColumnDTO>> result = new Result<>();
        try {
            result.setResult(dataSourceService.getTargetTypeColumns(sourceId, targetId, tableName));
            result.success("操作成功！");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.error500(e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/columnType/{dbType}", method = RequestMethod.GET)
    public Result<List<String>> getTargetTypeColumns(@PathVariable(value = "dbType") String dbType) {
        Result<List<String>> result = new Result<>();
        try {
            result.setResult(dataSourceService.getColumnType(dbType));
            result.success("操作成功！");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.error500(e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/createTable", method = RequestMethod.POST)
    public Result<String> createHiveTable(@RequestBody CreateHiveTableInput input) {
        Result<String> result = new Result<>();
        try {
            dataSourceService.createHiveTableByInput(input);
            result.success("操作成功！");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.error500(e.getMessage());
        }
        return result;
    }
}
