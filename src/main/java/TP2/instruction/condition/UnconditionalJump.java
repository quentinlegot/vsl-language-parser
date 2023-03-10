package TP2.instruction.condition;

import TP2.instruction.Instruction;
import TP2.Utils;

public class UnconditionalJump extends Instruction {

    private final String label;

    public UnconditionalJump(int indent, String label) {
        super(indent);
        this.label = label;
    }

    @Override
    public String toString() {
        return Utils.indent(indent) + "br label %" + label + "\n";
    }
}
