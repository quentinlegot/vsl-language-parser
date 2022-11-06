package TP2.ASD.operation;

import TP2.*;
import TP2.ASD.Expression;
import TP2.ASD.RetExpression;
import TP2.operation.Div;

public class DivExpression extends Expression {

    Expression left;
    Expression right;

    public DivExpression(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public String pp() {
        return "(" + left.pp() + " / " + right.pp() + ")";
    }

    @Override
    public RetExpression toIR() throws TypeException {
        RetExpression leftRet = left.toIR();
        RetExpression rightRet = right.toIR();

        // We check if the types mismatches
        if(!leftRet.type.equals(rightRet.type)) {
            throw new TypeException("type mismatch: have " + leftRet.type + " and " + rightRet.type);
        }

        // We base our build on the left generated IR:
        // append right code
        leftRet.ir.append(rightRet.ir);

        // allocate a new identifier for the result
        String result = Utils.newtmp();

        // new add instruction result = left + right
        Instruction div = new Div(leftRet.type.toLlvmType(), leftRet.result, rightRet.result, result);

        // append this instruction
        leftRet.ir.appendCode(div);

        // return the generated IR, plus the type of this expression
        // and where to find its result
        return new RetExpression(leftRet.ir, leftRet.type, result);
    }
}
