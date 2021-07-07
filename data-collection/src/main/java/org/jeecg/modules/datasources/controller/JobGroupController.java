package org.jeecg.modules.datasources.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.annotation.Resource;
import org.jeecg.datax.biz.model.ReturnT;
import org.jeecg.datax.enums.RegistryConfig;
import org.jeecg.modules.datasources.core.util.I18nUtil;
import org.jeecg.modules.datasources.mapper.WaterfallJobGroupMapper;
import org.jeecg.modules.datasources.mapper.WaterfallJobInfoMapper;
import org.jeecg.modules.datasources.mapper.WaterfallJobRegistryMapper;
import org.jeecg.modules.datasources.model.WaterfallJobGroup;
import org.jeecg.modules.datasources.model.WaterfallJobRegistry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by jingwk on 2019/11/17
 */
@RestController
@RequestMapping("/api/jobGroup")
@Api(tags = "执行器管理接口")
public class JobGroupController {

    @Resource
    public WaterfallJobInfoMapper jobInfoMapper;
    @Resource
    public WaterfallJobGroupMapper jobGroupMapper;
    @Resource
    private WaterfallJobRegistryMapper jobRegistryMapper;

    @GetMapping("/list")
    @ApiOperation("执行器列表")
    public ReturnT<List<WaterfallJobGroup>> getExecutorList() {
        return new ReturnT<>(jobGroupMapper.findAll());
    }

    @PostMapping("/save")
    @ApiOperation("新建执行器")
    public ReturnT<String> save(@RequestBody WaterfallJobGroup jobGroup) {

        // valid
        if (jobGroup.getAppName() == null || jobGroup.getAppName().trim().length() == 0) {
            return new ReturnT<String>(500, (I18nUtil.getString("system_please_input") + "AppName"));
        }
        if (jobGroup.getAppName().length() < 4 || jobGroup.getAppName().length() > 64) {
            return new ReturnT<String>(500, I18nUtil.getString("jobgroup_field_appName_length"));
        }
        if (jobGroup.getTitle() == null || jobGroup.getTitle().trim().length() == 0) {
            return new ReturnT<String>(500,
                    (I18nUtil.getString("system_please_input") + I18nUtil.getString("jobgroup_field_title")));
        }
        if (jobGroup.getAddressType() != 0) {
            if (jobGroup.getAddressList() == null || jobGroup.getAddressList().trim().length() == 0) {
                return new ReturnT<String>(500, I18nUtil.getString("jobgroup_field_addressType_limit"));
            }
            String[] addresses = jobGroup.getAddressList().split(",");
            for (String item : addresses) {
                if (item == null || item.trim().length() == 0) {
                    return new ReturnT<String>(500, I18nUtil.getString("jobgroup_field_registryList_invalid"));
                }
            }
        }

        int ret = jobGroupMapper.save(jobGroup);
        return (ret > 0) ? ReturnT.SUCCESS : ReturnT.FAIL;
    }

    @PostMapping("/update")
    @ApiOperation("更新执行器")
    public ReturnT<String> update(@RequestBody WaterfallJobGroup jobGroup) {
        // valid
        if (jobGroup.getAppName() == null || jobGroup.getAppName().trim().length() == 0) {
            return new ReturnT<String>(500, (I18nUtil.getString("system_please_input") + "AppName"));
        }
        if (jobGroup.getAppName().length() < 4 || jobGroup.getAppName().length() > 64) {
            return new ReturnT<String>(500, I18nUtil.getString("jobgroup_field_appName_length"));
        }
        if (jobGroup.getTitle() == null || jobGroup.getTitle().trim().length() == 0) {
            return new ReturnT<String>(500,
                    (I18nUtil.getString("system_please_input") + I18nUtil.getString("jobgroup_field_title")));
        }
        if (jobGroup.getAddressType() == 0) {
            // 0=自动注册
            List<String> registryList = findRegistryByAppName(jobGroup.getAppName());
            String addressListStr = null;
            if (registryList != null && !registryList.isEmpty()) {
                Collections.sort(registryList);
                addressListStr = "";
                for (String item : registryList) {
                    addressListStr += item + ",";
                }
                addressListStr = addressListStr.substring(0, addressListStr.length() - 1);
            }
            jobGroup.setAddressList(addressListStr);
        } else {
            // 1=手动录入
            if (jobGroup.getAddressList() == null || jobGroup.getAddressList().trim().length() == 0) {
                return new ReturnT<String>(500, I18nUtil.getString("jobgroup_field_addressType_limit"));
            }
            String[] addresses = jobGroup.getAddressList().split(",");
            for (String item : addresses) {
                if (item == null || item.trim().length() == 0) {
                    return new ReturnT<String>(500, I18nUtil.getString("jobgroup_field_registryList_invalid"));
                }
            }
        }

        int ret = jobGroupMapper.update(jobGroup);
        return (ret > 0) ? ReturnT.SUCCESS : ReturnT.FAIL;
    }

    private List<String> findRegistryByAppName(String appNameParam) {
        HashMap<String, List<String>> appAddressMap = new HashMap<>();
        List<WaterfallJobRegistry> list = jobRegistryMapper.findAll(RegistryConfig.DEAD_TIMEOUT, new Date());
        if (list != null) {
            for (WaterfallJobRegistry item : list) {
                if (RegistryConfig.RegistType.EXECUTOR.name().equals(item.getRegistryGroup())) {
                    String appName = item.getRegistryKey();
                    List<String> registryList = appAddressMap.get(appName);
                    if (registryList == null) {
                        registryList = new ArrayList<>();
                    }

                    if (!registryList.contains(item.getRegistryValue())) {
                        registryList.add(item.getRegistryValue());
                    }
                    appAddressMap.put(appName, registryList);
                }
            }
        }
        return appAddressMap.get(appNameParam);
    }

    @PostMapping("/remove")
    @ApiOperation("移除执行器")
    public ReturnT<String> remove(int id) {

        // valid
        int count = jobInfoMapper.pageListCount(0, 10, id, -1, null, null, 0, null);
        if (count > 0) {
            return new ReturnT<>(500, I18nUtil.getString("jobgroup_del_limit_0"));
        }

        List<WaterfallJobGroup> allList = jobGroupMapper.findAll();
        if (allList.size() == 1) {
            return new ReturnT<>(500, I18nUtil.getString("jobgroup_del_limit_1"));
        }

        int ret = jobGroupMapper.remove(id);
        return (ret > 0) ? ReturnT.SUCCESS : ReturnT.FAIL;
    }

    @RequestMapping(value = "/loadById", method = RequestMethod.POST)
    @ApiOperation("根据id获取执行器")
    public ReturnT<WaterfallJobGroup> loadById(int id) {
        WaterfallJobGroup jobGroup = jobGroupMapper.load(id);
        return jobGroup != null ? new ReturnT<>(jobGroup) : new ReturnT<>(ReturnT.FAIL_CODE, null);
    }

    @GetMapping("/query")
    @ApiOperation("查询执行器")
    public ReturnT<List<WaterfallJobGroup>> get(@ApiParam(value = "执行器AppName")
    @RequestParam(value = "appName", required = false) String appName,
            @ApiParam(value = "执行器名称")
            @RequestParam(value = "title", required = false) String title,
            @ApiParam(value = "执行器地址列表")
            @RequestParam(value = "addressList", required = false) String addressList) {
        return new ReturnT<>(jobGroupMapper.find(appName, title, addressList));
    }

}
