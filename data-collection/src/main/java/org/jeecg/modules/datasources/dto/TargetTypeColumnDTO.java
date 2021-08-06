package org.jeecg.modules.datasources.dto;

import lombok.Data;

@Data
public class TargetTypeColumnDTO {

    private String sourceColumnName;

    private String sourceColumnType;

    private String targetColumnType;

    private String columnComment;

}
