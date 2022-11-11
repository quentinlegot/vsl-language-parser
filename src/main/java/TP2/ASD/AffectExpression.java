package TP2.ASD;

import TP2.ASD.type.Int;
import TP2.ASD.type.Tab;
import TP2.Affect;
import TP2.Instruction;
import TP2.SymbolTable;
import TP2.TypeException;

public class AffectExpression extends Expression {

    private final String name;
    private final Expression expression;
    private final boolean isTab;
    private final Expression index;

    public AffectExpression(String name, Expression expression, boolean isTab, Expression index) {
        this.name = name;
        this.expression = expression;
        this.isTab = isTab;
        this.index = index;
    }

    public AffectExpression(String name, Expression expression) {
        this(name, expression, false, null);
    }
    @Override
    public String pp() {
        String str = name;
        if(isTab) {
            str += "[" + index.pp() + "]";
        }
        str += " = " + expression.pp() + "\n";
        return str;
    }

    @Override
    public RetExpression toIR() throws TypeException {
        expression.setTable(table);
        RetExpression indexRet = null;
        if(isTab) {
            index.setTable(table);
            indexRet =index.toIR();
        }

        RetExpression ret = expression.toIR();
        SymbolTable.Symbol symbol = table.lookup(name);
        if(symbol instanceof SymbolTable.VariableSymbol) {
            SymbolTable.VariableSymbol variableSymbol = (SymbolTable.VariableSymbol) symbol;
            SymbolTable.VariableSymbol nSymbol;
            if(isTab) {
                nSymbol = new SymbolTable.VariableSymbol(new Tab<>(ret.type, variableSymbol.getType().getSize()), name);
            } else {
                nSymbol = new SymbolTable.VariableSymbol(ret.type, name);
            }
            if(!variableSymbol.equals(nSymbol)) {
                throw new IllegalArgumentException("left side of assignment is incompatible with right side\nLeft: " + variableSymbol + "\nRight: \t" + nSymbol);
            }
            Instruction affect = new Affect(isTab ? new Tab<>(ret.type, variableSymbol.getType().getSize()).toLlvmType() : ret.type.toLlvmType(), name, ret.result, indexRet != null ? indexRet.result : null);
            ret.ir.appendCode(affect);
            return new RetExpression(ret.ir, new Int(), name);
        } else {
            throw new NullPointerException(name + " is undeclared or isn't a variable");
        }
    }
}
