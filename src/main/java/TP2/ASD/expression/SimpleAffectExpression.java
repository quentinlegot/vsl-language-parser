package TP2.ASD.expression;

import TP2.ASD.RetExpression;
import TP2.ASD.type.Int;
import TP2.instruction.Affect;
import TP2.instruction.Instruction;
import TP2.SymbolTable;
import TP2.TypeException;

public class SimpleAffectExpression extends AbstractAffectExpression {

    public SimpleAffectExpression(String name, Expression expression, boolean isTab, Expression index) {
        super(name, expression);
    }

    public SimpleAffectExpression(String name, Expression expression) {
        this(name, expression, false, null);
    }
    @Override
    public String pp() {
        return name + " := " + expression.pp() + "\n";
    }

    @Override
    public RetExpression toIR(SymbolTable table, int indent) throws TypeException {
        SymbolTable.Symbol symbol = table.lookup(name);
        if(symbol instanceof SymbolTable.VariableSymbol) {
            RetExpression ret = expression.toIR(table, indent);
            SymbolTable.VariableSymbol nSymbol = new SymbolTable.VariableSymbol(ret.type, name);
            if(!symbol.equals(nSymbol)) {
                throw new IllegalArgumentException("left side of assignment is incompatible with right side\nLeft: " + symbol + "\nRight: \t" + nSymbol);
            }
            Instruction affect = new Affect(indent, ret.type.toLlvmType(), name, ret.result, null);
            ret.ir.appendCode(affect);
            return new RetExpression(ret.ir, new Int(), name);
        } else {
            throw new NullPointerException(name + " is undeclared or isn't a variable");
        }
    }
}
