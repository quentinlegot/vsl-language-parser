package TP2.instruction.condition;

import TP2.instruction.Instruction;

public class LabelInstruction extends Instruction {

    private final String label;

    public LabelInstruction(String label) {
        super(0);
        this.label = label;
    }

    @Override
    public String toString() {
        return label + ":\n"; // jump
    }
}
