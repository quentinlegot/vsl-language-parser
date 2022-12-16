package TP2.ASD.expression.operation;

import TP2.ASD.expression.Expression;
import TP2.instruction.Instruction;
import TP2.instruction.operation.Div;
import TP2.llvm.type.Type;

public class DivExpression extends AbstractOperationExpression {

    public DivExpression(Expression left, Expression right) {
        super(left, right, "/");
    }
    @Override
    protected Instruction operationInstruction(int indent, Type type, String leftResult, String rightResult, String tmpVar) {
        return new Div(indent, type, leftResult, rightResult, tmpVar);
    }

}
