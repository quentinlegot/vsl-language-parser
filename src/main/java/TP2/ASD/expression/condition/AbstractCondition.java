package TP2.ASD.expression.condition;

import TP2.ASD.expression.Expression;

public abstract class AbstractCondition extends Expression {

    protected final Expression instruction;

    protected AbstractCondition(Expression blockOrInstruction) {
        this.instruction = blockOrInstruction;
    }
}
