package org.jeecg.modules.datasources.input;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Detective
 * @date Created in 2021/8/31
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WebsocketLogPageInput {

    private String jobName;

    private Integer pageNo;

    private Integer pageSize;
}
