package TP2.ASD.condition;

import TP2.ASD.Expression;

public abstract class AbstractCondition extends Expression {

    protected final Expression instruction;

    protected AbstractCondition(Expression blockOrInstruction) {
        this.instruction = blockOrInstruction;
    }
}
