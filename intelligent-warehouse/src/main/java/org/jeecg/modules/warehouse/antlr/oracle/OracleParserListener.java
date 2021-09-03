// Generated from /Users/ye/work/hz/waterfall/data-collection/src/main/java/org/jeecg/modules/datasources/antlr/oracle/OracleParser.g4 by ANTLR 4.9.1
package org.jeecg.modules.warehouse.antlr.oracle;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link OracleParser}.
 */
public interface OracleParserListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link OracleParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(OracleParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link OracleParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(OracleParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by {@link OracleParser#root}.
	 * @param ctx the parse tree
	 */
	void enterRoot(OracleParser.RootContext ctx);
	/**
	 * Exit a parse tree produced by {@link OracleParser#root}.
	 * @param ctx the parse tree
	 */
	void exitRoot(OracleParser.RootContext ctx);
	/**
	 * Enter a parse tree produced by {@link OracleParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(OracleParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link OracleParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(OracleParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link OracleParser#create_table}.
	 * @param ctx the parse tree
	 */
	void enterCreate_table(OracleParser.Create_tableContext ctx);
	/**
	 * Exit a parse tree produced by {@link OracleParser#create_table}.
	 * @param ctx the parse tree
	 */
	void exitCreate_table(OracleParser.Create_tableContext ctx);
	/**
	 * Enter a parse tree produced by {@link OracleParser#table_name}.
	 * @param ctx the parse tree
	 */
	void enterTable_name(OracleParser.Table_nameContext ctx);
	/**
	 * Exit a parse tree produced by {@link OracleParser#table_name}.
	 * @param ctx the parse tree
	 */
	void exitTable_name(OracleParser.Table_nameContext ctx);
	/**
	 * Enter a parse tree produced by {@link OracleParser#identifier}.
	 * @param ctx the parse tree
	 */
	void enterIdentifier(OracleParser.IdentifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link OracleParser#identifier}.
	 * @param ctx the parse tree
	 */
	void exitIdentifier(OracleParser.IdentifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link OracleParser#char_set_name}.
	 * @param ctx the parse tree
	 */
	void enterChar_set_name(OracleParser.Char_set_nameContext ctx);
	/**
	 * Exit a parse tree produced by {@link OracleParser#char_set_name}.
	 * @param ctx the parse tree
	 */
	void exitChar_set_name(OracleParser.Char_set_nameContext ctx);
	/**
	 * Enter a parse tree produced by {@link OracleParser#id_expression}.
	 * @param ctx the parse tree
	 */
	void enterId_expression(OracleParser.Id_expressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link OracleParser#id_expression}.
	 * @param ctx the parse tree
	 */
	void exitId_expression(OracleParser.Id_expressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link OracleParser#relational_table}.
	 * @param ctx the parse tree
	 */
	void enterRelational_table(OracleParser.Relational_tableContext ctx);
	/**
	 * Exit a parse tree produced by {@link OracleParser#relational_table}.
	 * @param ctx the parse tree
	 */
	void exitRelational_table(OracleParser.Relational_tableContext ctx);
	/**
	 * Enter a parse tree produced by {@link OracleParser#relational_property}.
	 * @param ctx the parse tree
	 */
	void enterRelational_property(OracleParser.Relational_propertyContext ctx);
	/**
	 * Exit a parse tree produced by {@link OracleParser#relational_property}.
	 * @param ctx the parse tree
	 */
	void exitRelational_property(OracleParser.Relational_propertyContext ctx);
	/**
	 * Enter a parse tree produced by {@link OracleParser#column_definition}.
	 * @param ctx the parse tree
	 */
	void enterColumn_definition(OracleParser.Column_definitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link OracleParser#column_definition}.
	 * @param ctx the parse tree
	 */
	void exitColumn_definition(OracleParser.Column_definitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link OracleParser#default_value}.
	 * @param ctx the parse tree
	 */
	void enterDefault_value(OracleParser.Default_valueContext ctx);
	/**
	 * Exit a parse tree produced by {@link OracleParser#default_value}.
	 * @param ctx the parse tree
	 */
	void exitDefault_value(OracleParser.Default_valueContext ctx);
	/**
	 * Enter a parse tree produced by {@link OracleParser#column_name}.
	 * @param ctx the parse tree
	 */
	void enterColumn_name(OracleParser.Column_nameContext ctx);
	/**
	 * Exit a parse tree produced by {@link OracleParser#column_name}.
	 * @param ctx the parse tree
	 */
	void exitColumn_name(OracleParser.Column_nameContext ctx);
	/**
	 * Enter a parse tree produced by {@link OracleParser#datatype}.
	 * @param ctx the parse tree
	 */
	void enterDatatype(OracleParser.DatatypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link OracleParser#datatype}.
	 * @param ctx the parse tree
	 */
	void exitDatatype(OracleParser.DatatypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link OracleParser#inline_constraint}.
	 * @param ctx the parse tree
	 */
	void enterInline_constraint(OracleParser.Inline_constraintContext ctx);
	/**
	 * Exit a parse tree produced by {@link OracleParser#inline_constraint}.
	 * @param ctx the parse tree
	 */
	void exitInline_constraint(OracleParser.Inline_constraintContext ctx);
	/**
	 * Enter a parse tree produced by {@link OracleParser#constraint_state}.
	 * @param ctx the parse tree
	 */
	void enterConstraint_state(OracleParser.Constraint_stateContext ctx);
	/**
	 * Exit a parse tree produced by {@link OracleParser#constraint_state}.
	 * @param ctx the parse tree
	 */
	void exitConstraint_state(OracleParser.Constraint_stateContext ctx);
	/**
	 * Enter a parse tree produced by {@link OracleParser#precision_part}.
	 * @param ctx the parse tree
	 */
	void enterPrecision_part(OracleParser.Precision_partContext ctx);
	/**
	 * Exit a parse tree produced by {@link OracleParser#precision_part}.
	 * @param ctx the parse tree
	 */
	void exitPrecision_part(OracleParser.Precision_partContext ctx);
	/**
	 * Enter a parse tree produced by {@link OracleParser#native_datatype_element}.
	 * @param ctx the parse tree
	 */
	void enterNative_datatype_element(OracleParser.Native_datatype_elementContext ctx);
	/**
	 * Exit a parse tree produced by {@link OracleParser#native_datatype_element}.
	 * @param ctx the parse tree
	 */
	void exitNative_datatype_element(OracleParser.Native_datatype_elementContext ctx);
	/**
	 * Enter a parse tree produced by {@link OracleParser#numeric}.
	 * @param ctx the parse tree
	 */
	void enterNumeric(OracleParser.NumericContext ctx);
	/**
	 * Exit a parse tree produced by {@link OracleParser#numeric}.
	 * @param ctx the parse tree
	 */
	void exitNumeric(OracleParser.NumericContext ctx);
	/**
	 * Enter a parse tree produced by {@link OracleParser#regular_id}.
	 * @param ctx the parse tree
	 */
	void enterRegular_id(OracleParser.Regular_idContext ctx);
	/**
	 * Exit a parse tree produced by {@link OracleParser#regular_id}.
	 * @param ctx the parse tree
	 */
	void exitRegular_id(OracleParser.Regular_idContext ctx);
}