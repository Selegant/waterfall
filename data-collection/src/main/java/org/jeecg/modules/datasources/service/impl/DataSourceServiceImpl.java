package org.jeecg.modules.datasources.service.impl;

import cn.hutool.db.DbRuntimeException;
import cn.hutool.db.DbUtil;
import cn.hutool.db.Entity;
import cn.hutool.db.ds.simple.SimpleDataSource;
import cn.hutool.db.meta.MetaUtil;
import cn.hutool.db.meta.TableType;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jeecg.modules.datasources.dto.TableColumnInfoDTO;
import org.jeecg.modules.datasources.input.TableColumnInput;
import org.jeecg.modules.datasources.mapper.WaterfallDataSourceAmountMapper;
import org.jeecg.modules.datasources.mapper.WaterfallDataSourceMapper;
import org.jeecg.modules.datasources.mapper.WaterfallDataSourceTypeMapper;
import org.jeecg.modules.datasources.model.WaterfallDataSource;
import org.jeecg.modules.datasources.model.WaterfallDataSourceAmount;
import org.jeecg.modules.datasources.model.WaterfallDataSourceType;
import org.jeecg.modules.datasources.service.IDataSourceService;
import org.jeecg.modules.datasources.service.IWaterfallDataSourceAmountService;
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

    @Autowired
    private WaterfallDataSourceAmountMapper waterfallDataSourceAmountMapper;

    @Autowired
    private IWaterfallDataSourceAmountService waterfallDataSourceAmountService;

    private static final String MYSQL = "mysql";

    private static final String ORACLE = "oracle";

    private static final String AMOUNT = "amount";

    private static final ThreadPoolExecutor POOL = new ThreadPoolExecutor(10, 20, 10, TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>(100));

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
        return waterfallDataSourceTypeMapper.selectList(new QueryWrapper<WaterfallDataSourceType>());
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
        try {
            if (getTables(dataSource).size() > 0) {
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
    public List<String> getTables(WaterfallDataSource dataSource) {
        dataSource.setJdbcUrl(concatUrl(dataSource));
        DataSource db = new SimpleDataSource(dataSource.getJdbcUrl(), dataSource.getUsername(),
                dataSource.getPassword());
        String type = dataSource.getDbType().toLowerCase();
        List<String> result = new ArrayList<>();
        if (MYSQL.equals(type)) {
            result = MetaUtil.getTables(db, dataSource.getDbName(), TableType.TABLE);
        }
        if (ORACLE.equals(type)) {
            result = MetaUtil
                    .getTables(db, dataSource.getUsername().toUpperCase(), TableType.TABLE);
        }
        result.sort(String::compareTo);
        return result;
    }

    @Override
    public List<TableColumnInfoDTO> getTableColumns(TableColumnInput input) {
        DataSource db = new SimpleDataSource(input.getJdbcUrl(), input.getUsername(),
                input.getPassword());
        return DBUtil.getTableMeta(db, input.getTableName(), input.getDatabase());
    }

    @Override
    public void asyncUpdateAmount(WaterfallDataSource dataSource) {
        List<String> tables = getTables(dataSource); // 真实数据源的表集合
        QueryWrapper<WaterfallDataSourceAmount> wrapper = new QueryWrapper<>();
        wrapper.eq("db_id", dataSource.getId());
        List<WaterfallDataSourceAmount> amounts = waterfallDataSourceAmountMapper
                .selectList(wrapper);
        // 在库里的表集合
        List<String> tablesInAmountDb = amounts.stream()
                .map(WaterfallDataSourceAmount::getTableName).collect(
                        Collectors.toList());
        List<String> removeList = new ArrayList<>();
        List<WaterfallDataSourceAmount> addList = new ArrayList<>();
        for (String tableName : tablesInAmountDb) { // 找出需要删除的表名
            if (!tables.contains(tableName)) {
                removeList.add(tableName);
            }
        }
        for (String tableName : tables) { // 找出需要添加的表名
            if (!tablesInAmountDb.contains(tableName)) {
                WaterfallDataSourceAmount model = new WaterfallDataSourceAmount();
                model.setDbId(dataSource.getId());
                model.setTableName(tableName);
                addList.add(model);
            }
        }
        QueryWrapper<WaterfallDataSourceAmount> deleteWrapper = new QueryWrapper<>();
        deleteWrapper.eq("db_id", dataSource.getId()).in("table_name", removeList);
        waterfallDataSourceAmountMapper.delete(deleteWrapper);
        waterfallDataSourceAmountMapper.insertBatch(addList);
        // 更新完之后的所有的表
        List<WaterfallDataSourceAmount> updatedTables = waterfallDataSourceAmountMapper.selectList(wrapper);
        if (updatedTables.size() < 50) {
            for (WaterfallDataSourceAmount amount : updatedTables) {
                List<WaterfallDataSourceAmount> list = new ArrayList<>();
                list.add(amount);
                POOL.execute(() -> updateAmount(dataSource, list));
            }
        } else {
            int j = updatedTables.size() / 10;
            for (int i = 0; i < 10; i++) {
                final int x = i;
                POOL.execute(() -> updateAmount(dataSource, updatedTables.subList(x * j, (x + 1) * j)));
            }
            if (updatedTables.size() % 10 != 0) {
                POOL.execute(() -> updateAmount(dataSource, updatedTables.subList(j * 10, updatedTables.size())));
            }
        }
    }

    @Override
    public List<WaterfallDataSourceAmount> getAmountList(Integer dbId) {
        QueryWrapper<WaterfallDataSourceAmount> wrapper = new QueryWrapper<>();
        wrapper.eq("db_id", dbId).orderByAsc("table_name");
        return waterfallDataSourceAmountMapper.selectList(wrapper);
    }

    private void updateAmount(WaterfallDataSource dataSource, List<WaterfallDataSourceAmount> lstAmount) {
        dataSource.setJdbcUrl(concatUrl(dataSource));
        DataSource db = new SimpleDataSource(dataSource.getJdbcUrl(), dataSource.getUsername(),
                dataSource.getPassword());
        try {
            db.getConnection();
            for (WaterfallDataSourceAmount amount : lstAmount) {
                Long start = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
                Entity entity = DbUtil.use(db).query("SELECT COUNT(*) AS " + AMOUNT + " FROM " + amount.getTableName())
                        .get(0);
                Long end = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
                amount.setAmount(Integer.valueOf(entity.get(AMOUNT).toString()));
                amount.setRequiredTime((int) (end - start));
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
            log.error("setAmount:数据库连接异常或查询异常");
        }
        waterfallDataSourceAmountService.updateBatchById(lstAmount);
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
