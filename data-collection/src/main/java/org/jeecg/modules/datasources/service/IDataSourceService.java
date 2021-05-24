package org.jeecg.modules.datasources.service;

import java.sql.SQLException;
import org.jeecg.modules.datasources.dto.TableColumnInfoDTO;
import org.jeecg.modules.datasources.dto.WaterfallDataSourceListDTO;
import org.jeecg.modules.datasources.input.TableColumnInput;
import org.jeecg.modules.datasources.model.WaterfallDataSource;
import org.jeecg.modules.datasources.model.WaterfallDataSourceType;

import java.util.List;

public interface IDataSourceService {

    void saveDataSource(WaterfallDataSource dataSource) throws Exception;

    List<WaterfallDataSource> list(String purpose);

    List<WaterfallDataSourceType> dataSourceTypeList();

    void updateDataSource(WaterfallDataSource dataSource) throws Exception;

    void deleteDataSource(List<Integer> ids);

    void addDataSourceType(WaterfallDataSourceType dataSourceType);

    void updateDataSourceType(WaterfallDataSourceType dataSourceType);

    void deleteDataSourceType(List<Integer> ids);

    Boolean connection(WaterfallDataSource dataSource) throws Exception;

    List<String> getTables(WaterfallDataSource dataSource) throws SQLException;

    List<TableColumnInfoDTO> getTableColumns(TableColumnInput input);
}
