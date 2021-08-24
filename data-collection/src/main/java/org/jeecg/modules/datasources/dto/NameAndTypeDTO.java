package org.jeecg.modules.datasources.dto;

import lombok.Builder;
import lombok.Data;

/**
 * @author Detective
 * @date Created in 2021/8/24
 */
@Data
@Builder
public class NameAndTypeDTO {

    private String name;

    private String type;
}
