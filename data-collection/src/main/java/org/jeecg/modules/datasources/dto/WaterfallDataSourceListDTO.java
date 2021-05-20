package org.jeecg.modules.datasources.dto;

import lombok.Data;
import org.jeecg.modules.datasources.model.WaterfallDataSource;

@Data
public class WaterfallDataSourceListDTO extends WaterfallDataSource {

  private String dataSourceTypeName;
}
