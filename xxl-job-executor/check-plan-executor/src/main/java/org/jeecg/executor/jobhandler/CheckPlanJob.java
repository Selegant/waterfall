package org.jeecg.executor.jobhandler;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.jeecg.xxl.biz.model.ReturnT;
import org.jeecg.xxl.biz.model.TriggerParam;
import org.jeecg.xxl.handler.IJobHandler;
import org.jeecg.xxl.handler.annotation.JobHandler;
import org.jeecg.executor.util.JdbcUtil;
import org.jeecg.xxl.log.JobLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * XxlJob开发示例（Bean模式）
 *
 * 开发步骤：
 *      1、任务开发：在Spring Bean实例中，开发Job方法；
 *      2、注解配置：为Job方法添加注解 "@XxlJob(value="自定义jobhandler名称", init = "JobHandler初始化方法", destroy = "JobHandler销毁方法")"，注解value值对应的是调度中心新建任务的JobHandler属性的值。
 *      3、执行日志：需要通过 "XxlJobHelper.log" 打印执行日志；
 *      4、任务结果：默认任务结果为 "成功" 状态，不需要主动设置；如有诉求，比如设置任务结果为失败，可以通过 "XxlJobHelper.handleFail/handleSuccess" 自主设置任务结果；
 *
 * @author xuxueli 2019-12-11 21:52:51
 */
@JobHandler(value = "checkPlanJobHandler")
@Component
public class CheckPlanJob extends IJobHandler {
    private static Logger logger = LoggerFactory.getLogger(CheckPlanJob.class);

    @Override
    public ReturnT<String> execute(TriggerParam tgParam) throws Exception {
        JobLogger.log("-------------------------------------检查开始---------------------------------");
        final JSONObject jsonObject = JSONObject.parseObject(tgParam.getExecutorParams());
        String driver = "org.apache.hive.jdbc.HiveDriver";
        String url = jsonObject.getString("jdbcUrl");
        String username = jsonObject.getString("username");
        String password = jsonObject.getString("password");
        String folderId = jsonObject.getString("folderId");
        String tableName = folderId + "_" +jsonObject.getString("tableName");
        JSONArray emptyCheck = jsonObject.getJSONArray("emptyCheck");
        JSONObject compareCheck = jsonObject.getJSONObject("compareCheck");
        final JSONObject cornCheck = jsonObject.getJSONObject("cornCheck");
        JdbcUtil jdbc = new JdbcUtil(username,password,driver,url);
        try {
            jdbc.getConnection();
            emptyCheck.stream().forEach(e -> {
                emptyCheck(jdbc, tableName, e.toString());
            });
            compareCheck.entrySet().stream().forEach(e -> {
                compareCheck(jdbc, tableName, e.getKey(), e.getValue().toString());
            });
            cornCheck.entrySet().stream().forEach(e -> {
                cornCheck(jdbc, tableName, e.getKey(), e.getValue().toString());
            });
        }catch (Exception e) {

        }finally {
            jdbc.releaseConn();
        }

        JobLogger.log("-------------------------------------检查结束---------------------------------");

        return null;
    }


    private void emptyCheck(JdbcUtil jdbc, String tableName, String field) {
        String sql = "select count(*) as count, count(" + field + ") as col from " + tableName;
        try {
            final List<Map<String, Object>> result = jdbc.findResult(sql, null);
            final Integer count = Integer.valueOf(result.get(0).get("count").toString());
            final Integer col = Integer.valueOf(result.get(0).get("col").toString());
            JobLogger.log("非空检查，表字{},字段名{}，检查总行数目{}，符合行数{}", tableName, field, count, col);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void compareCheck(JdbcUtil jdbc, String tableName, String field, String exceptValue) {
        String countSql = "select count(*) as count from " + tableName;
        String sql = "select count(*) as col from " + tableName +" where " + field +" " + exceptValue;
        try {
            final Integer count = Integer.valueOf(jdbc.findResult(countSql, null).get(0).get("count").toString());
            final Integer col = Integer.valueOf(jdbc.findResult(sql, null).get(0).get("col").toString());
            JobLogger.log("通用比较检查，表字{},字段名{}，检查总行数目{}，符合行数{}", tableName, field, count, col);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void cornCheck(JdbcUtil jdbc, String tableName, String field, String regular) {
        String countSql = "select count(*) as count from " + tableName;
        String sql = "select count(*) as col from " + tableName +" where " + field +" regexp '" + regular +"'";
        try {
            final Integer count = Integer.valueOf(jdbc.findResult(countSql, null).get(0).get("count").toString());
            final Integer col = Integer.valueOf(jdbc.findResult(sql, null).get(0).get("col").toString());
            JobLogger.log("正则检查，表字{},字段名{}，检查总行数目{}，符合行数{}", tableName, field, count, col);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
