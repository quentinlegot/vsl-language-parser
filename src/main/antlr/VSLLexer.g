lexer grammar VSLLexer;

options {
  language = Java;
}

@header {
  package TP2;
}

WS : (' '|'\r'?'\n'|'\t') -> skip
   ;

COMMENT : '//' (~'\n')* -> skip
        ;

fragment LETTER : 'a'..'z' ;
fragment DIGIT  : '0'..'9' ;
fragment ASCII  : ~('\n'|'"');

// keywords
LP    : '(' ; // Left parenthesis
RP    : ')' ;
PLUS  : '+' ;
MUL   : '*' ;
DIV   : '/' ;
MINUS : '-' ;
EQUAL : '=' ;
COLON : ':' ;
COMMA : ',' ;
LB    : '{' ;
RB    : '}' ;
LSB   : '[' ;
RSB   : ']' ;
AFFECTSYMBOL : ':=';


// TODO : other keywords
// var types
INT : 'INT' ;

// instructions
FUNC: 'FUNC' ;
VOID: 'VOID' ;
IF  : 'IF' ;
FI  : 'FI';
THEN: 'THEN' ;
ELSE: 'ELSE' ;
WHILE : 'WHILE';
DO  : 'DO';
DONE : 'DONE' ;

// other tokens (no conflict with keywords in VSL)
IDENT   : LETTER (LETTER|DIGIT)*;
TEXT    : '"' (ASCII)* '"' { setText(getText().substring(1, getText().length() - 1)); };
INTEGER : (DIGIT)+ ;
