package org.jeecg.modules.datasources.service.impl;

import cn.hutool.db.Db;
import cn.hutool.db.DbRuntimeException;
import cn.hutool.db.DbUtil;
import cn.hutool.db.ds.simple.SimpleDataSource;
import cn.hutool.db.meta.Column;
import cn.hutool.db.meta.MetaUtil;
import cn.hutool.db.meta.Table;
import cn.hutool.db.meta.TableType;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;

import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.datasources.dto.TableColumnInfoDTO;
import org.jeecg.modules.datasources.dto.WaterfallDataSourceListDTO;
import org.jeecg.modules.datasources.input.TableColumnInput;
import org.jeecg.modules.datasources.mapper.WaterfallDataSourceMapper;
import org.jeecg.modules.datasources.mapper.WaterfallDataSourceTypeMapper;
import org.jeecg.modules.datasources.model.WaterfallDataSource;
import org.jeecg.modules.datasources.model.WaterfallDataSourceType;
import org.jeecg.modules.datasources.service.IDataSourceService;
import org.jeecg.modules.datasources.util.DBUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import org.springframework.stereotype.Service;

/**
 * @author selegant
 */
@Slf4j
@Service
public class DataSourceServiceImpl implements IDataSourceService {

    @Autowired
    private WaterfallDataSourceMapper waterfallDataSourceMapper;

    @Autowired
    private WaterfallDataSourceTypeMapper waterfallDataSourceTypeMapper;

    @Override
    public void saveDataSource(WaterfallDataSource dataSource) {
        connection(dataSource);
        waterfallDataSourceMapper.insertSelective(dataSource);
    }

    @Override
    public List<WaterfallDataSourceListDTO> list(String purpose) {
       return waterfallDataSourceMapper.list(purpose);
    }

    @Override
    public List<WaterfallDataSourceType> dataSourceTypeList() {
        return waterfallDataSourceTypeMapper.selectList(new QueryWrapper<WaterfallDataSourceType>());
    }

    @Override
    public void updateDataSource(WaterfallDataSource dataSource) {
        connection(dataSource);
        waterfallDataSourceMapper.updateByPrimaryKeySelective(dataSource);
    }

    @Override
    public void deleteDataSource(List<Integer> ids) {
        waterfallDataSourceMapper.deleteBatchIds(ids);
    }

    @Override
    public void addDataSourceType(WaterfallDataSourceType dataSourceType) {
        waterfallDataSourceTypeMapper.insertSelective(dataSourceType);
    }

    @Override
    public void updateDataSourceType(WaterfallDataSourceType dataSourceType) {
        waterfallDataSourceTypeMapper.updateByPrimaryKeySelective(dataSourceType);
    }

    @Override
    public void deleteDataSourceType(List<Integer> ids) {
        waterfallDataSourceTypeMapper.deleteBatchIds(ids);
    }

    @Override
    public Boolean connection(WaterfallDataSource dataSource) {
        DataSource db = new SimpleDataSource(dataSource.getJdbcUrl(), dataSource.getUsername(), dataSource.getPassword());
        try {
            if (MetaUtil.getTables(db,dataSource.getDatabase()).size() > 0) {
                return true;
            } else {
                throw new RuntimeException("请设置具体数据库");
            }
        }catch (DbRuntimeException e){
            log.error(e.getMessage(),e);
            throw new RuntimeException("数据库连接失败");
        }

    }

    @Override
    public List<String> getTables(WaterfallDataSource dataSource) throws SQLException {
      DataSource db = new SimpleDataSource(dataSource.getJdbcUrl(), dataSource.getUsername(), dataSource.getPassword());
      return MetaUtil.getTables(db,dataSource.getDatabase());
    }

    @Override
    public List<TableColumnInfoDTO> getTableColumns(TableColumnInput input) {
      DataSource db = new SimpleDataSource(input.getJdbcUrl(), input.getUsername(), input.getPassword());
      return DBUtil.getTableMeta(db,input.getTableName(),input.getDatabase());
    }

}
