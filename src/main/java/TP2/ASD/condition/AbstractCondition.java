package TP2.ASD.condition;

import TP2.ASD.Expression;

public abstract class AbstractCondition extends Expression {

    /**
     * Instruction represent a block or a unique instruction following 'THEN' in VSL+
     */
    protected final Expression instruction;

    protected AbstractCondition(Expression blockOrInstruction) {
        this.instruction = blockOrInstruction;
    }
}
