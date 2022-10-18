lexer grammar VSLLexer;

options {
  language = Java;
}

@header {
  package TP2;
}

WS : (' '|'\n'|'\t') -> skip
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
EQUALS : '=';
COMMA : ',' ;
LB : '{' ;
RB : '}' ;

// TODO : other keywords
// var types
INT : 'INT' ;

// instructions
FUNC: 'FUNC' ;
VOID: 'VOID' ;

// other tokens (no conflict with keywords in VSL)
IDENT   : LETTER (LETTER|DIGIT)*;
TEXT    : '"' (ASCII)* '"' { setText(getText().substring(1, getText().length() - 1)); };
INTEGER : (DIGIT)+ ;
