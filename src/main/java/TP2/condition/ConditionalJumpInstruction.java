package TP2.condition;

import TP2.Instruction;

public class ConditionalJumpInstruction extends Instruction {


    private final String conditionVar;
    private final String labelIfTrue;
    private final String labelIfFalse;

    public ConditionalJumpInstruction(String conditionVar, String labelIfTrue, String labelIfFalse) {
        this.conditionVar = conditionVar;
        this.labelIfTrue = labelIfTrue;
        this.labelIfFalse = labelIfFalse;
    }

    @Override
    public String toString() {
        return "br il " + conditionVar + ", label %" + labelIfTrue + ", label %" + labelIfFalse + "\n";
    }
}
