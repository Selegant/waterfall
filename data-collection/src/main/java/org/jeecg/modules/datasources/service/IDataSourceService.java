package org.jeecg.modules.datasources.service;

import java.sql.SQLException;
import org.jeecg.modules.datasources.dto.WaterfallDataSourceListDTO;
import org.jeecg.modules.datasources.model.WaterfallDataSource;
import org.jeecg.modules.datasources.model.WaterfallDataSourceType;

import java.util.List;

public interface IDataSourceService {

    void saveDataSource(WaterfallDataSource dataSource);

    List<WaterfallDataSourceListDTO> list(String purpose);

    List<WaterfallDataSourceType> dataSourceTypeList();

    void updateDataSource(WaterfallDataSource dataSource);

    void deleteDataSource(List<Integer> ids);

    void addDataSourceType(WaterfallDataSourceType dataSourceType);

    void updateDataSourceType(WaterfallDataSourceType dataSourceType);

    void deleteDataSourceType(List<Integer> ids);

    Boolean connection(WaterfallDataSource dataSource);

    List<String> getTables(WaterfallDataSource dataSource) throws SQLException;
}
