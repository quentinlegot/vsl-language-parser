package TP2.condition;

import TP2.Instruction;
import TP2.Utils;

public class ConditionalJumpInstruction extends Instruction {


    private final String conditionVar;
    private final String labelIfTrue;
    private final String labelIfFalse;

    public ConditionalJumpInstruction(int indent, String conditionVar, String labelIfTrue, String labelIfFalse) {
        super(indent);
        this.conditionVar = conditionVar;
        this.labelIfTrue = labelIfTrue;
        this.labelIfFalse = labelIfFalse;
    }

    @Override
    public String toString() {
        return Utils.indent(indent) + "br i1 " + conditionVar + ", label %" + labelIfTrue + ", label %" + labelIfFalse + "\n";
    }
}
