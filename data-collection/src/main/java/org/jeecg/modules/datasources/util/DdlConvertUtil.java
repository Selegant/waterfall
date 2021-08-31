package org.jeecg.modules.datasources.util;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.apache.commons.lang3.StringUtils;
import org.jeecg.modules.datasources.antlr.common.SqlErrorListener;
import org.jeecg.modules.datasources.antlr.mysql.*;
import org.jeecg.modules.datasources.antlr.oracle.OracleLexer;
import org.jeecg.modules.datasources.antlr.oracle.OracleParser;
import org.jeecg.modules.datasources.constant.DataSourceConstant;
import org.jeecg.modules.datasources.dto.DataModuleDTO;
import org.jeecg.modules.datasources.model.WaterfallModelField;
import org.springframework.util.CollectionUtils;

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
        parser.removeErrorListeners();
        parser.addErrorListener(new SqlErrorListener());
        ParseTree tree = parser.program();

        ParseTreeWalker walker = new ParseTreeWalker();
        MySqlParserBaseListener listener = new MySqlParserBaseListener();
        walker.walk(listener, tree);

        return listener.getModule();
    }

    public static void main(String[] args) {
        String sql = "create TABLE AB_DRUGWAY\n" +
                "(\n" +
                "    INCREMENT_ID           INT AUTO_INCREMENT COMMENT '自增长主键INCREMENT_ID'\n" +
                "        PRIMARY KEY,\n" +
                "    DOSE_WAY_ID            vARCHAR(40)                            NOT NULL COMMENT '给药方式序号DOSE_WAY_ID',\n" +
                "    ORG_CODE               CHAR(32)                               NOT NULL COMMENT '组织机构代码ORG_CODE',\n" +
                "    DOSE_WAY_TYPE          VARCHAR(20)                            NULL COMMENT '给药方式类别DOSE_WAY_TYPE',\n" +
                "    DOSE_WAY_CODE          VARCHAR(20)                            NULL COMMENT '给药方式编码DOSE_WAY_CODE',\n" +
                "    DOSE_EN_NAME           VARCHAR(60)                            NULL COMMENT '给药英文名称DOSE_EN_NAME',\n" +
                "    DOSE_ZH_NAME           VARCHAR(100)                           NULL COMMENT '给药中文名称DOSE_ZH_NAME',\n" +
                "    FORCE_FLAG             INT(1)       DEFAULT 0                 NULL COMMENT '是否取整判别FORCE_FLAG',\n" +
                "    OUTPATI_USE_CHECK      INT(1)       DEFAULT 0                 NULL COMMENT '门诊使用判别OUTPATI_USE_CHECK',\n" +
                "    INPATI_USE_CHECK       INT(1)       DEFAULT 0                 NULL COMMENT '住院使用判别INPATI_USE_CHECK',\n" +
                "    DRUG_TITLE_PREFIX      VARCHAR(255) DEFAULT ''                NULL COMMENT '药物标签前缀',\n" +
                "    EMERG_USE_CHECK        INT(1)       DEFAULT 0                 NULL COMMENT '急诊使用判别EMERG_USE_CHECK',\n" +
                "    USING_DRUG_ATTR        VARCHAR(3)                             NULL COMMENT '使用药品属性USING_DRUG_ATTR',\n" +
                "    INJECTION_FLAG         INT(1)       DEFAULT 0                 NULL COMMENT '是否注射判别INJECTION_FLAG',\n" +
                "    OUTPATI_ROUNDING_CHECK INT(1)       DEFAULT 0                 NULL COMMENT '门诊取整判别OUTPATI_ROUNDING_CHECK',\n" +
                "    CONFIG_FLAG            INT(1)       DEFAULT 0                 NULL COMMENT '是否配置判别CONFIG_FLAG',\n" +
                "    DEL                    INT(1)       DEFAULT 0                 NULL COMMENT '是否作废判别DEL',\n" +
                "    ORDERBY                INT(3)                                 NULL COMMENT '显示排序序号ORDERBY',\n" +
                "    SEARCH_CODE1           VARCHAR(6)                             NULL COMMENT '汉字输入码一SEARCH_CODE1',\n" +
                "    SEARCH_CODE2           VARCHAR(6)                             NULL COMMENT '汉字输入码二SEARCH_CODE2',\n" +
                "    SEARCH_CODE3           VARCHAR(6)                             NULL COMMENT '汉字输入码三SEARCH_CODE3',\n" +
                "    CREATE_TIME            TIMESTAMP    DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '创建时间CREATE_TIME',\n" +
                "    CREATOR                CHAR(32)                               NULL COMMENT '创建人CREATOR',\n" +
                "    UPDATE_TIME            TIMESTAMP    DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间UPDATE_TIME',\n" +
                "    UPDATER                CHAR(32)                               NULL COMMENT '更新人UPDATER'\n" +
                ")\n" +
                "    COMMENT '临床给药方式AB_DRUGWAY';";
        System.out.println(JSON.toJSONString(mysqlToModel(sql)));

    }

    public static DataModuleDTO oracleToModel(String sql) {
        CharStream input = CharStreams.fromString(sql);
        OracleLexer lexer = new OracleLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        OracleParser parser = new OracleParser(tokens);
        parser.removeErrorListeners();
        parser.addErrorListener(new SqlErrorListener());
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
