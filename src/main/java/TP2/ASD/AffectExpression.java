package TP2.ASD;

import TP2.ASD.type.Int;
import TP2.Affect;
import TP2.Instruction;
import TP2.SymbolTable;
import TP2.TypeException;

public class AffectExpression extends Expression {

    private final String name;
    private final Expression expression;

    public AffectExpression(String name, Expression expression) {
        this.name = name;
        this.expression = expression;
    }
    @Override
    public String pp() {
        return name + " = " + expression.pp() + "\n";
    }

    @Override
    public RetExpression toIR() throws TypeException {
        expression.setTable(table);
        RetExpression ret = expression.toIR();
        SymbolTable.Symbol symbol = table.lookup(name);
        if(symbol == null)
            throw new NullPointerException(name + " variable is undeclared");
        Instruction affect = new Affect(ret.type.toLlvmType(), name, ret.result);
        ret.ir.appendCode(affect);
        return new RetExpression(ret.ir, new Int(), name);
    }
}
