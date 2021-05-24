package org.jeecg.modules.datasources.controller;

import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.datasources.dto.TableColumnInfoDTO;
import org.jeecg.modules.datasources.dto.WaterfallDataSourceListDTO;
import org.jeecg.modules.datasources.input.TableColumnInput;
import org.jeecg.modules.datasources.model.WaterfallDataSource;
import org.jeecg.modules.datasources.model.WaterfallDataSourceType;
import org.jeecg.modules.datasources.service.IDataSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
            log.error(e.getMessage(),e);
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
            log.error(e.getMessage(),e);
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
            log.error(e.getMessage(),e);
            result.error500(e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public Result<WaterfallDataSource> update(@RequestBody WaterfallDataSource dataSource, HttpServletRequest request){
        Result<WaterfallDataSource> result = new Result<>();
        try {
            dataSourceService.updateDataSource(dataSource);
            result.success("修改成功！");
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            result.error500(e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public Result<String> delete(@RequestBody List<Integer> ids, HttpServletRequest request) {
        Result<String> result = new Result<>();
        try {
            dataSourceService.deleteDataSource(ids);
            result.success("删除成功！");
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            result.error500("操作失败");
        }
        return result;
    }

    @RequestMapping(value = "/type/add", method = RequestMethod.POST)
    public Result<WaterfallDataSourceType> addDataSourceType(@RequestBody WaterfallDataSourceType dataSourceType, HttpServletRequest request) {
        Result<WaterfallDataSourceType> result = new Result<>();
        try {
            dataSourceService.addDataSourceType(dataSourceType);
            result.success("添加成功！");
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            result.error500("操作失败");
        }
        return result;
    }

    @RequestMapping(value = "/type/update", method = RequestMethod.POST)
    public Result<WaterfallDataSourceType> updateDataSourceType(@RequestBody WaterfallDataSourceType dataSourceType, HttpServletRequest request) {
        Result<WaterfallDataSourceType> result = new Result<>();
        try {
            dataSourceService.updateDataSourceType(dataSourceType);
            result.success("修改成功！");
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            result.error500("操作失败");
        }
        return result;
    }

    @RequestMapping(value = "/type/delete",method = RequestMethod.POST)
    public Result<String> deleteDataSourceType(@RequestBody List<Integer> ids, HttpServletRequest request) {
        Result<String> result = new Result<>();
        try {
            dataSourceService.deleteDataSourceType(ids);
            result.success("删除成功！");
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            result.error500("操作失败");
        }
        return result;
    }

    @RequestMapping(value = "/connection",method = RequestMethod.POST)
    public Result<Boolean> connection(@RequestBody WaterfallDataSource dataSource, HttpServletRequest request) {
        Result<Boolean> result = new Result<>();
        try {
            result.setResult(dataSourceService.connection(dataSource));
            result.success("操作成功！");
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            result.error500(e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/getTables",method = RequestMethod.POST)
    public Result<List<String>> getTables(@RequestBody WaterfallDataSource dataSource, HttpServletRequest request) {
        Result<List<String>> result = new Result<>();
        try {
            result.setResult(dataSourceService.getTables(dataSource));
            result.success("操作成功！");
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            result.error500("操作失败");
        }
        return result;
    }

    @RequestMapping(value = "/getTableColumns",method = RequestMethod.POST)
    public Result<List<TableColumnInfoDTO>> getTableColumns(@RequestBody TableColumnInput input, HttpServletRequest request) {
        Result<List<TableColumnInfoDTO>> result = new Result<>();
        try {
            result.setResult(dataSourceService.getTableColumns(input));
            result.success("操作成功！");
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            result.error500("操作失败");
        }
        return result;
    }

}
