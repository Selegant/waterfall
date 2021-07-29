package org.jeecg.modules.datasources.constant;

public class DataSourceConstant {

    /**
     * 数据库类型
     */

    public static final String MYSQL = "mysql";

    public static final String ORACLE = "oracle";

    public static final String HIVE = "hive";

    /**
     * sql查询别名
     */
    public static final String AMOUNT = "amount";

    /**
     * datax
     */

    public static final String MYSQL_READER = "mysqlreader";

    public static final String MYSQL_WRITER = "mysqlwriter";

    public static final String ORACLE_READER = "oraclereader";

    public static final String ORACLE_WRITER = "oraclewriter";

    public static final String HIVE_READER = "hdfsreader";

    public static final String HIVE_WRITER = "hdfswriter";

    /**
     * 表，视图
     */
    public static final Integer TYPE_TABLE_VIEW = 0;

    public static final Integer TYPE_TABLE = 1;

    public static final Integer TYPE_VIEW = 2;

    /**
     * hive驱动
     */
    public static final String HIVE_DRIVER = "org.apache.hive.jdbc.HiveDriver";
}
