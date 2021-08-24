package org.jeecg.modules.datasources.util;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.apache.commons.lang3.StringUtils;
import org.jeecg.modules.datasources.antlr.mysql.MySqlLexer;
import org.jeecg.modules.datasources.antlr.mysql.MySqlParser;
import org.jeecg.modules.datasources.antlr.oracle.OracleLexer;
import org.jeecg.modules.datasources.antlr.oracle.OracleParser;
import org.jeecg.modules.datasources.constant.DataSourceConstant;
import org.jeecg.modules.datasources.dto.DataModuleDTO;
import org.jeecg.modules.datasources.model.WaterfallModelField;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * DDL转换工具类
 * @Author liansongye
 * @create 2021/8/4 10:07 上午
 */
public class DdlConvertUtil {


    /**************************** Mysql ********************************/

    // todo 1、改到visitor或者listener  2、添加分区字段解析
    public static DataModuleDTO mysqlToModel(String sql) throws IOException {
        MySqlParser parse = getParseTree(sql);
        DataModuleDTO ddl = new DataModuleDTO();
        MySqlParser.RootContext statement = parse.root();
        MySqlParser.CreateTableContext table = statement.sqlStatements().sqlStatement(0).ddlStatement().createTable();
        String dbAndTable = table.getRuleContexts(MySqlParser.TableNameContext.class).get(0).getText().replaceAll("`", "");
        dbAndTable = dbAndTable.contains(".") ? dbAndTable.split("\\.")[1] : dbAndTable;
        ddl.setModelName(dbAndTable);

        List<MySqlParser.CreateDefinitionContext> clounmsContext = table.getRuleContexts(MySqlParser.CreateDefinitionsContext.class).get(0).getRuleContexts(MySqlParser.ColumnDeclarationContext.class);

        //表字段
        List<WaterfallModelField> coloumns = new ArrayList<>(clounmsContext.size());
        for (MySqlParser.CreateDefinitionContext clounm : clounmsContext) {
            String id = clounm.getRuleContexts(MySqlParser.UidContext.class).get(0).getText().replace("`","");
            MySqlParser.ColumnDefinitionContext columnDefinition = clounm.getRuleContexts(MySqlParser.ColumnDefinitionContext.class).get(0);
            String dataType = DataTypeUtil.parseDataTypeOne(DataSourceConstant.MYSQL,columnDefinition.getRuleContexts(MySqlParser.DataTypeContext.class).get(0).getChild(0).getText());
            List<MySqlParser.LengthOneDimensionContext> lenContexts = columnDefinition.getRuleContexts(MySqlParser.DataTypeContext.class).get(0).getRuleContexts(MySqlParser.LengthOneDimensionContext.class);
            String len = CollectionUtils.isEmpty(lenContexts) ? null : lenContexts.get(0).getRuleContexts(MySqlParser.DecimalLiteralContext.class).get(0).getText().replace("(","").replace(")","");
            Boolean isPrimary = CollectionUtils.isEmpty(columnDefinition.getRuleContexts(MySqlParser.PrimaryKeyColumnConstraintContext.class)) ? false : true;
            Boolean emptyFlag = false;
            if (!isPrimary) {
                emptyFlag = CollectionUtils.isEmpty(columnDefinition.getRuleContexts(MySqlParser.NullNotnullContext.class)) ? true : false;
            }
            String comment = "''";
            List<MySqlParser.CommentColumnConstraintContext> commentContexts = columnDefinition.getRuleContexts(MySqlParser.CommentColumnConstraintContext.class);
            if (commentContexts != null && commentContexts.size() > 1) {
                comment = commentContexts.get(1).getText();
            }
            WaterfallModelField waterfallModelField = new WaterfallModelField();
            waterfallModelField.setFieldName(id);
            waterfallModelField.setFieldTypeName(dataType);
            waterfallModelField.setLength(len);
            waterfallModelField.setPrimarykeyFlag(isPrimary);
            waterfallModelField.setEmptyFlag(emptyFlag);
            waterfallModelField.setRemark(comment);
            coloumns.add(waterfallModelField);
        }

        ddl.setModelFields(coloumns);
        return ddl;
    }

    private static MySqlParser getParseTree(String sql) throws IOException {
        CharStream input = CharStreams.fromString(sql);
        MySqlLexer lexer = new MySqlLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        MySqlParser parser = new MySqlParser(tokens);
        return parser;
    }

    public static void main(String[] args) {
        String sql = "CREATE TABLE `COMPACTION_QUEUE` (\n" +
                "  `CQ_ID` BIGINT(20) NOT NULL,\n" +
                "  `CQ_DATABASE` VARCHAR(128) NOT NULL,\n" +
                "  `CQ_TABLE` VARCHAR(128) NOT NULL,\n" +
                "  `CQ_PARTITION` VARCHAR(767) DEFAULT NULL,\n" +
                "  `CQ_STATE` CHAR(1) NOT NULL,\n" +
                "  `CQ_TYPE` CHAR(1) NOT NULL,\n" +
                "  `CQ_TBLPROPERTIES` VARCHAR(2048) DEFAULT NULL,\n" +
                "  `CQ_WORKER_ID` VARCHAR(128) DEFAULT NULL,\n" +
                "  `CQ_START` BIGINT(20) DEFAULT NULL,\n" +
                "  `CQ_RUN_AS` VARCHAR(128) DEFAULT NULL,\n" +
                "  `CQ_HIGHEST_TXN_ID` BIGINT(20) DEFAULT NULL,\n" +
                "  `CQ_META_INFO` VARBINARY(2048) DEFAULT NULL,\n" +
                "  `CQ_HADOOP_JOB_ID` VARCHAR(32) DEFAULT NULL\n" +
                ",);\n" +
                "\n";
        try {
            mysqlToModel(sql);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static DataModuleDTO oracleToModel(String sql) throws IOException {
        CharStream input = CharStreams.fromString(sql);
        OracleLexer lexer = new OracleLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        OracleParser parser = new OracleParser(tokens);
        DataModuleDTO ddl = new DataModuleDTO();
        OracleParser.RootContext statement = parser.root();

        String dbAndTable = statement.statement().create_table().table_name().getText();
        dbAndTable = dbAndTable.contains(".") ? dbAndTable.split("\\.")[1] : dbAndTable;
        ddl.setModelName(dbAndTable);
        List<WaterfallModelField> coloumns = new ArrayList<>();

        List<OracleParser.Relational_propertyContext> relational_propertyContexts = statement.statement().create_table().relational_table().relational_property();
        relational_propertyContexts.stream().forEach(e -> {
            String columnName = e.column_definition().column_name().getText();
            String dataType = DataTypeUtil.parseDataTypeOne(DataSourceConstant.ORACLE, e.column_definition().datatype().native_datatype_element().getText());
            String len = "STRING".equals(dataType) ? null :
                    CollectionUtils.isEmpty(e.column_definition().default_value().children) ? null : e.column_definition().default_value().getText().replace("(","").replace(")","");
            List<OracleParser.Inline_constraintContext> inline_constraintContexts = e.column_definition().inline_constraint();
            Boolean isPrimary  = false;
            Boolean emptyFlag = true;
            //PRIMARY:2 notNull:1
            AtomicInteger flag = new AtomicInteger(0);
            inline_constraintContexts.stream().forEach(t -> {
                if (StringUtils.isNotBlank(t.PRIMARY().getText())) {
                    flag.set(2);
                }
                if (StringUtils.isNotBlank(t.NOT().getText())) {
                    flag.set(1);
                }
            });

            if (flag.get() == 1) {
                emptyFlag = false;
            }
            if (flag.get() == 2) {
                emptyFlag = false;
                isPrimary = true;
            }
            WaterfallModelField waterfallModelField = new WaterfallModelField();
            waterfallModelField.setFieldName(columnName);
            waterfallModelField.setFieldTypeName(dataType);
            waterfallModelField.setLength(len);
            waterfallModelField.setPrimarykeyFlag(isPrimary);
            waterfallModelField.setEmptyFlag(emptyFlag);
            coloumns.add(waterfallModelField);
            coloumns.add(waterfallModelField);
        });
        ddl.setModelFields(coloumns);
        return ddl;
    }

    //DDL实体转hive
    public static String modelToHiveDdl(DataModuleDTO dto) {
        StringBuilder builder = new StringBuilder();
        String ddl = "CREATE TABLE %s\n";
        builder.append(String.format(ddl, dto.getModelName()));
        builder.append("(");
        //id INT comment 'ss'
        String colStr = "%s\t%s\tCOMMENT\t%s";
        String collect = dto.getModelFields().stream().map(col -> {
            if ("DECIMAL".equalsIgnoreCase(col.getFieldTypeName())) {
                col.setFieldTypeName(col.getFieldTypeName() + "(" +col.getLength()+")");
            }
            return String.format(colStr, col.getFieldName(), col.getFieldTypeName(), "'" + col.getRemark() + "'");
        }).collect(Collectors.joining(","));
        builder.append(collect +")");
        return builder.toString();
    }

    public static String delSql(String tableName) {
        return "drop table if exists " + tableName;
    }
}
