package TP2.ASD;

import TP2.Instruction;
import TP2.Llvm;
import TP2.Return;
import TP2.TypeException;

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
        RetExpression start = e.get(0).toIR();
        RetExpression last = start;
        if(e.size() > 1) {
           for(int i = 1; i < e.size(); i++) {
               last = e.get(i).toIR();
               start.ir.append(last.ir);
           }
        }
        // add a return instruction
        Instruction retExpr = new Return(last.type.toLlvmType(), last.result);
        start.ir.appendCode(retExpr);

        return start.ir;
    }
  }