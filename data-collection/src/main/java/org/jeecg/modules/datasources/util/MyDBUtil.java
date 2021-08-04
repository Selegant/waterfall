package org.jeecg.modules.datasources.util;

import static com.alibaba.druid.pool.DruidDataSourceFactory.PROP_INITIALSIZE;
import static com.alibaba.druid.pool.DruidDataSourceFactory.PROP_MAXACTIVE;
import static com.alibaba.druid.pool.DruidDataSourceFactory.PROP_MAXWAIT;
import static com.alibaba.druid.pool.DruidDataSourceFactory.PROP_PASSWORD;
import static com.alibaba.druid.pool.DruidDataSourceFactory.PROP_URL;
import static com.alibaba.druid.pool.DruidDataSourceFactory.PROP_USERNAME;
import static org.jeecg.modules.datasources.constant.DataSourceConstant.MYSQL;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.db.DbRuntimeException;
import cn.hutool.db.DbUtil;
import cn.hutool.db.ds.simple.SimpleDataSource;
import cn.hutool.db.meta.MetaUtil;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jeecg.modules.datasources.dto.TableColumnInfoDTO;
import org.jeecg.modules.datasources.model.WaterfallDataSource;
import org.jetbrains.annotations.NotNull;

@Slf4j
public class MyDBUtil {

    private static final String INITIAL_SIZE = "20";

    private static final String MAX_ACTIVE = "40";

    private static final String MAX_WAIT = "3000";

    private static final String DATE = "date";

    private static List<String> decimalPoint;

    private static List<String> noNeedSize;

    static {
        // TODO 稍后完善
        decimalPoint = new ArrayList<>();
        decimalPoint.add("DOUBLE");
        decimalPoint.add("DECIMAL");
        noNeedSize = new ArrayList<>();
        noNeedSize.add("TIMESTAMP");
    }

    public static DataSource createDruidPoolByHands(WaterfallDataSource dataSource) throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put(PROP_URL, dataSource.getJdbcUrl());
        map.put(PROP_USERNAME, dataSource.getUsername());
        map.put(PROP_PASSWORD, dataSource.getPassword());
        map.put(PROP_INITIALSIZE, INITIAL_SIZE);
        map.put(PROP_MAXACTIVE, MAX_ACTIVE);
        map.put(PROP_MAXWAIT, MAX_WAIT);
        return DruidDataSourceFactory.createDataSource(map);
    }

    public static String packageDDL(List<TableColumnInfoDTO> columns, String tableName) {
        String sql = "CREATE TABLE " + tableName + " (";
        List<String> primaryKeys = new ArrayList<>();
        String columnSql = "";
        for (TableColumnInfoDTO dto : columns) {
            columnSql += dto.getColumnName() + " " + dto.getColumnType();
            if (dto.getColumnSize() != null && !noNeedSize.contains(dto.getColumnType())) {
                columnSql += " (" + dto.getColumnSize();
                if (dto.getDecimalDigits() != null && decimalPoint.contains(dto.getColumnType())) {
                    columnSql += "," + dto.getDecimalDigits() + ")";
                } else {
                    columnSql += ")";
                }
            }
            if (!dto.getNullable()) {
                columnSql += " NOT NULL";
            }
            if (StringUtils.isNotBlank(dto.getColumnDef())) {
                columnSql += " DEFAULT " + dto.getColumnDef();
            }
            if (dto.getPrimaryKey() != null && dto.getPrimaryKey()) {
                columnSql += " AUTO_INCREMENT";
                primaryKeys.add(dto.getColumnName());
            }
            if (StringUtils.isNotBlank(dto.getRemarks())) {
                columnSql += " COMMENT '" + dto.getRemarks() + "'";
            }
            columnSql += ",";
        }
        if (!primaryKeys.isEmpty()) {
            for (String key : primaryKeys) {
                columnSql += "PRIMARY KEY (" + key + "),";
            }
        }
        sql += columnSql.substring(0, columnSql.length() - 1);
        sql += ");";
        return sql;
    }

    /**
     * 获取表字段信息
     */
    public static List<TableColumnInfoDTO> getTableMeta(DataSource ds, String tableName, String database) {
        List<TableColumnInfoDTO> result = getTableColumnInfo(ds, tableName, database);
        result = CollUtil.sortByProperty(result, "columnName");

        return result;
    }

    @NotNull
    public static List<TableColumnInfoDTO> getTableColumnInfo(DataSource ds, String tableName, String database) {
        List<TableColumnInfoDTO> result = new LinkedList<>();
        Connection conn = null;
        try {
            conn = ds.getConnection();

            // catalog和schema获取失败默认使用null代替
            String catalog = MetaUtil.getCataLog(conn);
            final DatabaseMetaData metaData = conn.getMetaData();

            // 获得表元数据（表注释）
//      try (ResultSet rs = metaData
//          .getTables(catalog, database, tableName, new String[]{TableType.TABLE.value()})) {
//        if (null != rs) {
//          if (rs.next()) {
//            table.setComment(rs.getString("REMARKS"));
//          }
//        }
//      }

            Set<String> pkNames = new LinkedHashSet<>();
            // 获得主键
            try (ResultSet rs = metaData.getPrimaryKeys(catalog, database, tableName)) {
                if (null != rs) {
                    while (rs.next()) {
                        pkNames.add(rs.getString("COLUMN_NAME"));
                    }
                }
            }

            // 获得列
            try (ResultSet rs = metaData.getColumns(catalog, database, tableName, null)) {
                if (null != rs) {
                    while (rs.next()) {
//            table.setColumn(Column.create(tableName, rs));
                        TableColumnInfoDTO dto = new TableColumnInfoDTO();
                        dto.init(rs);
                        if (pkNames.contains(dto.getColumnName())) {
                            dto.setPrimaryKey(true);
                        }
                        result.add(dto);
                    }
                }
            }
        } catch (SQLException e) {
            throw new DbRuntimeException("Get columns error!", e);
        } finally {
            DbUtil.close(conn);
        }
//    result.stream().sorted(Comparator.comparing(TableColumnInfoDTO::getColumnName)).collect(Collectors.toList());
        result.forEach(c -> {
            c.setIncColumn(0);
            if (!Objects.isNull(c.getPrimaryKey()) && c.getPrimaryKey()) {
                c.setIncColumn(1);
            }
            if (c.getColumnType().toLowerCase(Locale.ROOT).contains(DATE)) {
                c.setIncColumn(1);
            }
        });
        return result;
    }

}
