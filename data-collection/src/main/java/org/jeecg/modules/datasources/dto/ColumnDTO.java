package org.jeecg.modules.datasources.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author liansongye
 * @create 2021/8/4 10:41 上午
 */
@NoArgsConstructor
@Data
public class ColumnDTO {

    private String fieldName;
    private Integer fieldTypeId;
    private String fieldTypeName;
    private Integer length;
    private Boolean primarykeyFlag;
    private Boolean emptyFlag;
    private String remark;


    public ColumnDTO(String fieldName, Integer fieldTypeId, String fieldTypeName, Integer length, Boolean primarykeyFlag, Boolean emptyFlag, String remark) {
        this.fieldName = fieldName;
        this.fieldTypeId = fieldTypeId;
        this.fieldTypeName = fieldTypeName;
        this.length = length;
        this.primarykeyFlag = primarykeyFlag;
        this.emptyFlag = emptyFlag;
        this.remark = remark;
    }
}
