package org.jeecg.modules.datasources.util;

import cn.hutool.db.DbRuntimeException;
import cn.hutool.db.DbUtil;
import cn.hutool.db.meta.Column;
import cn.hutool.db.meta.MetaUtil;
import cn.hutool.db.meta.Table;
import cn.hutool.db.meta.TableType;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import javax.sql.DataSource;
import org.jeecg.modules.datasources.dto.TableColumnInfoDTO;

public class DBUtil {

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

    return result;
  }

}
