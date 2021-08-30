// Generated from /Users/ye/work/hz/waterfall/data-collection/src/main/java/org/jeecg/modules/datasources/antlr/mysql/MySqlParser.g4 by ANTLR 4.9.1
package org.jeecg.modules.datasources.antlr.mysql;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link MySqlParser}.
 */
public interface MySqlParserListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link MySqlParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(MySqlParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySqlParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(MySqlParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySqlParser#root}.
	 * @param ctx the parse tree
	 */
	void enterRoot(MySqlParser.RootContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySqlParser#root}.
	 * @param ctx the parse tree
	 */
	void exitRoot(MySqlParser.RootContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySqlParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(MySqlParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySqlParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(MySqlParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySqlParser#createTable}.
	 * @param ctx the parse tree
	 */
	void enterCreateTable(MySqlParser.CreateTableContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySqlParser#createTable}.
	 * @param ctx the parse tree
	 */
	void exitCreateTable(MySqlParser.CreateTableContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySqlParser#tableName}.
	 * @param ctx the parse tree
	 */
	void enterTableName(MySqlParser.TableNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySqlParser#tableName}.
	 * @param ctx the parse tree
	 */
	void exitTableName(MySqlParser.TableNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySqlParser#createDefinitions}.
	 * @param ctx the parse tree
	 */
	void enterCreateDefinitions(MySqlParser.CreateDefinitionsContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySqlParser#createDefinitions}.
	 * @param ctx the parse tree
	 */
	void exitCreateDefinitions(MySqlParser.CreateDefinitionsContext ctx);
	/**
	 * Enter a parse tree produced by the {@code tableOptionEngine}
	 * labeled alternative in {@link MySqlParser#tableOption}.
	 * @param ctx the parse tree
	 */
	void enterTableOptionEngine(MySqlParser.TableOptionEngineContext ctx);
	/**
	 * Exit a parse tree produced by the {@code tableOptionEngine}
	 * labeled alternative in {@link MySqlParser#tableOption}.
	 * @param ctx the parse tree
	 */
	void exitTableOptionEngine(MySqlParser.TableOptionEngineContext ctx);
	/**
	 * Enter a parse tree produced by the {@code tableOptionAutoIncrement}
	 * labeled alternative in {@link MySqlParser#tableOption}.
	 * @param ctx the parse tree
	 */
	void enterTableOptionAutoIncrement(MySqlParser.TableOptionAutoIncrementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code tableOptionAutoIncrement}
	 * labeled alternative in {@link MySqlParser#tableOption}.
	 * @param ctx the parse tree
	 */
	void exitTableOptionAutoIncrement(MySqlParser.TableOptionAutoIncrementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code tableOptionCharset}
	 * labeled alternative in {@link MySqlParser#tableOption}.
	 * @param ctx the parse tree
	 */
	void enterTableOptionCharset(MySqlParser.TableOptionCharsetContext ctx);
	/**
	 * Exit a parse tree produced by the {@code tableOptionCharset}
	 * labeled alternative in {@link MySqlParser#tableOption}.
	 * @param ctx the parse tree
	 */
	void exitTableOptionCharset(MySqlParser.TableOptionCharsetContext ctx);
	/**
	 * Enter a parse tree produced by the {@code tableOptionCollate}
	 * labeled alternative in {@link MySqlParser#tableOption}.
	 * @param ctx the parse tree
	 */
	void enterTableOptionCollate(MySqlParser.TableOptionCollateContext ctx);
	/**
	 * Exit a parse tree produced by the {@code tableOptionCollate}
	 * labeled alternative in {@link MySqlParser#tableOption}.
	 * @param ctx the parse tree
	 */
	void exitTableOptionCollate(MySqlParser.TableOptionCollateContext ctx);
	/**
	 * Enter a parse tree produced by the {@code tableOptionComment}
	 * labeled alternative in {@link MySqlParser#tableOption}.
	 * @param ctx the parse tree
	 */
	void enterTableOptionComment(MySqlParser.TableOptionCommentContext ctx);
	/**
	 * Exit a parse tree produced by the {@code tableOptionComment}
	 * labeled alternative in {@link MySqlParser#tableOption}.
	 * @param ctx the parse tree
	 */
	void exitTableOptionComment(MySqlParser.TableOptionCommentContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySqlParser#collationName}.
	 * @param ctx the parse tree
	 */
	void enterCollationName(MySqlParser.CollationNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySqlParser#collationName}.
	 * @param ctx the parse tree
	 */
	void exitCollationName(MySqlParser.CollationNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySqlParser#createDefinition}.
	 * @param ctx the parse tree
	 */
	void enterCreateDefinition(MySqlParser.CreateDefinitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySqlParser#createDefinition}.
	 * @param ctx the parse tree
	 */
	void exitCreateDefinition(MySqlParser.CreateDefinitionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code primaryKeyTableConstraint}
	 * labeled alternative in {@link MySqlParser#tableConstraint}.
	 * @param ctx the parse tree
	 */
	void enterPrimaryKeyTableConstraint(MySqlParser.PrimaryKeyTableConstraintContext ctx);
	/**
	 * Exit a parse tree produced by the {@code primaryKeyTableConstraint}
	 * labeled alternative in {@link MySqlParser#tableConstraint}.
	 * @param ctx the parse tree
	 */
	void exitPrimaryKeyTableConstraint(MySqlParser.PrimaryKeyTableConstraintContext ctx);
	/**
	 * Enter a parse tree produced by the {@code uniqueKeyTableConstraint}
	 * labeled alternative in {@link MySqlParser#tableConstraint}.
	 * @param ctx the parse tree
	 */
	void enterUniqueKeyTableConstraint(MySqlParser.UniqueKeyTableConstraintContext ctx);
	/**
	 * Exit a parse tree produced by the {@code uniqueKeyTableConstraint}
	 * labeled alternative in {@link MySqlParser#tableConstraint}.
	 * @param ctx the parse tree
	 */
	void exitUniqueKeyTableConstraint(MySqlParser.UniqueKeyTableConstraintContext ctx);
	/**
	 * Enter a parse tree produced by the {@code foreignKeyTableConstraint}
	 * labeled alternative in {@link MySqlParser#tableConstraint}.
	 * @param ctx the parse tree
	 */
	void enterForeignKeyTableConstraint(MySqlParser.ForeignKeyTableConstraintContext ctx);
	/**
	 * Exit a parse tree produced by the {@code foreignKeyTableConstraint}
	 * labeled alternative in {@link MySqlParser#tableConstraint}.
	 * @param ctx the parse tree
	 */
	void exitForeignKeyTableConstraint(MySqlParser.ForeignKeyTableConstraintContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySqlParser#indexColumnNames}.
	 * @param ctx the parse tree
	 */
	void enterIndexColumnNames(MySqlParser.IndexColumnNamesContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySqlParser#indexColumnNames}.
	 * @param ctx the parse tree
	 */
	void exitIndexColumnNames(MySqlParser.IndexColumnNamesContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySqlParser#indexOption}.
	 * @param ctx the parse tree
	 */
	void enterIndexOption(MySqlParser.IndexOptionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySqlParser#indexOption}.
	 * @param ctx the parse tree
	 */
	void exitIndexOption(MySqlParser.IndexOptionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySqlParser#referenceDefinition}.
	 * @param ctx the parse tree
	 */
	void enterReferenceDefinition(MySqlParser.ReferenceDefinitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySqlParser#referenceDefinition}.
	 * @param ctx the parse tree
	 */
	void exitReferenceDefinition(MySqlParser.ReferenceDefinitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySqlParser#referenceAction}.
	 * @param ctx the parse tree
	 */
	void enterReferenceAction(MySqlParser.ReferenceActionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySqlParser#referenceAction}.
	 * @param ctx the parse tree
	 */
	void exitReferenceAction(MySqlParser.ReferenceActionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySqlParser#columnDefinition}.
	 * @param ctx the parse tree
	 */
	void enterColumnDefinition(MySqlParser.ColumnDefinitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySqlParser#columnDefinition}.
	 * @param ctx the parse tree
	 */
	void exitColumnDefinition(MySqlParser.ColumnDefinitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySqlParser#indexColumnName}.
	 * @param ctx the parse tree
	 */
	void enterIndexColumnName(MySqlParser.IndexColumnNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySqlParser#indexColumnName}.
	 * @param ctx the parse tree
	 */
	void exitIndexColumnName(MySqlParser.IndexColumnNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySqlParser#charsetName}.
	 * @param ctx the parse tree
	 */
	void enterCharsetName(MySqlParser.CharsetNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySqlParser#charsetName}.
	 * @param ctx the parse tree
	 */
	void exitCharsetName(MySqlParser.CharsetNameContext ctx);
	/**
	 * Enter a parse tree produced by the {@code nullColumnConstraint}
	 * labeled alternative in {@link MySqlParser#columnConstraint}.
	 * @param ctx the parse tree
	 */
	void enterNullColumnConstraint(MySqlParser.NullColumnConstraintContext ctx);
	/**
	 * Exit a parse tree produced by the {@code nullColumnConstraint}
	 * labeled alternative in {@link MySqlParser#columnConstraint}.
	 * @param ctx the parse tree
	 */
	void exitNullColumnConstraint(MySqlParser.NullColumnConstraintContext ctx);
	/**
	 * Enter a parse tree produced by the {@code defaultColumnConstraint}
	 * labeled alternative in {@link MySqlParser#columnConstraint}.
	 * @param ctx the parse tree
	 */
	void enterDefaultColumnConstraint(MySqlParser.DefaultColumnConstraintContext ctx);
	/**
	 * Exit a parse tree produced by the {@code defaultColumnConstraint}
	 * labeled alternative in {@link MySqlParser#columnConstraint}.
	 * @param ctx the parse tree
	 */
	void exitDefaultColumnConstraint(MySqlParser.DefaultColumnConstraintContext ctx);
	/**
	 * Enter a parse tree produced by the {@code autoIncrementColumnConstraint}
	 * labeled alternative in {@link MySqlParser#columnConstraint}.
	 * @param ctx the parse tree
	 */
	void enterAutoIncrementColumnConstraint(MySqlParser.AutoIncrementColumnConstraintContext ctx);
	/**
	 * Exit a parse tree produced by the {@code autoIncrementColumnConstraint}
	 * labeled alternative in {@link MySqlParser#columnConstraint}.
	 * @param ctx the parse tree
	 */
	void exitAutoIncrementColumnConstraint(MySqlParser.AutoIncrementColumnConstraintContext ctx);
	/**
	 * Enter a parse tree produced by the {@code primaryKeyColumnConstraint}
	 * labeled alternative in {@link MySqlParser#columnConstraint}.
	 * @param ctx the parse tree
	 */
	void enterPrimaryKeyColumnConstraint(MySqlParser.PrimaryKeyColumnConstraintContext ctx);
	/**
	 * Exit a parse tree produced by the {@code primaryKeyColumnConstraint}
	 * labeled alternative in {@link MySqlParser#columnConstraint}.
	 * @param ctx the parse tree
	 */
	void exitPrimaryKeyColumnConstraint(MySqlParser.PrimaryKeyColumnConstraintContext ctx);
	/**
	 * Enter a parse tree produced by the {@code uniqueKeyColumnConstraint}
	 * labeled alternative in {@link MySqlParser#columnConstraint}.
	 * @param ctx the parse tree
	 */
	void enterUniqueKeyColumnConstraint(MySqlParser.UniqueKeyColumnConstraintContext ctx);
	/**
	 * Exit a parse tree produced by the {@code uniqueKeyColumnConstraint}
	 * labeled alternative in {@link MySqlParser#columnConstraint}.
	 * @param ctx the parse tree
	 */
	void exitUniqueKeyColumnConstraint(MySqlParser.UniqueKeyColumnConstraintContext ctx);
	/**
	 * Enter a parse tree produced by the {@code commentColumnConstraint}
	 * labeled alternative in {@link MySqlParser#columnConstraint}.
	 * @param ctx the parse tree
	 */
	void enterCommentColumnConstraint(MySqlParser.CommentColumnConstraintContext ctx);
	/**
	 * Exit a parse tree produced by the {@code commentColumnConstraint}
	 * labeled alternative in {@link MySqlParser#columnConstraint}.
	 * @param ctx the parse tree
	 */
	void exitCommentColumnConstraint(MySqlParser.CommentColumnConstraintContext ctx);
	/**
	 * Enter a parse tree produced by the {@code collateColumnConstraint}
	 * labeled alternative in {@link MySqlParser#columnConstraint}.
	 * @param ctx the parse tree
	 */
	void enterCollateColumnConstraint(MySqlParser.CollateColumnConstraintContext ctx);
	/**
	 * Exit a parse tree produced by the {@code collateColumnConstraint}
	 * labeled alternative in {@link MySqlParser#columnConstraint}.
	 * @param ctx the parse tree
	 */
	void exitCollateColumnConstraint(MySqlParser.CollateColumnConstraintContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySqlParser#defaultValue}.
	 * @param ctx the parse tree
	 */
	void enterDefaultValue(MySqlParser.DefaultValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySqlParser#defaultValue}.
	 * @param ctx the parse tree
	 */
	void exitDefaultValue(MySqlParser.DefaultValueContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySqlParser#unaryOperator}.
	 * @param ctx the parse tree
	 */
	void enterUnaryOperator(MySqlParser.UnaryOperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySqlParser#unaryOperator}.
	 * @param ctx the parse tree
	 */
	void exitUnaryOperator(MySqlParser.UnaryOperatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySqlParser#constant}.
	 * @param ctx the parse tree
	 */
	void enterConstant(MySqlParser.ConstantContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySqlParser#constant}.
	 * @param ctx the parse tree
	 */
	void exitConstant(MySqlParser.ConstantContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySqlParser#stringLiteral}.
	 * @param ctx the parse tree
	 */
	void enterStringLiteral(MySqlParser.StringLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySqlParser#stringLiteral}.
	 * @param ctx the parse tree
	 */
	void exitStringLiteral(MySqlParser.StringLiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySqlParser#currentTimestamp}.
	 * @param ctx the parse tree
	 */
	void enterCurrentTimestamp(MySqlParser.CurrentTimestampContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySqlParser#currentTimestamp}.
	 * @param ctx the parse tree
	 */
	void exitCurrentTimestamp(MySqlParser.CurrentTimestampContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySqlParser#ifNotExists}.
	 * @param ctx the parse tree
	 */
	void enterIfNotExists(MySqlParser.IfNotExistsContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySqlParser#ifNotExists}.
	 * @param ctx the parse tree
	 */
	void exitIfNotExists(MySqlParser.IfNotExistsContext ctx);
	/**
	 * Enter a parse tree produced by the {@code stringDataType}
	 * labeled alternative in {@link MySqlParser#dataType}.
	 * @param ctx the parse tree
	 */
	void enterStringDataType(MySqlParser.StringDataTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code stringDataType}
	 * labeled alternative in {@link MySqlParser#dataType}.
	 * @param ctx the parse tree
	 */
	void exitStringDataType(MySqlParser.StringDataTypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code nationalStringDataType}
	 * labeled alternative in {@link MySqlParser#dataType}.
	 * @param ctx the parse tree
	 */
	void enterNationalStringDataType(MySqlParser.NationalStringDataTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code nationalStringDataType}
	 * labeled alternative in {@link MySqlParser#dataType}.
	 * @param ctx the parse tree
	 */
	void exitNationalStringDataType(MySqlParser.NationalStringDataTypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code nationalVaryingStringDataType}
	 * labeled alternative in {@link MySqlParser#dataType}.
	 * @param ctx the parse tree
	 */
	void enterNationalVaryingStringDataType(MySqlParser.NationalVaryingStringDataTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code nationalVaryingStringDataType}
	 * labeled alternative in {@link MySqlParser#dataType}.
	 * @param ctx the parse tree
	 */
	void exitNationalVaryingStringDataType(MySqlParser.NationalVaryingStringDataTypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code dimensionDataType}
	 * labeled alternative in {@link MySqlParser#dataType}.
	 * @param ctx the parse tree
	 */
	void enterDimensionDataType(MySqlParser.DimensionDataTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code dimensionDataType}
	 * labeled alternative in {@link MySqlParser#dataType}.
	 * @param ctx the parse tree
	 */
	void exitDimensionDataType(MySqlParser.DimensionDataTypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code simpleDataType}
	 * labeled alternative in {@link MySqlParser#dataType}.
	 * @param ctx the parse tree
	 */
	void enterSimpleDataType(MySqlParser.SimpleDataTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code simpleDataType}
	 * labeled alternative in {@link MySqlParser#dataType}.
	 * @param ctx the parse tree
	 */
	void exitSimpleDataType(MySqlParser.SimpleDataTypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code collectionDataType}
	 * labeled alternative in {@link MySqlParser#dataType}.
	 * @param ctx the parse tree
	 */
	void enterCollectionDataType(MySqlParser.CollectionDataTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code collectionDataType}
	 * labeled alternative in {@link MySqlParser#dataType}.
	 * @param ctx the parse tree
	 */
	void exitCollectionDataType(MySqlParser.CollectionDataTypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code spatialDataType}
	 * labeled alternative in {@link MySqlParser#dataType}.
	 * @param ctx the parse tree
	 */
	void enterSpatialDataType(MySqlParser.SpatialDataTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code spatialDataType}
	 * labeled alternative in {@link MySqlParser#dataType}.
	 * @param ctx the parse tree
	 */
	void exitSpatialDataType(MySqlParser.SpatialDataTypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code longVarcharDataType}
	 * labeled alternative in {@link MySqlParser#dataType}.
	 * @param ctx the parse tree
	 */
	void enterLongVarcharDataType(MySqlParser.LongVarcharDataTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code longVarcharDataType}
	 * labeled alternative in {@link MySqlParser#dataType}.
	 * @param ctx the parse tree
	 */
	void exitLongVarcharDataType(MySqlParser.LongVarcharDataTypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code longVarbinaryDataType}
	 * labeled alternative in {@link MySqlParser#dataType}.
	 * @param ctx the parse tree
	 */
	void enterLongVarbinaryDataType(MySqlParser.LongVarbinaryDataTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code longVarbinaryDataType}
	 * labeled alternative in {@link MySqlParser#dataType}.
	 * @param ctx the parse tree
	 */
	void exitLongVarbinaryDataType(MySqlParser.LongVarbinaryDataTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySqlParser#lengthOneDimension}.
	 * @param ctx the parse tree
	 */
	void enterLengthOneDimension(MySqlParser.LengthOneDimensionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySqlParser#lengthOneDimension}.
	 * @param ctx the parse tree
	 */
	void exitLengthOneDimension(MySqlParser.LengthOneDimensionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySqlParser#lengthTwoDimension}.
	 * @param ctx the parse tree
	 */
	void enterLengthTwoDimension(MySqlParser.LengthTwoDimensionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySqlParser#lengthTwoDimension}.
	 * @param ctx the parse tree
	 */
	void exitLengthTwoDimension(MySqlParser.LengthTwoDimensionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySqlParser#lengthTwoOptionalDimension}.
	 * @param ctx the parse tree
	 */
	void enterLengthTwoOptionalDimension(MySqlParser.LengthTwoOptionalDimensionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySqlParser#lengthTwoOptionalDimension}.
	 * @param ctx the parse tree
	 */
	void exitLengthTwoOptionalDimension(MySqlParser.LengthTwoOptionalDimensionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySqlParser#identifier}.
	 * @param ctx the parse tree
	 */
	void enterIdentifier(MySqlParser.IdentifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySqlParser#identifier}.
	 * @param ctx the parse tree
	 */
	void exitIdentifier(MySqlParser.IdentifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySqlParser#simpleId}.
	 * @param ctx the parse tree
	 */
	void enterSimpleId(MySqlParser.SimpleIdContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySqlParser#simpleId}.
	 * @param ctx the parse tree
	 */
	void exitSimpleId(MySqlParser.SimpleIdContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySqlParser#fileSizeLiteral}.
	 * @param ctx the parse tree
	 */
	void enterFileSizeLiteral(MySqlParser.FileSizeLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySqlParser#fileSizeLiteral}.
	 * @param ctx the parse tree
	 */
	void exitFileSizeLiteral(MySqlParser.FileSizeLiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySqlParser#referenceControlType}.
	 * @param ctx the parse tree
	 */
	void enterReferenceControlType(MySqlParser.ReferenceControlTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySqlParser#referenceControlType}.
	 * @param ctx the parse tree
	 */
	void exitReferenceControlType(MySqlParser.ReferenceControlTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySqlParser#indexType}.
	 * @param ctx the parse tree
	 */
	void enterIndexType(MySqlParser.IndexTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySqlParser#indexType}.
	 * @param ctx the parse tree
	 */
	void exitIndexType(MySqlParser.IndexTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySqlParser#decimalLiteral}.
	 * @param ctx the parse tree
	 */
	void enterDecimalLiteral(MySqlParser.DecimalLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySqlParser#decimalLiteral}.
	 * @param ctx the parse tree
	 */
	void exitDecimalLiteral(MySqlParser.DecimalLiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySqlParser#hexadecimalLiteral}.
	 * @param ctx the parse tree
	 */
	void enterHexadecimalLiteral(MySqlParser.HexadecimalLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySqlParser#hexadecimalLiteral}.
	 * @param ctx the parse tree
	 */
	void exitHexadecimalLiteral(MySqlParser.HexadecimalLiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySqlParser#booleanLiteral}.
	 * @param ctx the parse tree
	 */
	void enterBooleanLiteral(MySqlParser.BooleanLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySqlParser#booleanLiteral}.
	 * @param ctx the parse tree
	 */
	void exitBooleanLiteral(MySqlParser.BooleanLiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySqlParser#collectionOptions}.
	 * @param ctx the parse tree
	 */
	void enterCollectionOptions(MySqlParser.CollectionOptionsContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySqlParser#collectionOptions}.
	 * @param ctx the parse tree
	 */
	void exitCollectionOptions(MySqlParser.CollectionOptionsContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySqlParser#engineName}.
	 * @param ctx the parse tree
	 */
	void enterEngineName(MySqlParser.EngineNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySqlParser#engineName}.
	 * @param ctx the parse tree
	 */
	void exitEngineName(MySqlParser.EngineNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySqlParser#logicalOperator}.
	 * @param ctx the parse tree
	 */
	void enterLogicalOperator(MySqlParser.LogicalOperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySqlParser#logicalOperator}.
	 * @param ctx the parse tree
	 */
	void exitLogicalOperator(MySqlParser.LogicalOperatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySqlParser#nullNotnull}.
	 * @param ctx the parse tree
	 */
	void enterNullNotnull(MySqlParser.NullNotnullContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySqlParser#nullNotnull}.
	 * @param ctx the parse tree
	 */
	void exitNullNotnull(MySqlParser.NullNotnullContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySqlParser#charsetNameBase}.
	 * @param ctx the parse tree
	 */
	void enterCharsetNameBase(MySqlParser.CharsetNameBaseContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySqlParser#charsetNameBase}.
	 * @param ctx the parse tree
	 */
	void exitCharsetNameBase(MySqlParser.CharsetNameBaseContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySqlParser#keywordsCanBeId}.
	 * @param ctx the parse tree
	 */
	void enterKeywordsCanBeId(MySqlParser.KeywordsCanBeIdContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySqlParser#keywordsCanBeId}.
	 * @param ctx the parse tree
	 */
	void exitKeywordsCanBeId(MySqlParser.KeywordsCanBeIdContext ctx);
	/**
	 * Enter a parse tree produced by {@link MySqlParser#functionNameBase}.
	 * @param ctx the parse tree
	 */
	void enterFunctionNameBase(MySqlParser.FunctionNameBaseContext ctx);
	/**
	 * Exit a parse tree produced by {@link MySqlParser#functionNameBase}.
	 * @param ctx the parse tree
	 */
	void exitFunctionNameBase(MySqlParser.FunctionNameBaseContext ctx);
}