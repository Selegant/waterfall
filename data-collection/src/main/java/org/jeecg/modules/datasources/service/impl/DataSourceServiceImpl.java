package org.jeecg.modules.datasources.service.impl;

import cn.hutool.db.DbRuntimeException;
import cn.hutool.db.DbUtil;
import cn.hutool.db.Entity;
import cn.hutool.db.ds.simple.SimpleDataSource;
import cn.hutool.db.meta.MetaUtil;
import cn.hutool.db.meta.TableType;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jeecg.modules.datasources.dto.DatabaseTreeDTO;
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
import org.jeecg.modules.datasources.util.MyDBUtil;
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

    private static final int TABLE = 1;

    private static final int VIEW = 2;

    private static final ThreadPoolExecutor POOL = new ThreadPoolExecutor(20, 30, 10, TimeUnit.SECONDS,
            new ArrayBlockingQueue<Runnable>(100));

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
    public List<String> getTables(Integer dbId, Integer typeId) {
        return getTables(waterfallDataSourceMapper.selectByPrimaryKey(dbId), typeId);
    }

    public List<String> getTables(WaterfallDataSource dataSource, Integer typeId) {
        dataSource.setJdbcUrl(concatUrl(dataSource));
        DataSource db = new SimpleDataSource(dataSource.getJdbcUrl(), dataSource.getUsername(),
                dataSource.getPassword());
        String type = dataSource.getDbType().toLowerCase();
        List<String> result = new ArrayList<>();
        if (MYSQL.equals(type)) {
            if(typeId==0){
                result.addAll(MetaUtil.getTables(db, dataSource.getDbName(), TableType.TABLE));
                result.addAll(MetaUtil.getTables(db, dataSource.getDbName(), TableType.VIEW));
            }
            if(typeId==1){
                result = MetaUtil.getTables(db, dataSource.getDbName(), TableType.TABLE);
            }
            if (typeId == 2) {
                result = MetaUtil.getTables(db, dataSource.getDbName(), TableType.VIEW);
            }
        }
        if (ORACLE.equals(type)) {
            if(typeId==0){
                result.addAll(MetaUtil
                        .getTables(db, dataSource.getUsername().toUpperCase(), TableType.TABLE));
                result.addAll(MetaUtil
                        .getTables(db, dataSource.getUsername().toUpperCase(), TableType.VIEW));
            }
            if(typeId==1){
                result = MetaUtil
                        .getTables(db, dataSource.getUsername().toUpperCase(), TableType.TABLE);
            }
            if (typeId == 2) {
                result = MetaUtil.getTables(db, dataSource.getUsername().toUpperCase(), TableType.VIEW);
            }
        }
        result.sort(String::compareTo);
        return result;
    }

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
            result = MetaUtil.getTables(db, dataSource.getUsername().toUpperCase(), TableType.TABLE);
        }
        result.sort(String::compareTo);
        return result;
    }

    @Override
    public List<TableColumnInfoDTO> getTableColumns(TableColumnInput input) {
        WaterfallDataSource waterfall = waterfallDataSourceMapper.selectById(input.getId());
        DataSource db = new SimpleDataSource(waterfall.getJdbcUrl(), waterfall.getUsername(), waterfall.getPassword());
        String serverName = "";
        if (StringUtils.isNotBlank(waterfall.getDbType())) {
            if (MYSQL.equals(StringUtils.lowerCase(waterfall.getDbType()))) {
                serverName = waterfall.getDbName();
            }
            if (ORACLE.equals(StringUtils.lowerCase(waterfall.getDbType()))) {
                serverName = StringUtils.upperCase(waterfall.getUsername());
            }
        }
        return MyDBUtil.getTableMeta(db, input.getTableName(), serverName);
    }

    @Override
    public void asyncUpdateAmount(Integer dbId, Integer type) throws Exception {
        WaterfallDataSource dataSource = waterfallDataSourceMapper.selectByPrimaryKey(dbId);
        List<String> tables = getTables(dataSource, type); // 真实数据源的表集合
        QueryWrapper<WaterfallDataSourceAmount> wrapper = new QueryWrapper<>();
        wrapper.eq("db_id", dataSource.getId());
        wrapper.eq("type", type);
        List<WaterfallDataSourceAmount> amounts = waterfallDataSourceAmountMapper
                .selectList(wrapper);
        // 在库里的表集合
        List<String> tablesInAmountDb = amounts.stream().map(WaterfallDataSourceAmount::getTableName)
                .collect(Collectors.toList());
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
        if (!removeList.isEmpty()) {
            QueryWrapper<WaterfallDataSourceAmount> deleteWrapper = new QueryWrapper<>();
            deleteWrapper.eq("db_id", dataSource.getId()).in("table_name", removeList);
            waterfallDataSourceAmountMapper.delete(deleteWrapper);
        }
        if (!addList.isEmpty()) {
            waterfallDataSourceAmountMapper.insertBatch(addList, type);
        }
        // 更新完之后的所有的表
        List<WaterfallDataSourceAmount> updatedTables = waterfallDataSourceAmountMapper.selectList(wrapper);

        DataSource ds = MyDBUtil.createDruidPoolByHands(dataSource); // 手动创建连接池

        if (updatedTables.size() < 50) {
            for (WaterfallDataSourceAmount amount : updatedTables) {
                List<WaterfallDataSourceAmount> list = new ArrayList<>();
                list.add(amount);
                POOL.execute(() -> updateAmount(ds, list));
            }
        } else {
            int j = updatedTables.size() / 10;
            for (int i = 0; i < 10; i++) {
                final int x = i;
                POOL.execute(() -> updateAmount(ds, updatedTables.subList(x * j, (x + 1) * j)));
            }
            if (updatedTables.size() % 10 != 0) {
                POOL.execute(() -> updateAmount(ds, updatedTables.subList(j * 10, updatedTables.size())));
            }
        }
    }

    @Override
    public List<WaterfallDataSourceAmount> getAmountList(Integer dbId, Integer type) {
        QueryWrapper<WaterfallDataSourceAmount> wrapper = new QueryWrapper<>();
        wrapper.eq("db_id", dbId).orderByAsc("table_name");
        wrapper.eq("type", type);
        return waterfallDataSourceAmountMapper.selectList(wrapper);
    }

    @Override
    public DatabaseTreeDTO treeList(String purpose) {
        QueryWrapper<WaterfallDataSource> wrapper = new QueryWrapper<>();
        wrapper.eq("purpose", purpose);
        List<WaterfallDataSource> list = waterfallDataSourceMapper.selectList(wrapper);
        DatabaseTreeDTO databaseTree = new DatabaseTreeDTO();
        List<DatabaseTreeDTO.Tree> trees = new ArrayList<>();
        list.forEach(s -> {
            DatabaseTreeDTO.Tree db = new DatabaseTreeDTO.Tree();
            db.setTitle(s.getDataSourceName());
            db.setKey(s.getId().toString());
            JSONObject object = new JSONObject();
            object.put("icon", s.getDbType());
            db.setSlots(object);
            List<DatabaseTreeDTO.Tree> childrenTree = new ArrayList<>();
            DatabaseTreeDTO.Tree table = new DatabaseTreeDTO.Tree();
            table.setTitle("Table");
            table.setKey(s.getId() + "-" + "1");
            object = new JSONObject();
            object.put("icon", "table");
            table.setSlots(object);
            childrenTree.add(table);
            table = new DatabaseTreeDTO.Tree();
            table.setTitle("View");
            table.setKey(s.getId() + "-" + "2");
            object = new JSONObject();
            object.put("icon", "view");
            table.setSlots(object);
            childrenTree.add(table);
            db.setChildren(childrenTree);
            trees.add(db);
        });
        databaseTree.setTrees(trees);
        return databaseTree;
    }

    private void updateAmount(DataSource dataSource, List<WaterfallDataSourceAmount> lstAmount) {
        try {
            for (WaterfallDataSourceAmount amount : lstAmount) {
                Long start = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
                Entity entity = DbUtil.use(dataSource)
                        .query("SELECT COUNT(*) AS " + AMOUNT + " FROM " + amount.getTableName()).get(0);
                Long end = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
                amount.setAmount(Integer.valueOf(entity.get(AMOUNT).toString()));
                amount.setRequiredTime((int) (end - start));
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
            log.error("setAmount:数据库连接异常或查询异常");
        } finally {
            DbUtil.close();
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
