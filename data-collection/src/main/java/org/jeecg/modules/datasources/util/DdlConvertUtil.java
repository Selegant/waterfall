package org.jeecg.modules.datasources.util;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.jeecg.modules.datasources.antlr.mysql.MySqlLexer;
import org.jeecg.modules.datasources.antlr.mysql.MySqlParser;
import org.jeecg.modules.datasources.dto.DataModuleDTO;
import org.jeecg.modules.datasources.dto.DdlDto;
import org.jeecg.modules.datasources.model.WaterfallModelField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
            String dataType = DataTypeUtil.parseDataTypeOne("Mysql",columnDefinition.getRuleContexts(MySqlParser.DataTypeContext.class).get(0).getChild(0).getText());
            List<MySqlParser.LengthOneDimensionContext> lenContexts = columnDefinition.getRuleContexts(MySqlParser.DataTypeContext.class).get(0).getRuleContexts(MySqlParser.LengthOneDimensionContext.class);
            Integer len = CollectionUtils.isEmpty(lenContexts) ? null : Integer.valueOf(lenContexts.get(0).getRuleContexts(MySqlParser.DecimalLiteralContext.class).get(0).getText());
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

    //DDL实体转hive
    public static String modelToHiveDdl(DataModuleDTO dto) {
        StringBuilder builder = new StringBuilder();
        String ddl = "CREATE TABLE IF NOT EXISTS %s\n";
        builder.append(String.format(ddl, dto.getModelName()));
        builder.append("(");
        //id INT comment 'ss'
        String colStr = "%s\t%s\tCOMMENT\t%s";
        String collect = dto.getModelFields().stream().map(col -> {
            return String.format(colStr, col.getFieldName(), col.getFieldTypeName(), col.getRemark());
        }).collect(Collectors.joining(","));
        builder.append(collect +")");
        return builder.toString();
    }
}
