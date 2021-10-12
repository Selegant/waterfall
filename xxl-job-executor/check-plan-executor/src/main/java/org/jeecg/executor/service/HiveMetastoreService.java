package org.jeecg.executor.service;

import java.util.List;

/**
 * @Author zhangwk
 * @create 2021/10/11 11:29 上午
 */
public interface HiveMetastoreService {
    // [[column_name, type_name, integer_idx][column_name1, type_name1, integer_idx1]]
    List<List<String>> getTableColumnInfo(String databaseName, String tableName);
}
