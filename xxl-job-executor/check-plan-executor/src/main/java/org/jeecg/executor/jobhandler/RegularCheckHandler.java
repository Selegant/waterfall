package org.jeecg.executor.jobhandler;

import org.jeecg.executor.dto.check.BaseCheckResult;
import org.jeecg.executor.dto.check.CheckCompareWithFields;
import org.jeecg.executor.dto.check.CheckRegularWithFields;
import org.jeecg.executor.util.JdbcUtil;
import org.jeecg.xxl.log.JobLogger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RegularCheckHandler {
    public static BaseCheckResult regularCheck(JdbcUtil jdbc, String tableName, Map<String, String> map) {
        if(map == null || map.size() == 0)
            return null;
        CheckRegularWithFields res = new CheckRegularWithFields();
        // 查询语句拼接
        String sql = "select sum(1) as count";
        for (Map.Entry ent : map.entrySet()){
            String field = (String) ent.getKey();
            String regular = (String) ent.getValue();
            sql += ", sum(if(" + field + " regexp " + regular + " ,1,0)) as " + field;
        }
        sql += " from " + tableName;

        try {
            final List<Map<String, Object>> result = jdbc.findResult(sql, null);
            res.setTotal(Long.valueOf(String.valueOf(result.get(0).get("count"))));
            Set<String> list = map.keySet();
            res.setFieldNames(new ArrayList<>(list));
            res.setResultSums(result.get(0));
            res.setRegularExpression(map);

            for(Map.Entry ent : result.get(0).entrySet())
                JobLogger.log("通用比较检查，表字{},字段名{}，检查总行数目{}，符合行数{}", tableName, ent.getKey(), res.getTotal(), ent.getValue());
        } catch (Exception e) {
            JobLogger.log("通用比较检查错误：{}", e.getMessage());
        }
        return res;
    }
}
