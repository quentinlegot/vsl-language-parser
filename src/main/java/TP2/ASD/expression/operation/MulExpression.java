package TP2.ASD.expression.operation;

import TP2.ASD.expression.Expression;
import TP2.instruction.Instruction;
import TP2.instruction.operation.Mul;
import TP2.llvm.type.Type;

public class MulExpression extends AbstractOperationExpression {

    public MulExpression(Expression left, Expression right) {
        super(left, right, "*");
    }

    @Override
    protected Instruction operationInstruction(int indent, Type type, String leftResult, String rightResult, String tmpVar) {
        return new Mul(indent, type, leftResult, rightResult, tmpVar);
    }

}
