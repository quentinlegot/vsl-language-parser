package TP2.ASD;

import TP2.*;

public class MulExpression extends Expression {

    Expression left;
    Expression right;

    public MulExpression(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }
    @Override
    public String pp() {
        return "(" + left.pp() + " * " + right.pp() + ")";
    }

    @Override
    public RetExpression toIR() throws TypeException {
        RetExpression leftRet = left.toIR();
        RetExpression rightRet = right.toIR();

        if(!leftRet.type.equals(rightRet.type)) {
            throw new TypeException("Type mismatch: have " + leftRet.type + " and " + rightRet.type);
        }

        leftRet.ir.append(rightRet.ir);
        String result = Utils.newtmp();
        Instruction mul = new Mul(leftRet.type.toLlvmType(), leftRet.result, rightRet.result, result);
        leftRet.ir.appendCode(mul);
        return new RetExpression(leftRet.ir, leftRet.type, result);
    }
}
