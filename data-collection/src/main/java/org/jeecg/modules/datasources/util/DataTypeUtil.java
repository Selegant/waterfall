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
            put("BIGINT","BIGINT");
            put("INT","INT");
            put("INT UNSIGNED","INT");
            put("INTEGER","INT");
            put("BIT","TINYINT");
            put("TINYINT","TINYINT");
            put("SMALLINT","SMALLINT");
            put("MEDIUMINT","BIGINT");
            put("DECIMAL","DECIMAL");
            put("FLOAT","DOUBLE");
            put("DOUBLE","DOUBLE");
            put("BINARY","BINARY");
            put("CHAR","STRING");
            put("VARCHAR","STRING");
            put("MEDIUMTEXT","STRING");
            put("TEXT","STRING");
            put("LONGTEXT","STRING");
            put("BLOB","STRING");
            put("DATETIME","TIMESTAMP");
            put("TIME","TIMESTAMP");
            put("TIMESTAMP","TIMESTAMP");
            put("DATE","TIMESTAMP");
            put("JSON","MAP<STRING,STRING>");
        }
    };



    public final static Map<String, String> ORACLE_HIVE =new HashMap<String, String>() {
        {
            put("INTEGER","DOUBLE");
            put("FLOAT","DOUBLE");
            put("BINARY_FLOAT","DOUBLE");
            put("BINARY_DOUBLE","DOUBLE");
            put("DATE","TIMESTAMP");
            put("TIMESTAMP","TIMESTAMP");
            put("CHAR","STRING");
            put("NCHAR","STRING");
            put("VARCHAR","STRING");
            put("VARCHAR2","STRING");
            put("NVARCHAR","STRING");
            put("NVARCHAR2","STRING");
            put("RAW","BINARY");
            put("BLOB","STRING");

            //NUMBER(1)->TINYINT NUMBER(P,S)->DECIMAL NUMBER(2),NUMBER(4)->SMALLINT NUMBER(5),NUMBER(9)->INT NUMBER(10),NUMBER(18)->BIGINT
            put("NUMBER(1)","TINYINT");
            put("NUMBER(4)","SMALLINT");
            put("NUMBER(9)","INT");
            put("NUMBER(18)","BIGINT");
            put("NUMBER(P,S)","DECIMAL");
        }
    };



    public final static Map<String, Map<String, String>> DATA_MAPPING =new HashMap<String, Map<String, String>>() {
        {
            put("MYSQL",MYSQL_HIVE);
            put("ORACLE",ORACLE_HIVE);
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
            dto.setColumnComment(e.getRemarks());
            result.add(dto);
        });
        return result;
    }

    public static String parseDataTypeOne(String dbType, String sourceColumnType) {
        if (sourceColumnType.toUpperCase().contains("NUMBER")) {
            if (sourceColumnType.contains(",")) {
                sourceColumnType = "NUMBER(P,S)";
            }else {
                Integer size = Integer.valueOf(sourceColumnType.substring(sourceColumnType.indexOf("(") + 1, sourceColumnType.indexOf(")")));
                if (size == 1){
                    sourceColumnType = "NUMBER(1)";
                }else if (size <= 4) {
                    sourceColumnType = "NUMBER(4)";
                }else if (size <= 9) {
                    sourceColumnType = "NUMBER(9)";
                }else if (size <= 18) {
                    sourceColumnType = "NUMBER(18)";
                }
            }
        }
        String type = DATA_MAPPING.get(dbType).get(sourceColumnType.toUpperCase());

        return type;
    }
}
