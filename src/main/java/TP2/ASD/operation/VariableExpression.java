package TP2.ASD.operation;

import TP2.ASD.Expression;
import TP2.ASD.RetExpression;
import TP2.ASD.type.Int;
import TP2.Llvm;
import TP2.SymbolTable;
import TP2.TypeException;
import TP2.Utils;

public class VariableExpression extends Expression {

    private final String name;

    public VariableExpression(String name) {
        this.name = name;
    }

    @Override
    public String pp() {
        return name;
    }

    @Override
    public RetExpression toIR() throws TypeException {
        SymbolTable.Symbol symbol = table.lookup(name);
        if(symbol instanceof SymbolTable.VariableSymbol) {
            SymbolTable.VariableSymbol variableSymbol = (SymbolTable.VariableSymbol) symbol;
            String tmpVar = Utils.newtmp();
            String result = tmpVar + " = load " + variableSymbol.getType().toLlvmType().toString() + ", " +
                    variableSymbol.getType().toLlvmType().toString() + "* %" + name + "\n";
            return new RetExpression(new Llvm.IR(Llvm.empty(), Llvm.empty()), new Int(), result);
        } else {
            throw new NullPointerException(name + " variable is undeclared");
        }

    }
}
