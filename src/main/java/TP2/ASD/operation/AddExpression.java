package TP2.ASD.operation;

import TP2.ASD.Expression;
import TP2.Instruction;
import TP2.Llvm;
import TP2.operation.Add;

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