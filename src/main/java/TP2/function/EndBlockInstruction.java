package TP2.function;

import TP2.Instruction;
import TP2.Utils;

public class EndBlockInstruction extends Instruction {

    public EndBlockInstruction(int indent) {
        super(indent);
    }

    @Override
    public String toString() {
        return Utils.indent(indent) + "}\n";
    }
}
