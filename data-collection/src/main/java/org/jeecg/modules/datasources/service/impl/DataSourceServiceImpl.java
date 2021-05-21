package org.jeecg.modules.datasources.service.impl;

import cn.hutool.db.ds.simple.SimpleDataSource;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.jeecg.modules.datasources.dto.WaterfallDataSourceListDTO;
import org.jeecg.modules.datasources.mapper.WaterfallDataSourceMapper;
import org.jeecg.modules.datasources.mapper.WaterfallDataSourceTypeMapper;
import org.jeecg.modules.datasources.model.WaterfallDataSource;
import org.jeecg.modules.datasources.model.WaterfallDataSourceType;
import org.jeecg.modules.datasources.service.IDataSourceService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import org.springframework.stereotype.Service;

/**
 * @author selegant
 */
@Service
public class DataSourceServiceImpl implements IDataSourceService {

    @Autowired
    private WaterfallDataSourceMapper waterfallDataSourceMapper;

    @Autowired
    private WaterfallDataSourceTypeMapper waterfallDataSourceTypeMapper;

    @Override
    public void saveDataSource(WaterfallDataSource dataSource) {
        DataSource db = new SimpleDataSource(dataSource.getJdbcUrl(), dataSource.getUsername(), dataSource.getPassword());
        try {
            db.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("连接不通过");
        }
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
        DataSource db = new SimpleDataSource(dataSource.getJdbcUrl(), dataSource.getUsername(), dataSource.getPassword());
        try {
            db.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("连接不通过");
        }
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
            db.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("连接不通过");
        }
        return true;
    }

}
