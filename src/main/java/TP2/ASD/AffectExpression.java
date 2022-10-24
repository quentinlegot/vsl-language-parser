package TP2.ASD;

import TP2.Affect;
import TP2.Instruction;
import TP2.TypeException;
import TP2.Utils;

public class AffectExpression extends Expression {

    private final String name;
    private final Expression expression;

    public AffectExpression(String name, Expression expression) {
        this.name = name;
        this.expression = expression;
    }
    @Override
    public String pp() {
        return "INT " + name + " = " + expression.pp();
    }

    @Override
    public RetExpression toIR() throws TypeException {
        RetExpression ret = expression.toIR();

        String result = Utils.newtmp();
        Instruction affect = new Affect(ret.type.toLlvmType(), result, ret.result);
        ret.ir.appendCode(affect);
        return new RetExpression(ret.ir, new Int(), result);
    }
}
