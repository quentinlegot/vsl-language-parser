package TP2.ASD;

import TP2.*;

import java.util.List;

public class Program {
    List<Expression> e; // What a program contains. TODO : change when you extend the language

    public Program(List<Expression> e) {
      this.e = e;
    }

    /**
     * Pretty-printer
     */
    public String pp() {
        StringBuilder str = new StringBuilder();
        for(Expression ex : e) {
            str.append(ex.pp());
        }
        return str.toString();
    }

    /**
     * IR generation
     */
    public Llvm.IR toIR() throws TypeException {
        // computes the IR of the expression
        SymbolTable rootTable = new SymbolTable();
        Expression start = e.get(0);
        start.setTable(rootTable);
        RetExpression startRet = start.toIR();
        RetExpression last = startRet;

        if(e.size() > 1) {
           for(int i = 1; i < e.size(); i++) {
               Expression ex = e.get(i);
               ex.setTable(rootTable);
               last = ex.toIR();
               startRet.ir.append(last.ir);
           }
        }
        // add a return instruction
        Instruction retExpr = new Return(last.type.toLlvmType(), last.result);
        startRet.ir.appendCode(retExpr);

        return startRet.ir;
    }
  }