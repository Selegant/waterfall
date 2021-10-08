package org.jeecg.executor.jobhandler;

import org.jeecg.executor.dto.check.BaseCheckResult;
import org.jeecg.executor.dto.check.CheckEmptyWithFields;
import org.jeecg.executor.util.JdbcUtil;
import org.jeecg.xxl.log.JobLogger;

import java.util.List;
import java.util.Map;

public class EmptyCheckHandler {

    public static BaseCheckResult emptyCheck(JdbcUtil jdbc, String tableName, List<String> list) {
        if(list.isEmpty())
            return null;
        CheckEmptyWithFields res = new CheckEmptyWithFields();

        // 查询语句拼接
        String sql = "select sum(1) as count";
        for (String field : list){
            sql += ", sum(if(" + field + " is null or " + field + " = '',1,0)) as " + field;
        }
        sql += " from " + tableName;

        try {
            final List<Map<String, Object>> result = jdbc.findResult(sql, null);
            res.setFieldNames(list);
            res.setTotal(Long.valueOf(String.valueOf(result.get(0).get("count"))));
            res.setResultSums(result.get(0));

            // 打印日志
            String resInfo = "";
            for (String field : list)
                resInfo += (field + ":" + res.getResultSums().get(field));
            JobLogger.log("非空检查，表{},检查总行数目{},字段名和其空值数{}", tableName, res.getTotal(), resInfo);
        } catch (Exception e) {
            JobLogger.log("非空检查错误：{}", e.getMessage());
        }

        return res;
    }
}
