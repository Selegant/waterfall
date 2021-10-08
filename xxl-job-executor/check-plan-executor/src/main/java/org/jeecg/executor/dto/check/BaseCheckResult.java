package org.jeecg.executor.dto.check;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class BaseCheckResult {
    // 检测统计总和
    Long total;
    // 字段列表
    List<String> fieldNames;
    // 字段类型列表s
    Map<String, Object> fiedlTypes;
    // 控制检查结果，对应字段列表
    Map<String, Object> resultSums;
}
