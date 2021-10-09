package org.jeecg.modules.workflow.service.impl;

import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import org.apache.dolphinscheduler.common.enums.DbConnectType;
import org.apache.dolphinscheduler.common.enums.DbType;
import org.apache.dolphinscheduler.dao.entity.DataSource;
import org.jeecg.modules.workflow.service.DsBase;
import org.jeecg.modules.workflow.service.DsDataSourceService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author wangtao
 * @date 2021-09-28 09:19
 */

@Service
public class DsDataSourceServiceImpl extends DsBase implements DsDataSourceService  {
    public static final String MYSQL = "MYSQL";

    public static final String ORACLE = "ORACLE";

    public static final String HIVE = "HIVE";

    @Override
    public boolean createDsDataSource(String dataSource) {
        String url = "datasources/create";
        JSONObject dsDataSource = JSON.parseObject(dataSource);
        Map<String,Object> params = new HashMap<>(16);
        if(ORACLE.equals(dsDataSource.getString("dbType"))){
            params.put("database", Optional.ofNullable(dsDataSource.getString("serverName")).orElse(""));
        }else {
            params.put("database", Optional.ofNullable(dsDataSource.getString("dbName")).orElse(""));
        }
        params.put("host",dsDataSource.getString("host"));
        params.put("name",dsDataSource.getString("dataSourceName"));
        params.put("port",dsDataSource.getString("port"));
        params.put("principal","");
        params.put("other","");
        params.put("type",DbType.valueOf(dsDataSource.getString("dbType")));
        params.put("userName",Optional.ofNullable(dsDataSource.getString("username")).orElse(""));
        params.put("password",Optional.ofNullable(dsDataSource.getString("password")).orElse(""));
        params.put("connectType", DbConnectType.ORACLE_SERVICE_NAME);
        HttpResponse response = HttpUtil.createPost(dolphinSchedulerUrl+url).header("token",dolphinSchedulerToken).form(params).execute();
        JSONObject result = JSONObject.parseObject(response.body());
        if(result.getIntValue("code")!=0){
            throw new RuntimeException(result.getString("msg"));
        }
        return true;
    }

    @Override
    public boolean deleteDsDataSource(String name) {
        String url = "datasources/deleteByName";
        Map<String,Object> params = new HashMap<>(16);
        params.put("name",name);
        HttpResponse response = HttpUtil.createGet(dolphinSchedulerUrl+url).header("token",dolphinSchedulerToken).form(params).execute();
        JSONObject result = JSONObject.parseObject(response.body());
        if(result.getIntValue("code")!=0){
            throw new RuntimeException(result.getString("msg"));
        }
        return true;
    }




}
