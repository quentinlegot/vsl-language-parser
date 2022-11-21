parser grammar VSLParser;

options {
  language = Java;
  tokenVocab = VSLLexer;
}

@header {
  package TP2;

  import java.util.stream.Collectors;
  import java.util.Arrays;
  import java.util.LinkedList;
}

program returns [TP2.ASD.Program out]
    : e=block EOF { $out = new TP2.ASD.Program($e.out); } // TODO : change when you extend the language
    ;

block returns [TP2.ASD.Expression out]
    : e=instructionList { $out = new TP2.ASD.BlockExpression($e.out); }
    ;

instructionList returns [LinkedList<TP2.ASD.Expression> out]
    : i=instruction il=instructionList {
        $out = $il.out;
        $out.addFirst($i.out);
    }
    | i=instruction {
        $out = new LinkedList<>();
        $out.add($i.out);
    }
    ;

instruction returns [TP2.ASD.Expression out]
    : a=affectation {
        $out = $a.out;
    }
    | d=declaration {
        $out = $d.out;
    }
    | ifi=if_then_else {
        $out = $ifi.out;
    }
    ;

affectation returns [TP2.ASD.AbstractAffectExpression out]
    : i=IDENT AFFECTSYMBOL is=expression {
        $out = new TP2.ASD.SimpleAffectExpression($i.text, $is.out);
    }
    | i=IDENT LSB index=expression RSB AFFECTSYMBOL e=expression {
        $out = new TP2.ASD.TabAffectExpression($i.text, $e.out, $index.out);
    }
    ;

declaration returns [TP2.ASD.AbstractDeclareExpression out]
    : t=type { LinkedList<TP2.ASD.AbstractDeclareExpression> l = new LinkedList<>(); }
    (((i=IDENT { l.add(new TP2.ASD.SimpleDeclareExpression($t.out, $i.text, l)); })
        | (i=IDENT LSB INTEGER RSB { l.add(new TP2.ASD.TabDeclareExpression(new TP2.ASD.type.Tab($t.out, $INTEGER.int), $i.text, l, new TP2.ASD.IntegerExpression($INTEGER.int))); })) COMMA? )*
     {
        $out = l.removeLast();
     }
    ;

if_then_else returns [TP2.ASD.Expression out]
    @init { TP2.ASD.Expression elsePart = null; }
    : i=if_condition (e=else_condition { elsePart = $e.out; })?  FI
    {
        $out = new TP2.ASD.condition.IfThenElseExpression($i.out, elsePart);
    }
    ;

if_condition returns [TP2.ASD.Expression out]
    : IF bo=boolean_condition THEN b=block {
        $out = new TP2.ASD.condition.IfConditionExpression($bo.out, $b.out);
    }
    | IF bo=boolean_condition THEN i=instruction {
        $out = new TP2.ASD.condition.IfConditionExpression($bo.out, $i.out);
    }
    ;

else_condition returns [TP2.ASD.Expression out]
    : ELSE b=block {
        $out = new TP2.ASD.condition.ElseConditionExpression($b.out);
    }
    | ELSE i=instruction {
        $out = new TP2.ASD.condition.ElseConditionExpression($i.out);
    }
    ;

boolean_condition returns [TP2.ASD.Expression out]
    : e=expression {
        $out = new TP2.ASD.condition.BooleanConditionExpression($e.out);
    }
    ;

expression returns [TP2.ASD.Expression out]
    : l=expression PLUS r=factor  { $out = new TP2.ASD.operation.AddExpression($l.out, $r.out); }
    | l=expression MINUS r=factor { $out = new TP2.ASD.operation.MinusExpression($l.out, $r.out); }
    | f=factor { $out = $f.out; }
    ;

factor returns [ TP2.ASD.Expression out ]
    : l=factor MUL r=factor { $out = new TP2.ASD.operation.MulExpression($l.out, $r.out); }
    | l=factor DIV r=factor { $out = new TP2.ASD.operation.DivExpression($l.out, $r.out); }
    | primary { $out = $primary.out; }
    ;

primary returns [TP2.ASD.Expression out]
    : MINUS p=primary { $out = new TP2.ASD.operation.MulExpression(new TP2.ASD.IntegerExpression(-1), $p.out); }
    | INTEGER { $out = new TP2.ASD.IntegerExpression($INTEGER.int); }
    | IDENT { $out = new TP2.ASD.operation.VariableExpression($IDENT.text); }
    | LP e=expression RP { $out = $e.out; }
    ;

type returns [ TP2.ASD.type.Type out ]
    : i=INT { $out = new TP2.ASD.type.Int(); }
    // TODO add more types (like char)
    ;
