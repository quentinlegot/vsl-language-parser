package TP2.ASD.expression;

import TP2.ASD.RetExpression;
import TP2.ASD.type.Int;
import TP2.ASD.type.Tab;
import TP2.instruction.Affect;
import TP2.instruction.Instruction;
import TP2.SymbolTable;
import TP2.TypeException;

public class TabAffectExpression extends AbstractAffectExpression {

    private final Expression index;

    public TabAffectExpression(String name, Expression expression, Expression index) {
        super(name, expression);
        this.index = index;
    }

    @Override
    public String pp() {
        return name + "[" + index.pp() + "] := " + expression.pp() + "\n";
    }

    @Override
    public RetExpression toIR(SymbolTable table, int indent) throws TypeException {

        RetExpression indexRet = index.toIR(table, indent);
        RetExpression ret = expression.toIR(table, indent);
        indexRet.ir.append(ret.ir);
        SymbolTable.Symbol symbol = table.lookup(name);
        if(symbol instanceof SymbolTable.VariableSymbol) {
            SymbolTable.VariableSymbol variableSymbol = (SymbolTable.VariableSymbol) symbol;
            SymbolTable.VariableSymbol nSymbol = new SymbolTable.VariableSymbol(new Tab<>(ret.type, variableSymbol.getType().getSize()), name);
            if(!variableSymbol.equals(nSymbol)) {
                throw new IllegalArgumentException("left side of assignment is incompatible with right side\nLeft: " + variableSymbol + "\nRight: \t" + nSymbol);
            }
            Instruction affect = new Affect(indent, new Tab<>(ret.type, variableSymbol.getType().getSize()).toLlvmType(), name, ret.result, indexRet.result);
            indexRet.ir.appendCode(affect);
            return new RetExpression(indexRet.ir, new Int(), name);
        } else {
            throw new NullPointerException(name + " is undeclared or isn't a variable");
        }
    }
}
