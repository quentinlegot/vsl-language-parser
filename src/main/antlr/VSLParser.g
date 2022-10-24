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
    : i=IDENT EQUAL is=instruction {
        $out = new TP2.ASD.AffectExpression($i.text, $is.out);
    }
    | e=expression {
        $out = $e.out;
    }
    ;

expression returns [TP2.ASD.Expression out]
    : MINUS e=expression { $out= new TP2.ASD.MulExpression(new TP2.ASD.IntegerExpression(-1), $e.out); }
    | l=expression MUL  r=expression  { $out = new TP2.ASD.MulExpression($l.out, $r.out); }
    | l=expression DIV r=expression   { $out = new TP2.ASD.DivExpression($l.out, $r.out); }
    | l=expression PLUS r=expression  { $out = new TP2.ASD.AddExpression($l.out, $r.out); }
    | l=expression MINUS r=expression { $out = new TP2.ASD.MinusExpression($l.out, $r.out); }
    | LP e=expression RP { $out=$e.out; }
    | f=factor { $out = $f.out; }
    // TODO : that's all?
    ;

factor returns [TP2.ASD.Expression out]
    : p=primary { $out = $p.out; }
    // TODO : that's all?
    ;

primary returns [TP2.ASD.Expression out]
    : INTEGER { $out = new TP2.ASD.IntegerExpression($INTEGER.int); }
    // TODO : that's all?
    ;
