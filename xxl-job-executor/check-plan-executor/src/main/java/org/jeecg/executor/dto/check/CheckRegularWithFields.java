package org.jeecg.executor.dto.check;

import lombok.Data;

import java.util.Map;

/*
    多字段正则检测实体类
    批量检查模型选中列符合正则检测的数据信息
 */
@Data
public class CheckRegularWithFields extends BaseCheckResult {
    // 正则表达式
    Map<String, String> regularExpression;
}
