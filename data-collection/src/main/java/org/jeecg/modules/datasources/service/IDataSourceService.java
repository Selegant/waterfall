package org.jeecg.modules.datasources.service;

import org.jeecg.modules.datasources.model.WaterfallDataSource;
import org.jeecg.modules.datasources.model.WaterfallDataSourceType;

import java.util.List;

public interface IDataSourceService {

    void saveDataSource(WaterfallDataSource dataSource);

    List<WaterfallDataSource> list();

    List<WaterfallDataSourceType> dataSourceTypeList();
}
