package org.jeecg.modules.datasources.service;

import java.sql.SQLException;

import org.jeecg.modules.datasources.dto.DatabaseTreeDTO;
import org.jeecg.modules.datasources.dto.TableColumnInfoDTO;
import org.jeecg.modules.datasources.dto.TargetTypeColumnDTO;
import org.jeecg.modules.datasources.dto.WaterfallDataSourceListDTO;
import org.jeecg.modules.datasources.input.CreateHiveTableInput;
import org.jeecg.modules.datasources.input.TableColumnInput;
import org.jeecg.modules.datasources.model.WaterfallDataSource;
import org.jeecg.modules.datasources.model.WaterfallDataSourceAmount;
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

    List<String> getTables(Integer dbId,Integer typeId);

    List<TableColumnInfoDTO> getTableColumns(TableColumnInput input);

    void asyncUpdateAmount(Integer dbId, Integer type) throws Exception;

    List<WaterfallDataSourceAmount> getAmountList(Integer dbId, Integer type);

    DatabaseTreeDTO treeList(String purpose);

    List<TargetTypeColumnDTO> getTargetTypeColumns(Integer sourceId, Integer targetId, String typeName);

    String getCreateDdl(TableColumnInput input);

    void createHiveTable(Integer dbId, String... sqls);

    List<String> getColumnType(String dbType);

    void createHiveTableByInput(CreateHiveTableInput input);

    String getDbType(Integer dbId);
}
