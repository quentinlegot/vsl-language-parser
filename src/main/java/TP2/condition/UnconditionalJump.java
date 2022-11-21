package TP2.condition;

import TP2.Instruction;

public class UnconditionalJump extends Instruction {

    private final String label;

    public UnconditionalJump(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return "br label %" + label + "\n";
    }
}
