package org.jeecg.modules.datasources.util;

import lombok.extern.slf4j.Slf4j;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.apache.commons.lang3.StringUtils;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.modules.datasources.antlr.mysql.*;
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
@Slf4j
public class DdlConvertUtil {

    /**************************** Mysql ********************************/

    public static DataModuleDTO mysqlToModel(String sql) {
        CharStream input = CharStreams.fromString(sql);
        MySqlLexer lexer = new MySqlLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        MySqlParser parser = new MySqlParser(tokens);
        ParseTree tree = parser.program();

        ParseTreeWalker walker = new ParseTreeWalker();
        MySqlParserBaseListener listener = new MySqlParserBaseListener();
        walker.walk(listener, tree);

        return listener.getModule();
    }

    public static void main(String[] args) {
        String sql = "CREATE TABLE IF NOT EXISTS nbods.cd_treatdiagnosis\n" +
                "(\n" +
                "\t`id` INT AUTO_INCREMENT COMMENT ' id ' PRIMARY KEY,\n" +
                "\tPRIMARY KEY (`id`)\n" +
                ")";
        System.out.println(mysqlToModel(sql).toString());

    }

    public static DataModuleDTO oracleToModel(String sql) {
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
