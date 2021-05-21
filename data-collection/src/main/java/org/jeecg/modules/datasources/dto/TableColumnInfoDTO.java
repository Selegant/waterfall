package org.jeecg.modules.datasources.dto;

import java.sql.ResultSet;
import java.sql.SQLException;
import lombok.Data;

@Data
public class TableColumnInfoDTO {

    private String columnName;

    private String columnType;

    private Integer columnSize;

    private Integer decimalDigits;

    private String columnDef;

    private String remarks;

    private Boolean nullable;

    private Boolean primaryKey;


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