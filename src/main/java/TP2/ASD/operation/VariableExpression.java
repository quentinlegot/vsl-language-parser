package TP2.ASD.operation;

import TP2.ASD.Expression;
import TP2.ASD.RetExpression;
import TP2.ASD.type.Int;
import TP2.*;
import TP2.operation.LoadVariable;

public class VariableExpression extends Expression {

    private final String name;
    private final Expression tabContent; // TODO add support for tab

    public VariableExpression(String name) {
       this(name, null);
    }

    public VariableExpression(String name, Expression tabContent) {
        this.name = "%" + name;
        this.tabContent = tabContent;
    }

    @Override
    public String pp() {
        return name;
    }

    @Override
    public RetExpression toIR(SymbolTable table, int indent) throws TypeException {
        SymbolTable.Symbol symbol = table.lookup(name);
        if(symbol instanceof SymbolTable.VariableSymbol) {
            String tmpVar = Utils.newtmp();
            RetExpression ret = new RetExpression(new Llvm.IR(Llvm.empty(), Llvm.empty()), new Int(), tmpVar);
            Instruction variableLoad;
            if(symbol instanceof SymbolTable.ArgumentVariableSymbol) {
                SymbolTable.ArgumentVariableSymbol variableSymbol = (SymbolTable.ArgumentVariableSymbol) symbol;
                variableLoad = new LoadVariable(indent, tmpVar, variableSymbol.getType().toLlvmType(), variableSymbol.getNameToUse());
            } else {
                SymbolTable.VariableSymbol variableSymbol = (SymbolTable.VariableSymbol) symbol;
                variableLoad = new LoadVariable(indent, tmpVar, variableSymbol.getType().toLlvmType(), name);
            }
            ret.ir.appendCode(variableLoad);
            return ret;
        } else {
            throw new NullPointerException(name + " variable is undeclared");
        }

    }
}
