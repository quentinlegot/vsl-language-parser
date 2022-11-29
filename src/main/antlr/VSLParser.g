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
  import org.antlr.v4.runtime.misc.Pair;
}

program returns [TP2.ASD.Program out]
    : e=functions EOF {
        $out = new TP2.ASD.Program($e.out);
    }
    ;

functions returns [LinkedList<TP2.ASD.function.AbstractFunctionExpression> out]
    : f=function fl=functions {
        $out = $fl.out;
        $out.addFirst($f.out);
    }
    | f=function {
        $out = new LinkedList<>();
        $out.add($f.out);
    }
    ;

function returns [TP2.ASD.function.AbstractFunctionExpression out]
    @init { LinkedList<Pair<TP2.ASD.type.Type, String>> par = null; }
    : FUNC t=function_type i=IDENT LP (fp=function_parameters { par = $fp.out; })? RP ((LB b=block RB)|(b=block)){
        $out = new TP2.ASD.function.FunctionExpression($t.out, $i.text, par, $b.out);
    }
    | PROTO t=function_type i=IDENT LP (fp=function_parameters { par = $fp.out; })? RP {
        $out = new TP2.ASD.function.PrototypeExpression($t.out, $i.text, par);
    }
    ;

function_type returns [TP2.ASD.type.Type out]
    : type {
        $out = $type.out;
    }
    | VOID {
        $out = new TP2.ASD.type.Void();
    }
    ;

function_parameters returns [LinkedList<Pair<TP2.ASD.type.Type, String>> out]
    : p=parameter COMMA fp=function_parameters {
        $out = $fp.out;
        $out.addFirst($p.out);
    }
    | p=parameter {
        $out = new LinkedList<>();
        $out.add($p.out);
    }
    ;

parameter returns [Pair<TP2.ASD.type.Type, String> out]
    : IDENT {
        $out = new Pair<>(new TP2.ASD.type.Int(), $IDENT.text);
    }
    ;


block returns [TP2.ASD.BlockExpression out]
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
    | wh=while {
        $out = $wh.out;
    }
    | print {
        $out = $print.out;
    }
    | read {
        $out = $read.out;
    }
    | returnExpr {
        $out = $returnExpr.out;
    }
    | fc=function_call {
        $out = $fc.out;
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

while returns [TP2.ASD.Expression out]
    : WHILE e=expression DO b=block DONE {
        $out = new TP2.ASD.condition.WhileExpression($e.out, $b.out);
    }
    ;

if_then_else returns [TP2.ASD.Expression out]
    @init { TP2.ASD.condition.ElseConditionExpression elsePart = null; }
    : i=if_condition (e=else_condition { elsePart = $e.out; })?  FI
    {
        $out = new TP2.ASD.condition.IfThenElseExpression($i.out, elsePart);
    }
    ;

if_condition returns [TP2.ASD.condition.IfConditionExpression out]
    : IF e=expression THEN b=block {
        $out = new TP2.ASD.condition.IfConditionExpression($e.out, $b.out);
    }
    ;

else_condition returns [TP2.ASD.condition.ElseConditionExpression out]
    : ELSE b=block {
        $out = new TP2.ASD.condition.ElseConditionExpression($b.out);
    }
    ;

print returns [TP2.ASD.PrintExpression out]
    : PRINT p=print_content_list {
        $out = new TP2.ASD.PrintExpression($p.out);
    }
    ;

function_call returns [TP2.ASD.Expression out]
    @init { LinkedList<TP2.ASD.Expression> par = null; }
    : IDENT LP (fp=parameter_function_call { par = $fp.out; })? RP {
        $out = new TP2.ASD.operation.FunctionCallExpression($IDENT.text, par);
    }
    ;

parameter_function_call returns [LinkedList<TP2.ASD.Expression> out]
    : e=expression COMMA pf=parameter_function_call {
        $out = $pf.out;
        $out.addFirst($e.out);
    }
    | e=expression {
        $out = new LinkedList<>();
        $out.add($e.out);
    }
    ;


read returns [TP2.ASD.Expression out]
    : READ p=IDENT {
        $out = new TP2.ASD.ReadExpression($p.text);
    }
    ;

print_content_list returns [LinkedList<TP2.ASD.Expression> out]
    : p=print_content COMMA pl=print_content_list {
        $out = $pl.out;
        $out.addFirst($p.out);
    }
    | p=print_content {
        $out = new LinkedList<>();
        $out.add($p.out);
    }
    ;

print_content returns [TP2.ASD.Expression out]
    : TEXT { $out = new TP2.ASD.StringExpression($TEXT.text); }
    | e=expression { $out = $e.out; }
    ;

returnExpr returns [TP2.ASD.Expression out]
    : RETURN e=expression {
        $out = new TP2.ASD.ReturnExpression($e.out);
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
    | IDENT LSB index=expression RSB { $out = new TP2.ASD.operation.VariableExpression($IDENT.text, $index.out); }
    | fc=function_call { $out = $fc.out; }
    | LP e=expression RP { $out = $e.out; }
    ;

type returns [ TP2.ASD.type.Type out ]
    : i=INT { $out = new TP2.ASD.type.Int(); }
    ;
