package org.jeecg.modules.datasources.util;

import static com.alibaba.druid.pool.DruidDataSourceFactory.PROP_INITIALSIZE;
import static com.alibaba.druid.pool.DruidDataSourceFactory.PROP_MAXACTIVE;
import static com.alibaba.druid.pool.DruidDataSourceFactory.PROP_MAXWAIT;
import static com.alibaba.druid.pool.DruidDataSourceFactory.PROP_PASSWORD;
import static com.alibaba.druid.pool.DruidDataSourceFactory.PROP_URL;
import static com.alibaba.druid.pool.DruidDataSourceFactory.PROP_USERNAME;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.db.DbRuntimeException;
import cn.hutool.db.DbUtil;
import cn.hutool.db.meta.Column;
import cn.hutool.db.meta.MetaUtil;
import cn.hutool.db.meta.Table;
import cn.hutool.db.meta.TableType;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;
import javax.sql.DataSource;
import org.jeecg.modules.datasources.dto.TableColumnInfoDTO;
import org.jeecg.modules.datasources.model.WaterfallDataSource;

public class MyDBUtil {

  private static final String INITIAL_SIZE = "20";

  private static final String MAX_ACTIVE = "40";

  private static final String MAX_WAIT = "3000";

  private static final String DATE = "date";

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

  public static List<TableColumnInfoDTO> getTableMeta(DataSource ds, String tableName, String database) {
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
            if(pkNames.contains(dto.getColumnName())){
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
    result = CollUtil.sortByProperty(result,"columnName");
    result.forEach(c->{
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
