package TP2.instruction.function;

import TP2.instruction.Instruction;
import TP2.Utils;

public class StartBlockInstruction extends Instruction {
    public StartBlockInstruction(int indent) {
        super(indent);
    }

    @Override
    public String toString() {
        return Utils.indent(indent) + "{\n";
    }
}
