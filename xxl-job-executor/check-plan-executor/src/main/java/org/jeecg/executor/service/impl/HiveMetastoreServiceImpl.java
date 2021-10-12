package org.jeecg.executor.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.jeecg.executor.mapper.HiveMetastoreMapper;
import org.jeecg.executor.service.HiveMetastoreService;
import org.jeecg.xxl.log.JobLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author zhangwk
 * @create 2021/10/11 11:29 上午
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class HiveMetastoreServiceImpl implements HiveMetastoreService {
    @Autowired
    private HiveMetastoreMapper hiveMapper;

    @Override
    public List<List<String>> getTableColumnInfo(String databaseName, String tableName) {
        // 样例数据：l_comment	string	15
        List<List<String>> columnInfo = hiveMapper.getColumnInfo(databaseName, tableName);
        //检验规则
        if (columnInfo == null || columnInfo.size() == 0 )  return null;
        for (int i = 0; i < columnInfo.size(); i++) {
            List<String> colinfo = columnInfo.get(i);
            if (colinfo == null || colinfo.size() == 0){
                JobLogger.log("模型属性为空，序列号：" + i);
                columnInfo.remove(i);
            } else if (colinfo.size() != 3){
                JobLogger.log("模型属性异常,信息：" + colinfo.toString());
            }
        }
        return columnInfo;
    }
}
