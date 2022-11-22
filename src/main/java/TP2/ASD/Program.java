package TP2.ASD;

import TP2.Llvm;
import TP2.SymbolTable;
import TP2.TypeException;

public class Program {
    Expression block; // What a program contains. TODO : change when you extend the language

    public Program(Expression block) {
      this.block = block;
    }

    /**
     * Pretty-printer
     */
    public String pp() {
        return block.pp();
    }

    /**
     * IR generation
     */
    public Llvm.IR toIR() throws TypeException {
        // computes the IR of the expression
        SymbolTable rootTable = new SymbolTable();
        RetExpression ret = block.toIR(rootTable, 1); // TODO update indent to zero when functions will be implements

        return ret.ir;
    }
  }