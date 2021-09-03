// Generated from /Users/ye/work/hz/waterfall/data-collection/src/main/java/org/jeecg/modules/datasources/antlr/oracle/OracleParser.g4 by ANTLR 4.9.1
package org.jeecg.modules.warehouse.antlr.oracle;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link OracleParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface OracleParserVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link OracleParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(OracleParser.ProgramContext ctx);
	/**
	 * Visit a parse tree produced by {@link OracleParser#root}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRoot(OracleParser.RootContext ctx);
	/**
	 * Visit a parse tree produced by {@link OracleParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(OracleParser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link OracleParser#create_table}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCreate_table(OracleParser.Create_tableContext ctx);
	/**
	 * Visit a parse tree produced by {@link OracleParser#table_name}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTable_name(OracleParser.Table_nameContext ctx);
	/**
	 * Visit a parse tree produced by {@link OracleParser#identifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdentifier(OracleParser.IdentifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link OracleParser#char_set_name}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitChar_set_name(OracleParser.Char_set_nameContext ctx);
	/**
	 * Visit a parse tree produced by {@link OracleParser#id_expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitId_expression(OracleParser.Id_expressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link OracleParser#relational_table}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRelational_table(OracleParser.Relational_tableContext ctx);
	/**
	 * Visit a parse tree produced by {@link OracleParser#relational_property}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRelational_property(OracleParser.Relational_propertyContext ctx);
	/**
	 * Visit a parse tree produced by {@link OracleParser#column_definition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitColumn_definition(OracleParser.Column_definitionContext ctx);
	/**
	 * Visit a parse tree produced by {@link OracleParser#default_value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDefault_value(OracleParser.Default_valueContext ctx);
	/**
	 * Visit a parse tree produced by {@link OracleParser#column_name}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitColumn_name(OracleParser.Column_nameContext ctx);
	/**
	 * Visit a parse tree produced by {@link OracleParser#datatype}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDatatype(OracleParser.DatatypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link OracleParser#inline_constraint}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInline_constraint(OracleParser.Inline_constraintContext ctx);
	/**
	 * Visit a parse tree produced by {@link OracleParser#constraint_state}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstraint_state(OracleParser.Constraint_stateContext ctx);
	/**
	 * Visit a parse tree produced by {@link OracleParser#precision_part}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrecision_part(OracleParser.Precision_partContext ctx);
	/**
	 * Visit a parse tree produced by {@link OracleParser#native_datatype_element}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNative_datatype_element(OracleParser.Native_datatype_elementContext ctx);
	/**
	 * Visit a parse tree produced by {@link OracleParser#numeric}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNumeric(OracleParser.NumericContext ctx);
	/**
	 * Visit a parse tree produced by {@link OracleParser#regular_id}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRegular_id(OracleParser.Regular_idContext ctx);
}