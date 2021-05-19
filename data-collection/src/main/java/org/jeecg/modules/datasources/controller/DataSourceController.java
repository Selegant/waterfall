package org.jeecg.modules.datasources.controller;

import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.constant.CacheConstant;
import org.jeecg.common.system.util.JwtUtil;
import org.jeecg.modules.datasources.model.WaterfallDataSource;
import org.jeecg.modules.datasources.model.WaterfallDataSourceType;
import org.jeecg.modules.datasources.service.IDataSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
    public Result<List<WaterfallDataSource>> list(HttpServletRequest request) {
        Result<List<WaterfallDataSource>> result = new Result<>();
        try {
            result.setResult(dataSourceService.list());
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
            result.error500("操作失败");
        }
        return result;
    }


}
