package org.jeecg.executor.dto.check;

import lombok.Data;

import java.util.Map;

/*
    多字段比较检测实体类
    批量检查模型选中列符合比较检测的数据信息
 */
@Data
public class CheckCompareWithFields extends BaseCheckResult{
    // 比较规则表达式
    Map<String, String> compareExpressions;
}
