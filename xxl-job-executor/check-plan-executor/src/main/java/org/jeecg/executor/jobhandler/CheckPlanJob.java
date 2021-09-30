package org.jeecg.executor.jobhandler;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.commons.lang.StringUtils;
import org.jeecg.executor.dto.CheckResultDTO;
import org.jeecg.executor.dto.JobDTO;
import org.jeecg.executor.dto.NewCheckResultDTO;
import org.jeecg.executor.dto.WaterfallJobLog;
import org.jeecg.executor.mapper.WaterfallJobLogMapper;
import org.jeecg.executor.service.ICheckPlanService;
import org.jeecg.rpc.util.json.BasicJson;
import org.jeecg.xxl.biz.model.ReturnT;
import org.jeecg.xxl.biz.model.TriggerParam;
import org.jeecg.executor.util.JdbcUtil;
import org.jeecg.xxl.handler.IJobHandler;
import org.jeecg.xxl.handler.annotation.JobHandler;
import org.jeecg.xxl.log.JobLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.*;

@JobHandler(value = "checkPlanJobHandler")
@Component
public class CheckPlanJob extends IJobHandler {

    @Autowired
    private ICheckPlanService checkPlanService;

    @Autowired
    private WaterfallJobLogMapper waterfallJobLogMapper;

    private final String driver = "org.apache.hive.jdbc.HiveDriver";

    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    @Override
    public ReturnT<String> execute(TriggerParam tgParam) throws Exception {
        Date startTime = new Date();
        Map<String, Object> res = new HashMap<>();
        Map<String, CheckResultDTO> fieldInfo = new HashMap<>();

        JobLogger.log("-------------------------------------检查开始---------------------------------");
        // 获取校验信息和校验表信息
        final JobDTO checkPlanDTO = checkPlanService.getExecutorParam(tgParam.getJobId());
        // 准备以jdbc方式连接hive 查询出质检数据
        String url = checkPlanDTO.getJdbcUrl();
        String username = checkPlanDTO.getUsername();
        String password = checkPlanDTO.getPassword();
        String tableName = checkPlanDTO.getFolderId() + "_" + checkPlanDTO.getTableName();
//        String tableName = checkPlanDTO.getTableName();

        JdbcUtil jdbc = new JdbcUtil(username, password, driver, url);
        try {
            // 注册驱动，获取连接
            jdbc.getConnection();
            /*checkPlanDTO.getEmptyCheck().stream().forEach(e -> {

                CheckResultDTO dto = emptyCheck(jdbc, tableName, e);

                if (dto != null && StringUtils.isNotBlank(dto.getFieldName())) {
                    if (fieldInfo.containsKey(dto.getFieldName())) {
                        CheckResultDTO tmp = fieldInfo.get(dto.getFieldName());
                        tmp.setTotal(dto.getTotal());
                        tmp.setEmptySum(dto.getEmptySum());
                    } else {
                        fieldInfo.put(dto.getFieldName(), dto);
                    }
                }
            });*/
            // 遍历判空规则，进行判空检测
            NewCheckResultDTO emptyDto = emptyCheck(jdbc, tableName, checkPlanDTO.getEmptyCheck());
            // 结果检测、封装
            if (emptyDto != null && !emptyDto.getEmptySums().isEmpty())
                    fieldInfo.put(tableName, emptyDto);

            checkPlanDTO.getCompareCheck().entrySet().stream().forEach(e -> {
                CheckResultDTO dto = compareCheck(jdbc, tableName, e.getKey(), e.getValue());
                if (dto != null && StringUtils.isNotBlank(dto.getFieldName())) {
                    if (fieldInfo.containsKey(dto.getFieldName())) {
                        CheckResultDTO tmp = fieldInfo.get(dto.getFieldName());
                        tmp.setTotal(dto.getTotal());
                        tmp.setCompareSum(dto.getCompareSum());
                        tmp.setCompareExpression(dto.getCompareExpression());
                    } else {
                        fieldInfo.put(dto.getFieldName(), dto);
                    }
                }
            });
            checkPlanDTO.getCornCheck().entrySet().stream().forEach(e -> {
                CheckResultDTO dto = cornCheck(jdbc, tableName, e.getKey(), e.getValue());
                if (dto != null && StringUtils.isNotBlank(dto.getFieldName())) {
                    if (fieldInfo.containsKey(dto.getFieldName())) {
                        CheckResultDTO tmp = fieldInfo.get(dto.getFieldName());
                        tmp.setTotal(dto.getTotal());
                        tmp.setRegularSum(dto.getRegularSum());
                        tmp.setRegularExpression(dto.getRegularExpression());
                    } else {
                        fieldInfo.put(dto.getFieldName(), dto);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jdbc.releaseConn();
        }

        JobLogger.log("-------------------------------------检查结束---------------------------------");


        //基本信息
        Map<String, Object> baseInfo = new HashMap<>();
        baseInfo.put("dataSource", "HIVE");
//        baseInfo.put("project", tgParam.get);
        baseInfo.put("tableName", checkPlanDTO.getTableName());
        baseInfo.put("startTime", sdf.format(startTime));
        baseInfo.put("endTime", sdf.format(new Date()));

        //表信息

        res.put("baseInfo", baseInfo);
        res.put("fieldInfo", fieldInfo);

        WaterfallJobLog log = new WaterfallJobLog();
        log.setId(tgParam.getLogId());
        log.setCheckResult(BasicJson.toJson(res));
        log.setModelName(checkPlanDTO.getTableName());
//        log.setModelId(checkPlanDTO.get);
        waterfallJobLogMapper.updateByPrimaryKeySelective(log);

        return null;
    }

    private NewCheckResultDTO emptyCheck(JdbcUtil jdbc, String tableName, List<String> list) {
        if(list.isEmpty())
            return null;
        NewCheckResultDTO res = new NewCheckResultDTO();

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
            res.setEmptySums(result.get(0));

            // 打印日志
            String resInfo = "";
            for (String field : list)
                resInfo += (field + ":" + res.getEmptySums().get(field));
            JobLogger.log("非空检查，表{},检查总行数目{},字段名和其空值数{}", tableName, res.getTotal(), resInfo);
        } catch (Exception e) {
            JobLogger.log("非空检查错误：{}", e.getMessage());
        }

        return res;
    }

    private CheckResultDTO emptyCheck(JdbcUtil jdbc, String tableName, String field) {
        CheckResultDTO res = new CheckResultDTO();
        String sql = "select count(*) as count, count(" + field + ") as col from " + tableName;
        try {
            final List<Map<String, Object>> result = jdbc.findResult(sql, null);
            final Long count = Long.valueOf(result.get(0).get("count").toString());
            final Long col = Long.valueOf(result.get(0).get("col").toString());

            res.setFieldName(field);
            res.setTotal(count);
            res.setEmptySum(count - col);
//            res.setFiedlType();
            JobLogger.log("非空检查，表字{},字段名{}，检查总行数目{}，符合行数{}", tableName, field, count, col);
        } catch (Exception e) {
            JobLogger.log("非空检查错误：{}", e.getMessage());
        }

        return res;
    }


    private CheckResultDTO compareCheck(JdbcUtil jdbc, String tableName, String field, String exceptValue) {
        CheckResultDTO res = new CheckResultDTO();
        String countSql = "select count(*) as count from " + tableName;
        String sql = "select count(*) as col from " + tableName + " where " + field + " " + exceptValue;
        try {
            final Long count = Long.valueOf(jdbc.findResult(countSql, null).get(0).get("count").toString());
            final Long col = Long.valueOf(jdbc.findResult(sql, null).get(0).get("col").toString());

            res.setFieldName(field);
            res.setTotal(count);
//            res.setFiedlType();
            res.setCompareSum(col);
            res.setCompareExpression(exceptValue);
            JobLogger.log("通用比较检查，表字{},字段名{}，检查总行数目{}，符合行数{}", tableName, field, count, col);
        } catch (Exception e) {
            JobLogger.log("通用比较检查错误：{}", e.getMessage());
        }
        return res;
    }


    private CheckResultDTO cornCheck(JdbcUtil jdbc, String tableName, String field, String regular) {
        CheckResultDTO res = new CheckResultDTO();
        String countSql = "select count(*) as count from " + tableName;
        String sql = "select count(*) as col from " + tableName + " where " + field + " regexp '" + regular + "'";
        try {
            final Long count = Long.valueOf(jdbc.findResult(countSql, null).get(0).get("count").toString());
            final Long col = Long.valueOf(jdbc.findResult(sql, null).get(0).get("col").toString());

            res.setFieldName(field);
            res.setTotal(count);
//            res.setFiedlType();
            res.setRegularSum(col);
            res.setRegularExpression(regular);
            JobLogger.log("正则检查，表字{},字段名{}，检查总行数目{}，符合行数{}", tableName, field, count, col);
        } catch (Exception e) {
            JobLogger.log("正则检查错误：{}", e.getMessage());
        }
        return res;
    }


}
