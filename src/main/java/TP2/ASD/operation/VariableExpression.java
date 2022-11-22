package TP2.ASD.operation;

import TP2.ASD.Expression;
import TP2.ASD.RetExpression;
import TP2.ASD.type.Int;
import TP2.*;
import TP2.operation.LoadVariable;

public class VariableExpression extends Expression {

    private final String name;

    public VariableExpression(String name) {
        this.name = "%" + name;
    }

    @Override
    public String pp() {
        return name;
    }

    @Override
    public RetExpression toIR(SymbolTable table) throws TypeException {
        SymbolTable.Symbol symbol = table.lookup(name);
        if(symbol instanceof SymbolTable.VariableSymbol) {
            String tmpVar = Utils.newtmp();
            RetExpression ret = new RetExpression(new Llvm.IR(Llvm.empty(), Llvm.empty()), new Int(), tmpVar);
            SymbolTable.VariableSymbol variableSymbol = (SymbolTable.VariableSymbol) symbol;
            Instruction variableLoad = new LoadVariable(tmpVar, variableSymbol.getType().toLlvmType(), name);
            ret.ir.appendCode(variableLoad);
            return ret;
        } else {
            throw new NullPointerException(name + " variable is undeclared");
        }

    }
}
