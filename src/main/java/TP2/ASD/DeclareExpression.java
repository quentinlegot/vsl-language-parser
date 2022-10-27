package TP2.ASD;

import TP2.*;

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
        RetExpression ret = expression.toIR();

        String result = Utils.newtmp();
        Instruction declare = new Declare(type.toLlvmType(), result, ret.result);
        ret.ir.appendCode(declare);
        return new RetExpression(ret.ir, new Int(), result);
    }
}
