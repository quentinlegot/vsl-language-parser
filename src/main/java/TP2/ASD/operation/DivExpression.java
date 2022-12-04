package TP2.ASD.operation;

import TP2.ASD.Expression;
import TP2.Instruction;
import TP2.Llvm;
import TP2.operation.Div;

public class DivExpression extends AbstractOperationExpression {

    public DivExpression(Expression left, Expression right) {
        super(left, right, "/");
    }
    @Override
    protected Instruction operationInstruction(int indent, Llvm.Type type, String leftResult, String rightResult, String tmpVar) {
        return new Div(indent, type, leftResult, rightResult, tmpVar);
    }

}
