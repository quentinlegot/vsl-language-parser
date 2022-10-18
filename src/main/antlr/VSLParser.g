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
  import java.util.Collections;
  import TP2.ASD.*;
}


// TODO : other rules

program returns [ TP2.ASD.Program out ]
    : e=function_list EOF { $out = new TP2.ASD.Program($e.out); } // TODO : change when you extend the language
    ;

function_list returns [ List<TP2.ASD.Function> out ]
    : f=function fl=function_list {
        $out = $fl.out;
        ((LinkedList)$out).addFirst($f.out);
    }
    | f=function {
        $out = new LinkedList<>();
        $out.add($f.out);
    }
    ;

function returns [ TP2.ASD.Function out ]
    : FUNC r=return_type t=IDENT LP p=function_parameters_list RP b=block
    {
        $out = new Function($r.out, $t.text, $p.parameters, $b.out);
    }
    | FUNC r=return_type t=IDENT LP RP b=block
    {
        $out = new Function($r.out, $t.text, Collections.emptyList(), $b.out);
    }
    ;

return_type returns [ String out ]
    : t=type { $out = $t.text; }
    | v=VOID { $out = $v.text; }
    ;

function_parameters_list returns [ List<String> parameters ]
    : t=TEXT COMMA f=function_parameters_list
    {
        $parameters = $f.parameters;
        ((LinkedList) $parameters).addFirst($t.text);
    }
    | t=TEXT
    {
        $parameters = new LinkedList<>();
        $parameters.add($t.text);
    }
    ;

block returns [ TP2.ASD.Block out ]
    : LB i=instructions_list RB { $out = new Block($i.out); }
    ;

instructions_list returns [ List<TP2.ASD.AsdInstruction> out ]
    : i=instruction il=instructions_list {
        $out = $il.out;
        ((LinkedList) $out).addFirst($i.out);
    }
    | i=instruction {
        $out = new LinkedList<>();
        $out.add($i.out);
    }
    ;

instruction returns [ TP2.ASD.AsdInstruction out ]
    : a=affectations { $out = $a.out; }
    ;

affectations returns [TP2.ASD.AsdInstruction out]
    : INT i=IDENT EQUALS e=expression { $out= new TP2.ASD.AffectInstruction(true, $i.text, $e.out); }
    | i=IDENT EQUALS e=expression { $out= new TP2.ASD.AffectInstruction(false, $i.text, $e.out); }
    ;

type returns [ String out ]
    : i=INT { $out = $i.text; }
    // TODO add more types (like char)
    ;

expression returns [TP2.ASD.Expression out]
    : MINUS e=expression { $out= new TP2.ASD.MulExpression(new TP2.ASD.IntegerExpression(-1), $e.out); }
    | l=expression PLUS r=expression  { $out = new TP2.ASD.AddExpression($l.out, $r.out); }
    | l=expression MINUS r=expression { $out = new TP2.ASD.MinusExpression($l.out, $r.out); }
    | l=expression MUL  r=expression  { $out = new TP2.ASD.MulExpression($l.out, $r.out); }
    | l=expression DIV r=expression   { $out = new TP2.ASD.DivExpression($l.out, $r.out); }
    | LP e=expression RP { $out=$e.out; }
    | f=factor { $out = $f.out; }
    ;

factor returns [TP2.ASD.Expression out]
    : p=primary { $out = $p.out; }
    // TODO : that's all?
    ;

primary returns [TP2.ASD.Expression out]
    : INTEGER { $out = new TP2.ASD.IntegerExpression($INTEGER.int); }
    // TODO : that's all?
    ;
