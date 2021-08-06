package org.jeecg.modules.datasources.input;

import java.util.List;
import lombok.Data;
import org.jeecg.modules.datasources.dto.TargetTypeColumnDTO;

@Data
public class CreateHiveTableInput {

    private Integer dbId;

    private String tableName;

    private List<TargetTypeColumnDTO> columns;
}
