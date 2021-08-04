package org.jeecg.modules.datasources.dto;

import lombok.Data;
import org.jeecg.modules.datasources.model.WaterfallModelPartition;

import java.util.List;

/**
 * @Author liansongye
 * @create 2021/8/4 10:10 上午
 */
@Data
public class DdlDto {
    private String dbName;
    private String tableName;
    private List<ColumnDTO> coloumns;
    private List<WaterfallModelPartition> partitions;
}
