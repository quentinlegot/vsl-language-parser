package TP2.ASD.expression.operation;

import TP2.ASD.expression.Expression;
import TP2.instruction.Instruction;
import TP2.Llvm;
import TP2.instruction.operation.Sub;

public class MinusExpression extends AbstractOperationExpression {

    public MinusExpression(Expression left, Expression right) {
        super(left, right, "-");
    }

    @Override
    protected Instruction operationInstruction(int indent, Llvm.Type type, String leftResult, String rightResult, String tmpVar) {
        return new Sub(indent, type, leftResult, rightResult, tmpVar);
    }
}
