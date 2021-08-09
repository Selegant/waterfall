parser grammar OracleParser;

options {
    tokenVocab=OracleLexer;
}

program: root EOF;

root
    : statement SEMICOLON? EOF
    ;

statement
    : create_table
    ;

create_table
    : CREATE TABLE table_name relational_table
    ;

table_name
    : identifier ('.' id_expression)?
    ;
identifier
    : (INTRODUCER char_set_name)? id_expression
    ;
char_set_name
    : id_expression ('.' id_expression)*
    ;

id_expression
    : regular_id
    | DELIMITED_ID
    ;

relational_table
    : ('(' relational_property (',' relational_property)* ')')?
    ;

relational_property
    : column_definition
    ;
column_definition
    : column_name datatype (DEFAULT default_value)? inline_constraint*
    ;
default_value
    : UNSIGNED_INTEGER
    | CHAR_STRING
    ;

column_name
    : identifier ('.' id_expression)*
    ;

datatype
    : native_datatype_element precision_part?
    ;

inline_constraint
    : ( NOT? NULL | UNIQUE | CONSTRAINT regular_id PRIMARY KEY) constraint_state?
    ;

constraint_state
    : ( NOT? DEFERRABLE
      | INITIALLY (IMMEDIATE|DEFERRED)
      | (RELY|NORELY)
      | (ENABLE|DISABLE)
      | (VALIDATE|NOVALIDATE)
      )+
    ;
precision_part
    : '(' (numeric | ASTERISK) (',' numeric)? (CHAR | BYTE)? ')'
    ;


native_datatype_element
    : BINARY_INTEGER
    | PLS_INTEGER
    | NATURAL
    | BINARY_FLOAT
    | BINARY_DOUBLE
    | NATURALN
    | POSITIVE
    | POSITIVEN
    | SIGNTYPE
    | SIMPLE_INTEGER
    | NVARCHAR2
    | DEC
    | INTEGER
    | INT
    | NUMERIC
    | SMALLINT
    | NUMBER
    | DECIMAL
    | DOUBLE PRECISION?
    | FLOAT
    | REAL
    | NCHAR
    | LONG RAW?
    | CHAR
    | CHARACTER
    | VARCHAR2
    | VARCHAR
    | STRING
    | RAW
    | BOOLEAN
    | DATE
    | ROWID
    | UROWID
    | YEAR
    | MONTH
    | DAY
    | HOUR
    | MINUTE
    | SECOND
    | TIMEZONE_HOUR
    | TIMEZONE_MINUTE
    | TIMEZONE_REGION
    | TIMEZONE_ABBR
    | TIMESTAMP
    | TIMESTAMP_UNCONSTRAINED
    | TIMESTAMP_TZ_UNCONSTRAINED
    | TIMESTAMP_LTZ_UNCONSTRAINED
    | YMINTERVAL_UNCONSTRAINED
    | DSINTERVAL_UNCONSTRAINED
    | BFILE
    | BLOB
    | CLOB
    | NCLOB
    | MLSLABEL
    ;

numeric
    : UNSIGNED_INTEGER
    | APPROXIMATE_NUM_LIT
    ;


regular_id
    : REGULAR_ID
    ;














