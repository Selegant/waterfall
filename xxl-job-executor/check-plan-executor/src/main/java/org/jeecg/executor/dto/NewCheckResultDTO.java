package org.jeecg.executor.dto;

import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author liansongye
 * @create 2021/9/10 3:43 下午
 */
@Data
public class NewCheckResultDTO extends CheckResultDTO{
    private List<String> fieldNames;
    private Map<String, Object> emptySums;
}
