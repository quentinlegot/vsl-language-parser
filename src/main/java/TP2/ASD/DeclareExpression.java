package TP2.ASD;

import TP2.*;
import TP2.ASD.type.Int;
import TP2.ASD.type.Type;

public class DeclareExpression extends Expression {

    private final Type type;
    private final String name;
    private final Expression expression;

    public DeclareExpression(Type type, String name, Expression expression) {
        this.type = type;
        this.name = name;
        this.expression = expression;
    }

    @Override
    public String pp() {
        return "INT " + name + " = " + expression.pp() + "\n";
    }

    @Override
    public RetExpression toIR() throws TypeException {
        expression.setTable(table);
        RetExpression ret = expression.toIR();
        table.add(new SymbolTable.VariableSymbol(type, name));
        Instruction declare = new Declare(type.toLlvmType(), name, ret.result);
        ret.ir.appendCode(declare);
        return new RetExpression(ret.ir, new Int(), name);
    }
}
