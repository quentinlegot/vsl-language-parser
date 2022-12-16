package TP2.ASD.expression.operation;

import TP2.ASD.expression.Expression;
import TP2.instruction.Instruction;
import TP2.Llvm;
import TP2.instruction.operation.Add;

/**
 * Concrete class for Expression: add case
 */
public class AddExpression extends AbstractOperationExpression {

    public AddExpression(Expression left, Expression right) {
      super(left, right, "+");
    }

  @Override
  protected Instruction operationInstruction(int indent, Llvm.Type type, String leftResult, String rightResult, String tmpVar) {
    return new Add(indent, type, leftResult, rightResult, tmpVar);
  }
}