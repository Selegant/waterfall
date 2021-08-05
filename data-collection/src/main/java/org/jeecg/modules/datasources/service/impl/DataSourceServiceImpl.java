package org.jeecg.modules.datasources.service.impl;

import static org.jeecg.modules.datasources.constant.DataSourceConstant.AMOUNT;
import static org.jeecg.modules.datasources.constant.DataSourceConstant.HIVE;
import static org.jeecg.modules.datasources.constant.DataSourceConstant.HIVE_DRIVER;
import static org.jeecg.modules.datasources.constant.DataSourceConstant.MYSQL;
import static org.jeecg.modules.datasources.constant.DataSourceConstant.ORACLE;
import static org.jeecg.modules.datasources.constant.DataSourceConstant.TYPE_TABLE;
import static org.jeecg.modules.datasources.constant.DataSourceConstant.TYPE_TABLE_VIEW;
import static org.jeecg.modules.datasources.constant.DataSourceConstant.TYPE_VIEW;
import static org.jeecg.modules.datasources.util.MyDBUtil.getTableColumnInfo;
import static org.jeecg.modules.datasources.util.MyDBUtil.packageDDL;

import cn.hutool.db.DbRuntimeException;
import cn.hutool.db.DbUtil;
import cn.hutool.db.ds.simple.SimpleDataSource;
import cn.hutool.db.meta.MetaUtil;
import cn.hutool.db.meta.TableType;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
import org.jeecg.modules.datasources.dto.TargetTypeColumnDTO;
import org.jeecg.modules.datasources.input.TableColumnInput;
import org.jeecg.modules.datasources.mapper.WaterfallDataSourceAmountMapper;
import org.jeecg.modules.datasources.mapper.WaterfallDataSourceMapper;
import org.jeecg.modules.datasources.mapper.WaterfallDataSourceTypeMapper;
import org.jeecg.modules.datasources.model.WaterfallDataSource;
import org.jeecg.modules.datasources.model.WaterfallDataSourceAmount;
import org.jeecg.modules.datasources.model.WaterfallDataSourceType;
import org.jeecg.modules.datasources.service.IDataSourceService;
import org.jeecg.modules.datasources.service.IWaterfallDataSourceAmountService;
import org.jeecg.modules.datasources.util.DataTypeUtil;
import org.jeecg.modules.datasources.util.MyDBUtil;
import org.jeecg.modules.datasources.util.MyDatasourcePoolUtil;
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

    @Autowired
    private MyDatasourcePoolUtil myDatasourcePoolUtil;

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
        if (MYSQL.equals(type) || HIVE.equals(type)) {
            if (TYPE_TABLE_VIEW.equals(typeId)) {
                result.addAll(MetaUtil.getTables(db, dataSource.getDbName(), TableType.TABLE));
                result.addAll(MetaUtil.getTables(db, dataSource.getDbName(), TableType.VIEW));
            }
            if (TYPE_TABLE.equals(typeId)) {
                result = MetaUtil.getTables(db, dataSource.getDbName(), TableType.TABLE);
            }
            if (TYPE_VIEW.equals(typeId)) {
                result = MetaUtil.getTables(db, dataSource.getDbName(), TableType.VIEW);
            }
        }
        if (ORACLE.equals(type)) {
            if (TYPE_TABLE_VIEW.equals(typeId)) {
                result.addAll(MetaUtil
                        .getTables(db, dataSource.getUsername().toUpperCase(), TableType.TABLE));
                result.addAll(MetaUtil
                        .getTables(db, dataSource.getUsername().toUpperCase(), TableType.VIEW));
            }
            if (TYPE_TABLE.equals(typeId)) {
                result = MetaUtil.getTables(db, dataSource.getUsername().toUpperCase(), TableType.TABLE);
            }
            if (TYPE_VIEW.equals(typeId)) {
                result = MetaUtil.getTables(db, dataSource.getUsername().toUpperCase(), TableType.VIEW);
            }
        }
        result.sort(String::compareTo);
        return result;
    }


    public List<String> getTables(WaterfallDataSource dataSource) {
        dataSource.setJdbcUrl(concatUrl(dataSource));
        DataSource db;
        String type = dataSource.getDbType().toLowerCase();
        if (HIVE.equals(type)) {
            db = new SimpleDataSource(dataSource.getJdbcUrl(), dataSource.getUsername(), dataSource.getPassword(),
                    HIVE_DRIVER);
        } else {
            db = new SimpleDataSource(dataSource.getJdbcUrl(), dataSource.getUsername(), dataSource.getPassword());
        }
        List<String> result = new ArrayList<>();
        if (MYSQL.equals(type) || HIVE.equals(type)) {
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
        String type = StringUtils.lowerCase(waterfall.getDbType());
        if (StringUtils.isNotBlank(waterfall.getDbType())) {
            if (MYSQL.equals(type) || HIVE.equals(type)) {
                serverName = waterfall.getDbName();
            }
            if (ORACLE.equals(type)) {
                serverName = StringUtils.upperCase(waterfall.getUsername());
            }
        }
        return MyDBUtil.getTableMeta(db, input.getTableName(), serverName);
    }

    @Override
    public String getCreateDdl(TableColumnInput input) {
        WaterfallDataSource waterfall = waterfallDataSourceMapper.selectById(input.getId());
        DataSource db = new SimpleDataSource(waterfall.getJdbcUrl(), waterfall.getUsername(), waterfall.getPassword());
        String serverName = "";
        String type = StringUtils.lowerCase(waterfall.getDbType());
        if (StringUtils.isNotBlank(waterfall.getDbType())) {
            if (MYSQL.equals(type) || HIVE.equals(type)) {
                serverName = waterfall.getDbName();
            }
            if (ORACLE.equals(type)) {
                serverName = StringUtils.upperCase(waterfall.getUsername());
            }
        }
        String res = "";
        Connection conn = null;
        try {
            conn = db.getConnection();
            String sql = "show create table " + "`" + serverName + "`." + "`" + input.getTableName() + "`;";
            ResultSet resultSet = conn.createStatement().executeQuery(sql);
            resultSet.next();
            res = (String) resultSet.getObject(resultSet.getMetaData().getColumnLabel(2));
        } catch (SQLException e) {
            throw new DbRuntimeException("Get ddl error!", e);
        } finally {
            DbUtil.close(conn);
        }

        return res;
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
                Connection connection = myDatasourcePoolUtil.getConnection(ds);
                POOL.execute(() -> updateAmount(connection, list));
            }
        } else {
            int j = updatedTables.size() / 10;
            for (int i = 0; i < 10; i++) {
                final int x = i;
                Connection connection = myDatasourcePoolUtil.getConnection(ds);
                POOL.execute(() -> updateAmount(connection, updatedTables.subList(x * j, (x + 1) * j)));
            }
            if (updatedTables.size() % 10 != 0) {
                Connection connection = myDatasourcePoolUtil.getConnection(ds);
                POOL.execute(() -> updateAmount(connection, updatedTables.subList(j * 10, updatedTables.size())));
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

    @Override
    public List<TargetTypeColumnDTO> getTargetTypeColumns(Integer sourceId, Integer targetId, String typeName) {
        WaterfallDataSource waterfall = waterfallDataSourceMapper.selectById(sourceId);
        List<TableColumnInfoDTO> sourceColumns = getTableColumns(
                TableColumnInput.builder().id(sourceId).tableName(typeName).build());
        return DataTypeUtil.transformDataType(waterfall.getDbType(), sourceColumns);
    }

    private void updateAmount(Connection connection, List<WaterfallDataSourceAmount> lstAmount) {
        try {
            for (WaterfallDataSourceAmount amount : lstAmount) {
                Long start = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
                PreparedStatement ps = connection
                        .prepareStatement("SELECT COUNT(*) AS " + AMOUNT + " FROM " + amount.getTableName());
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    amount.setAmount(rs.getInt(AMOUNT));
                }
                Long end = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
                amount.setRequiredTime((int) (end - start));
//                log.info(amount.getTableName() + ":" + amount.getAmount() + "-" + amount.getRequiredTime());
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
            log.error("setAmount:数据库连接异常或查询异常");
        } finally {
            DbUtil.close();
        }
        waterfallDataSourceAmountService.updateBatchById(lstAmount);
    }

    @Override
    public void createHiveTable(Integer dbId, String sql){
        try {
            WaterfallDataSource dataSource = waterfallDataSourceMapper.selectByPrimaryKey(dbId);
            DataSource ds = MyDBUtil.createDruidPoolByHands(dataSource);
            Connection connection = myDatasourcePoolUtil.getConnection(ds);
            PreparedStatement ps = null;
            ps = connection.prepareStatement(sql);
            ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            DbUtil.close();
        }
    }

    /*private void updateAmount(DataSource dataSource, List<WaterfallDataSourceAmount> lstAmount) {
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
    }*/

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
        if (HIVE.equals(type)) {
            String hiveUrl = "jdbc:hive2://%s:%s/%s";
            return String.format(hiveUrl, dataSource.getHost(), dataSource.getPort(),
                    dataSource.getDbName());
        }
        return "";
    }
}
