package TP2.ASD.operation;

import TP2.ASD.Expression;
import TP2.ASD.RetExpression;
import TP2.ASD.type.Int;
import TP2.ASD.type.Tab;
import TP2.*;
import TP2.operation.LoadVariable;

public class VariableExpression extends Expression {

    private final String name;
    private final Expression tabContent;

    public VariableExpression(String name) {
       this(name, null);
    }

    public VariableExpression(String name, Expression tabContent) {
        this.name = "%" + name;
        this.tabContent = tabContent;
    }

    @Override
    public String pp() {
        return name + (tabContent != null ? "[" + tabContent.pp() + "]": "");
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
                variableLoad = createLoadInstruction(indent, table, variableSymbol, variableSymbol.getNameToUse(), ret, tmpVar);
            } else {
                SymbolTable.VariableSymbol variableSymbol = (SymbolTable.VariableSymbol) symbol;
                variableLoad = createLoadInstruction(indent, table, variableSymbol, name, ret, tmpVar);
            }
            ret.ir.appendCode(variableLoad);
            return ret;
        } else {
            throw new NullPointerException(name + " variable is undeclared");
        }

    }

    private Instruction createLoadInstruction(int indent, SymbolTable table, SymbolTable.VariableSymbol variableSymbol, String symbolNameToUse, RetExpression ret, String tmpVar) throws TypeException {
        if(variableSymbol.getType() instanceof Tab) {
            RetExpression retTab = tabContent.toIR(table, indent);
            ret.ir.append(retTab.ir);
            return new LoadVariable(indent, tmpVar, variableSymbol.getType().toLlvmType(), symbolNameToUse, retTab.result);
        } else {
            return new LoadVariable(indent, tmpVar, variableSymbol.getType().toLlvmType(), symbolNameToUse);
        }
    }
}
