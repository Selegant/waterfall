package org.jeecg.executor.service.command;

import cn.hutool.core.util.StrUtil;
import org.jeecg.xxl.biz.model.TriggerParam;
import org.jeecg.xxl.enums.IncrementTypeEnum;
import org.jeecg.xxl.log.JobLogger;
import org.jeecg.xxl.util.Constants;
import org.jeecg.xxl.util.DateUtil;
import org.jeecg.executor.util.SystemUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.jeecg.xxl.util.Constants.SPLIT_COMMA;
import static org.jeecg.executor.service.jobhandler.DataXConstant.*;

/**
 * DataX command build
 *
 * @author jingwk 2020-06-07
 */
public class BuildCommand {

    /**
     * DataX command build
     * @param tgParam
     * @param tmpFilePath
     * @param dataXPyPath
     * @return
     */
    public static String[] buildDataXExecutorCmd(TriggerParam tgParam, String tmpFilePath, String dataXPyPath,String hadoopUser) {
        // command process
        //"--loglevel=debug"
        List<String> cmdArr = new ArrayList<>();
        cmdArr.add("python2");
        String dataXHomePath = SystemUtils.getDataXHomePath();
        if (StrUtil.isNotEmpty(dataXHomePath)) {
            dataXPyPath = dataXHomePath.contains("bin") ? dataXHomePath + DEFAULT_DATAX_PY : dataXHomePath + "bin" + File.separator + DEFAULT_DATAX_PY;
        }
        cmdArr.add(dataXPyPath);
        cmdArr.add("-p");
        cmdArr.add("\"-DHADOOP_USER_NAME="+hadoopUser+"\"");
        String doc = buildDataXParam(tgParam);
        if (StrUtil.isNotBlank(doc)) {
            cmdArr.add(doc.replaceAll(SPLIT_SPACE, TRANSFORM_SPLIT_SPACE));
        }
        cmdArr.add(tmpFilePath);
        return cmdArr.toArray(new String[cmdArr.size()]);
    }

    private static String buildDataXParam(TriggerParam tgParam) {
        StringBuilder doc = new StringBuilder();
        String jvmParam = StrUtil.isNotBlank(tgParam.getJvmParam()) ? tgParam.getJvmParam().trim() : tgParam.getJvmParam();
        String partitionStr = tgParam.getPartitionInfo();
        if (StrUtil.isNotBlank(jvmParam)) {
            doc.append(JVM_CM).append(TRANSFORM_QUOTES).append(jvmParam).append(TRANSFORM_QUOTES);
        }

        Integer incrementType = tgParam.getIncrementType();
        String replaceParam = StrUtil.isNotBlank(tgParam.getReplaceParam()) ? tgParam.getReplaceParam().trim() : null;

        if (incrementType != null && replaceParam != null) {

            if (IncrementTypeEnum.TIME.getCode() == incrementType) {
                if (doc.length() > 0) doc.append(SPLIT_SPACE);
                String replaceParamType = tgParam.getReplaceParamType();

                if (StrUtil.isBlank(replaceParamType) || replaceParamType.equals("Timestamp")) {
                    long startTime = tgParam.getStartTime().getTime() / 1000;
                    long endTime = tgParam.getTriggerTime().getTime() / 1000;
                    doc.append(PARAMS_CM).append(TRANSFORM_QUOTES).append(String.format(replaceParam, startTime, endTime));
                } else {
                    SimpleDateFormat sdf = new SimpleDateFormat(replaceParamType);
                    String endTime = sdf.format(tgParam.getTriggerTime()).replaceAll(SPLIT_SPACE, PERCENT);
                    String startTime = sdf.format(tgParam.getStartTime()).replaceAll(SPLIT_SPACE, PERCENT);
                    doc.append(PARAMS_CM).append(TRANSFORM_QUOTES).append(String.format(replaceParam, startTime, endTime));
                }
                //buildPartitionCM(doc, partitionStr);
                doc.append(TRANSFORM_QUOTES);

            } else if (IncrementTypeEnum.ID.getCode() == incrementType) {
                long startId = tgParam.getStartId();
                long endId = tgParam.getEndId();
                if (doc.length() > 0) doc.append(SPLIT_SPACE);
                doc.append(PARAMS_CM).append(TRANSFORM_QUOTES).append(String.format(replaceParam, startId, endId));
                doc.append(TRANSFORM_QUOTES);
            }
        }

        if (incrementType != null && IncrementTypeEnum.PARTITION.getCode() == incrementType) {
            if (StrUtil.isNotBlank(partitionStr)) {
                List<String> partitionInfo = Arrays.asList(partitionStr.split(SPLIT_COMMA));
                if (doc.length() > 0) doc.append(SPLIT_SPACE);
                doc.append(PARAMS_CM).append(TRANSFORM_QUOTES).append(String.format(PARAMS_CM_V_PT, buildPartition(partitionInfo))).append(TRANSFORM_QUOTES);
            }
        }

        JobLogger.log("------------------Command parameters:" + doc);
        return doc.toString();
    }


    private void buildPartitionCM(StringBuilder doc, String partitionStr) {
        if (StrUtil.isNotBlank(partitionStr)) {
            doc.append(SPLIT_SPACE);
            List<String> partitionInfo = Arrays.asList(partitionStr.split(SPLIT_COMMA));
            doc.append(String.format(PARAMS_CM_V_PT, buildPartition(partitionInfo)));
        }
    }

    private static String buildPartition(List<String> partitionInfo) {
        String field = partitionInfo.get(0);
        int timeOffset = Integer.parseInt(partitionInfo.get(1));
        String timeFormat = partitionInfo.get(2);
        String partitionTime = DateUtil.format(DateUtil.addDays(new Date(), timeOffset), timeFormat);
        return field + Constants.EQUAL + partitionTime;
    }

}
