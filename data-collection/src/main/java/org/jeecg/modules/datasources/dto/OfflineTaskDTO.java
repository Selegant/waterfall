package org.jeecg.modules.datasources.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author selegant
 */
@NoArgsConstructor
@Data
public class OfflineTaskDTO {


    private String name;
    private String desc;
    private Integer originalId;
    private String originalTable;
    private Integer collectionType;
    private Integer targetId;
    private String targetTable;
    private List<MappingColumnsBean> mappingColumns;
    private String incColumn;

    @NoArgsConstructor
    @Data
    public static class MappingColumnsBean {
        private String originalColumn;
        private String targetColumn;
        private Integer originalColumnIndex;
        private Integer targetColumnIndex;
    }
}
