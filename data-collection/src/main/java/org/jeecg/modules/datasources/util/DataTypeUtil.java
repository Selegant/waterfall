package org.jeecg.modules.datasources.util;

import com.alibaba.fastjson.JSONObject;

import java.util.*;
import org.jeecg.modules.datasources.dto.TableColumnInfoDTO;
import org.jeecg.modules.datasources.dto.TargetTypeColumnDTO;

/**
 * @author selegant
 */
public class DataTypeUtil {

    public final static Map<String, String> MYSQL_HIVE =new HashMap<String, String>() {
        {
            put("TINYINT","TINYINT");
            put("SMALLINT","SMALLINT");
            put("MEDIUMINT","INT");
            put("INT","INT");
            put("BIGINT","BIGINT");
            put("FLOAT","FLOAT");
            put("DOUBLE","DOUBLE");
            put("DECIMAL","DECIMAL");
            put("NUMERIC","DECIMAL");
            put("VARCHAR","STRING");
            put("CHAR","STRING");
            put("TIMESTAMP","TIMESTAMP");
            put("DATETIME","DATE");
        }
    };



    public final static Map<String, String> ORACLE_HIVE =new HashMap<String, String>() {
        {
            put("NUMBER","INT");
            put("BINARY_FLOAT","FLOAT");
            put("BINARY_DOUBLE","DOUBLE");
            put("VARCHAR","STRING");
            put("CHAR","STRING");
            put("RAW","BINARY");
            put("TIMESTAMP","TIMESTAMP");
            put("DATE","DATE");
        }
    };



    public final static Map<String, Map<String, String>> DATA_MAPPING =new HashMap<String, Map<String, String>>() {
        {
            put("Mysql",MYSQL_HIVE);
            put("Oracle",ORACLE_HIVE);
        }
    };


    public static List<JSONObject> parseDataType(String dbType, List<JSONObject> list) {
        Map<String, String> mapping = DATA_MAPPING.get(dbType);
        list.forEach(s -> {
            s.put("targetType", mapping.get(s.getString("originalType").toUpperCase(Locale.ROOT)));
        });
        return list;
    }

    public static List<TargetTypeColumnDTO> transformDataType(String dbType, List<TableColumnInfoDTO> sourceColumns) {
        Map<String, String> mapping = DATA_MAPPING.get(dbType);
        List<TargetTypeColumnDTO> result = new ArrayList<>();
        sourceColumns.forEach(e -> {
            String type = mapping.get(e.getColumnType());
            TargetTypeColumnDTO dto = new TargetTypeColumnDTO();
            dto.setSourceColumnName(e.getColumnName());
            dto.setSourceColumnType(e.getColumnType());
            dto.setTargetColumnType(type);
            result.add(dto);
        });
        return result;
    }

}
