package TP2.ASD;

import TP2.ASD.expression.function.AbstractFunctionExpression;
import TP2.ASD.expression.function.FunctionExpression;
import TP2.ASD.expression.function.PrototypeExpression;
import TP2.ASD.type.Void;
import TP2.llvm.Llvm;
import TP2.SymbolTable;
import TP2.TypeException;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Objects;

public class Program {

    private final LinkedList<AbstractFunctionExpression> functions;

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
        if(functions.stream().filter(f -> f.getIdent().equals("main") && f instanceof FunctionExpression).count() != 1) {
            throw new IllegalArgumentException("function main doesn't exist or exist multiple times, " + functions);
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
                if(definition.getParameters() != null) {
                    if(current.getParameters() == null) {
                        throw new IllegalArgumentException("Declared parameters in prototype and definition of the function "+ current.getIdent() + " aren't the same");
                    }
                    if(definition.getParameters().size() == current.getParameters().size()) {
                        for(int i = 0; i < definition.getParameters().size(); i++) {
                            if(!definition.getParameters().get(i).a.equals(current.getParameters().get(i).a)) {
                                throw new IllegalArgumentException("Declared parameters in prototype and definition of the function "+ current.getIdent() + " aren't the same");
                            }
                        }
                    } else {
                        throw new IllegalArgumentException("Declared parameters in prototype and definition of the function "+ current.getIdent() + " aren't the same");
                    }
                }
                if(!Objects.equals(definition.getType(), current.getType())) {
                    throw new IllegalArgumentException("Return type in prototype and definition of the function " + current.getIdent() + " aren't the same");
                }
                ret.ir.append(definition.toIR(rootTable, 0).ir);
                copy.remove(definition);
                copy.remove(current);
                if(copy.stream().anyMatch(f -> f.getIdent().equals(current.getIdent()))) {
                    throw new IllegalArgumentException("Function " + current.getIdent() + " prototype or definition has been redeclared which is forbidden");
                }
            } else {
                ret.ir.append(current.toIR(rootTable, 0).ir);
                copy.remove(current);
            }
        }
        return ret.ir;

    }
  }