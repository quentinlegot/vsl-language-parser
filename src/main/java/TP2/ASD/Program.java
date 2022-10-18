package TP2.ASD;

import TP2.Llvm;
import TP2.TypeException;

import java.util.List;

public class Program {
    List<FunctionAsd> functions; // What a program contains.

    public Program(List<FunctionAsd> functions) {
      this.functions = functions;
    }

    /**
     * Pretty-printer
     */
    public String pp() {
        StringBuilder str = new StringBuilder();
        for(FunctionAsd f : functions) {
            str.append(f.pp());
        }
      return str.toString();
    }

    /**
     * IR generation
     */
    public Llvm.IR toIR() throws TypeException {
        Llvm.IR ir = new Llvm.IR(Llvm.empty(), Llvm.empty());
        for(FunctionAsd f : functions) {
            FunctionAsd.RetExpression ret = f.toIR();
            // ir.appendCode(new Return(Llvm.Type, ret.result));
        }
        return ir;
    }
  }