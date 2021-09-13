package org.jeecg.executor.dto;

import lombok.Data;

/**
 * @Author liansongye
 * @create 2021/9/10 3:43 下午
 */
@Data
public class CheckResultDTO {
    private String fieldName;
    private String fiedlType;
    private Long total;
    private Long emptySum;
    private Long regularSum;
    private Long compareSum;

    private String regularExpression;
    private String compareExpression;
}
