package org.jeecg.modules.workflow.service;


import com.alibaba.fastjson.JSONObject;

/**
 * @author wangtao
 * @date 2021-09-28 09:19
 */
public interface DsDataSourceService {

    boolean createDsDataSource(String dataSource);

    boolean deleteDsDataSource(String name);
}
