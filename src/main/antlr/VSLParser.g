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


// TODO : other rules

program returns [TP2.ASD.Program out]
    : e=instructionList EOF { $out = new TP2.ASD.Program($e.out); } // TODO : change when you extend the language
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
    | e=expression {
        $out = $e.out;
    }
    ;

affectation returns [TP2.ASD.Expression out]
    : i=IDENT COLON EQUAL is=expression {
        $out = new TP2.ASD.AffectExpression($i.text, $is.out);
    }
    ;

declaration returns [TP2.ASD.Expression out]
    : t=type { LinkedList<TP2.ASD.DeclareExpression> l = new LinkedList<>(); }
    (((i=IDENT { l.add(new TP2.ASD.DeclareExpression($t.out, $i.text, l)); })
        | (i=IDENT LSB INTEGER RSB { l.add(new TP2.ASD.DeclareExpression(new TP2.ASD.type.Tab($t.out), $i.text, l, new TP2.ASD.IntegerExpression($INTEGER.int))); })) COMMA? )*
     {
        $out = l.removeLast();
     }
    ;

expression returns [TP2.ASD.Expression out]
    : MINUS e=expression { $out= new TP2.ASD.operation.MulExpression(new TP2.ASD.IntegerExpression(-1), $e.out); }
    | l=expression MUL  r=expression  { $out = new TP2.ASD.operation.MulExpression($l.out, $r.out); }
    | l=expression DIV r=expression   { $out = new TP2.ASD.operation.DivExpression($l.out, $r.out); }
    | l=expression PLUS r=expression  { $out = new TP2.ASD.operation.AddExpression($l.out, $r.out); }
    | l=expression MINUS r=expression { $out = new TP2.ASD.operation.MinusExpression($l.out, $r.out); }
    | LP e=expression RP { $out=$e.out; }
    | f=primary { $out = $f.out; }
    ;

primary returns [TP2.ASD.Expression out]
    : INTEGER { $out = new TP2.ASD.IntegerExpression($INTEGER.int); }
    | IDENT { $out = new TP2.ASD.operation.VariableExpression($IDENT.text); }
    ;

type returns [ TP2.ASD.type.Type out ]
    : i=INT { $out = new TP2.ASD.type.Int(); }
    // TODO add more types (like char)
    ;
