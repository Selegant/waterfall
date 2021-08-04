package org.jeecg.modules.datasources.dto;

import java.sql.ResultSet;
import java.sql.SQLException;
import lombok.Data;

@Data
public class TableColumnInfoDTO {

    private String columnName;

    private String columnType;

    private Integer columnSize;

    // 小数位数
    private Integer decimalDigits;

    // 默认值
    private String columnDef;

    private String remarks;

    // 是否运行空值
    private Boolean nullable;

    private Boolean primaryKey;

    /**
     * 是否为增量字段 0否 1是
     */
    private Integer incColumn;


    public void init(ResultSet columnMetaRs) throws SQLException {

        this.columnName = columnMetaRs.getString("COLUMN_NAME");
        this.columnType = columnMetaRs.getString("TYPE_NAME");
        this.columnSize = columnMetaRs.getInt("COLUMN_SIZE");
        this.nullable = columnMetaRs.getBoolean("NULLABLE");
        this.remarks = columnMetaRs.getString("REMARKS");
        this.decimalDigits = columnMetaRs.getInt("DECIMAL_DIGITS");
        this.columnDef = columnMetaRs.getString("COLUMN_DEF");
    }
}
