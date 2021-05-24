package org.jeecg.modules.datasources.service.impl;

import cn.hutool.db.DbRuntimeException;
import cn.hutool.db.ds.simple.SimpleDataSource;
import cn.hutool.db.meta.MetaUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jeecg.modules.datasources.dto.TableColumnInfoDTO;
import org.jeecg.modules.datasources.input.TableColumnInput;
import org.jeecg.modules.datasources.mapper.WaterfallDataSourceMapper;
import org.jeecg.modules.datasources.mapper.WaterfallDataSourceTypeMapper;
import org.jeecg.modules.datasources.model.WaterfallDataSource;
import org.jeecg.modules.datasources.model.WaterfallDataSourceType;
import org.jeecg.modules.datasources.service.IDataSourceService;
import org.jeecg.modules.datasources.util.DBUtil;
import org.springframework.beans.factory.annotation.Autowired;
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

    private static final String MYSQL = "mysql";
    private static final String ORACLE = "oracle";

    @Override
    public void saveDataSource(WaterfallDataSource dataSource) throws Exception {
        dataSource.setJdbcUrl(concatUrl(dataSource));
        connection(dataSource);
        waterfallDataSourceMapper.insertSelective(dataSource);
    }

    @Override
    public List<WaterfallDataSource> list(String purpose) {
        QueryWrapper<WaterfallDataSource> wrapper = new QueryWrapper<>();
        wrapper.eq("purpose", purpose);
        return waterfallDataSourceMapper.selectList(wrapper);
    }

    @Override
    public List<WaterfallDataSourceType> dataSourceTypeList() {
        return waterfallDataSourceTypeMapper
                .selectList(new QueryWrapper<WaterfallDataSourceType>());
    }

    @Override
    public void updateDataSource(WaterfallDataSource dataSource) throws Exception {
        dataSource.setJdbcUrl(concatUrl(dataSource));
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
    public Boolean connection(WaterfallDataSource dataSource) throws Exception {
        dataSource.setJdbcUrl(concatUrl(dataSource));
        DataSource db = new SimpleDataSource(dataSource.getJdbcUrl(), dataSource.getUsername(),
                dataSource.getPassword());
        try {
            if (MetaUtil.getTables(db, dataSource.getDbName()).size() > 0) {
                return true;
            } else {
                throw new RuntimeException("请设置具体数据库");
            }
        } catch (DbRuntimeException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException("数据库连接失败");
        }

    }

    @Override
    public List<String> getTables(WaterfallDataSource dataSource) throws SQLException {
        dataSource.setJdbcUrl(concatUrl(dataSource));
        DataSource db = new SimpleDataSource(dataSource.getJdbcUrl(), dataSource.getUsername(),
                dataSource.getPassword());
        if (MYSQL.equals(dataSource.getDbType())) {
            return MetaUtil.getTables(db, dataSource.getDbName());
        }
        if (ORACLE.equals(dataSource.getDbType())) {
            return MetaUtil.getTables(db, dataSource.getServerName());
        }
        return new ArrayList<>();
    }

    @Override
    public List<TableColumnInfoDTO> getTableColumns(TableColumnInput input) {
        DataSource db = new SimpleDataSource(input.getJdbcUrl(), input.getUsername(),
                input.getPassword());
        return DBUtil.getTableMeta(db, input.getTableName(), input.getDatabase());
    }

    private String concatUrl(WaterfallDataSource dataSource) throws RuntimeException {
        if (!StringUtils.isNotBlank(dataSource.getDbType())) {
            throw new RuntimeException("dbType不能为空");
        }
        String type = dataSource.getDbType().toLowerCase();
        if (MYSQL.equals(type)) {
            String mysqlUrl = "jdbc:mysql://%s:%s/%s";
            return String.format(mysqlUrl, dataSource.getHost(), dataSource.getPort(),
                    dataSource.getDbName());
        }
        if (ORACLE.equals(type)) {
            String oracleUrl = "jdbc:oracle:thin:@%s:%s/%s";
            return String.format(oracleUrl, dataSource.getHost(), dataSource.getPort(),
                    dataSource.getServerName());
        }
        return "";
    }

}
