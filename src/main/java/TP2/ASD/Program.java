package TP2.ASD;

import TP2.ASD.function.AbstractFunctionExpression;
import TP2.ASD.function.FunctionExpression;
import TP2.ASD.function.PrototypeExpression;
import TP2.ASD.type.Void;
import TP2.Llvm;
import TP2.SymbolTable;
import TP2.TypeException;

import java.util.ArrayList;
import java.util.LinkedList;

public class Program {
    LinkedList<AbstractFunctionExpression> functions; // What a program contains. TODO : change when you extend the language

    public Program(LinkedList<AbstractFunctionExpression> functions) {
      this.functions = functions;
    }

    /**
     * Pretty-printer
     */
    public String pp() {
        StringBuilder str = new StringBuilder();
        for(AbstractFunctionExpression expression : functions) {
            str.append(expression.pp());
        }
        return str.toString();
    }

    /**
     * IR generation
     */
    public Llvm.IR toIR() throws TypeException {
        // computes the IR of the expression
        SymbolTable rootTable = new SymbolTable();
        // check if function main exist
        RetExpression ret = new RetExpression(new Llvm.IR(), new Void(), "");
        if(functions.stream().filter(f -> f.getIdent().equals("main")).count() != 1) {
            throw new IllegalArgumentException("function main doesn't exist or exist multiple times, " + functions.toString());
        }
        ArrayList<AbstractFunctionExpression> copy = new ArrayList<>(functions);
        while(!copy.isEmpty()) {
            AbstractFunctionExpression current = copy.get(0);
            if(current instanceof PrototypeExpression) {
                // Search for definition
                AbstractFunctionExpression definition = copy.stream()
                        .filter(f -> f.getIdent().equals(current.getIdent()) && f instanceof FunctionExpression)
                        .findFirst()
                        .orElseThrow(() -> new NullPointerException("Function " + current.getIdent() + " have a prototype but doesn't have a definition"));
                ret.ir.append(definition.toIR(rootTable, 0).ir);
                copy.remove(definition);
                copy.remove(current);
            } else {
                ret.ir.append(current.toIR(rootTable, 0).ir);
                copy.remove(current);
            }
        }
        return ret.ir;

    }
  }