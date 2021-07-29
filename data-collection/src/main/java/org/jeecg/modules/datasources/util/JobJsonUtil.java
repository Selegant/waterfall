package org.jeecg.modules.datasources.util;

import static org.jeecg.modules.datasources.constant.DataSourceConstant.HIVE;
import static org.jeecg.modules.datasources.constant.DataSourceConstant.HIVE_READER;
import static org.jeecg.modules.datasources.constant.DataSourceConstant.HIVE_WRITER;
import static org.jeecg.modules.datasources.constant.DataSourceConstant.MYSQL;
import static org.jeecg.modules.datasources.constant.DataSourceConstant.MYSQL_READER;
import static org.jeecg.modules.datasources.constant.DataSourceConstant.MYSQL_WRITER;
import static org.jeecg.modules.datasources.constant.DataSourceConstant.ORACLE;
import static org.jeecg.modules.datasources.constant.DataSourceConstant.ORACLE_READER;
import static org.jeecg.modules.datasources.constant.DataSourceConstant.ORACLE_WRITER;

import com.alibaba.fastjson.JSONObject;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.jeecg.modules.datasources.dto.JobResultDTO;
import org.jeecg.modules.datasources.dto.JobResultDTO.ConnectionBean;
import org.jeecg.modules.datasources.dto.JobResultDTO.ContentBean;
import org.jeecg.modules.datasources.dto.JobResultDTO.ContentBean.ReaderBean;
import org.jeecg.modules.datasources.dto.JobResultDTO.ContentBean.ReaderBean.ParameterBean;
import org.jeecg.modules.datasources.dto.JobResultDTO.ContentBean.WriterBean;
import org.jeecg.modules.datasources.dto.JobResultDTO.ContentBean.WriterBean.ParameterBeanX;
import org.jeecg.modules.datasources.dto.JobResultDTO.ContentBean.WriterBean.ParameterBeanX.ConnectionBeanW;
import org.jeecg.modules.datasources.dto.JobResultDTO.SettingBean;
import org.jeecg.modules.datasources.dto.JobResultDTO.SettingBean.ErrorLimitBean;
import org.jeecg.modules.datasources.dto.JobResultDTO.SettingBean.SpeedBean;
import org.jeecg.modules.datasources.dto.OfflineTaskDTO;
import org.jeecg.modules.datasources.dto.OfflineTaskDTO.MappingColumnsBean;
import org.jeecg.modules.datasources.model.WaterfallDataSource;

public class JobJsonUtil {

    private final static String KEY_JOB = "job";

    private final static Integer CHANNEL_NUM = 3;

    private final static Integer ERROR_NUM = 0;

    private final static Double PERCENTAGE = 0.02;

    public static JSONObject assembleJobJson(WaterfallDataSource original, WaterfallDataSource target,
            OfflineTaskDTO input) {
        JobResultDTO job = new JobResultDTO();
        // 基本设置
        SettingBean setting = new SettingBean();
        SpeedBean speed = new SpeedBean();
        // channel 表示通道数量，byte表示通道速度，如果单通道速度1MB，配置byte为1048576表示一个channel
        speed.setChannel(CHANNEL_NUM);
//        speed.setBytes(1048576);
        setting.setSpeed(speed);
        ErrorLimitBean errorLimit = new ErrorLimitBean();
        errorLimit.setRecord(ERROR_NUM);
        errorLimit.setPercentage(PERCENTAGE);
        setting.setErrorLimit(errorLimit);
        job.setSetting(setting);

        List<ContentBean> contentBeans = new ArrayList<>();
        ContentBean content = new ContentBean();
        ReaderBean readerBean = new ReaderBean();
        // 名称根据数据库类型来赋值
        String originalType = original.getDbType().toLowerCase();
        String readerName = "";
        if (MYSQL.equals(originalType)) {
            readerName = MYSQL_READER;
        }
        if (ORACLE.equals(originalType)) {
            readerName = ORACLE_READER;
        }
        if (HIVE.equals(originalType)) {
            readerName = HIVE_READER;
        }
        readerBean.setName(readerName);
        // 源头表参数
        ParameterBean readerParameter = new ParameterBean();
        readerParameter.setPassword(original.getPassword());
        readerParameter.setUsername(original.getUsername());
        // 源表连接信息
        List<ConnectionBean> readerConnections = new ArrayList<>(1);
        ConnectionBean readerConnection = new ConnectionBean();
        List<String> readerJdbcUrl = new ArrayList<>(1);
        // 源表jdbcUrl
        readerJdbcUrl.add(original.getJdbcUrl());
        readerConnection.setJdbcUrl(readerJdbcUrl);
        // 源表表名
        List<String> readerTables = new ArrayList<>(1);
        readerTables.add(input.getOriginalTable());
        readerConnection.setTable(readerTables);
        readerConnections.add(readerConnection);
        readerParameter.setConnection(readerConnections);
        // 源表所需字段
        readerParameter.setColumn(input.getMappingColumns().stream().map(MappingColumnsBean::getOriginalColumn).collect(
                Collectors.toList()));
        readerBean.setParameter(readerParameter);
        content.setReader(readerBean);

        // 目标表信息
        WriterBean writerBean = new WriterBean();
        String targetType = target.getDbType().toLowerCase();
        String writerName = "";
        if (MYSQL.equals(targetType)) {
            writerName = MYSQL_WRITER;
        }
        if (ORACLE.equals(targetType)) {
            writerName = ORACLE_WRITER;
        }
        if (HIVE.equals(originalType)) {
            writerName = HIVE_WRITER;
        }
        writerBean.setName(writerName);
        // 目标表参数
        ParameterBeanX parameterBeanX = new ParameterBeanX();
        // 是否打印
        parameterBeanX.setPrint(true);
        parameterBeanX.setEncoding("UTF-8");
        parameterBeanX.setUsername(target.getUsername());
        parameterBeanX.setPassword(target.getPassword());
        List<ConnectionBeanW> writeConnections = new ArrayList<>(1);
        ConnectionBeanW writeConnection = new ConnectionBeanW();
        List<String> tables = new ArrayList<>(1);
        tables.add(input.getTargetTable());
        writeConnection.setTable(tables);
        writeConnection.setJdbcUrl(target.getJdbcUrl());
        writeConnections.add(writeConnection);
        parameterBeanX.setConnection(writeConnections);
        parameterBeanX.setColumn(input.getMappingColumns().stream().map(MappingColumnsBean::getTargetColumn).collect(
                Collectors.toList()));
        writerBean.setParameter(parameterBeanX);
        content.setWriter(writerBean);
        contentBeans.add(content);
        job.setContent(contentBeans);
        JSONObject result = new JSONObject();
        result.put(KEY_JOB, job);
        return result;
    }

}
