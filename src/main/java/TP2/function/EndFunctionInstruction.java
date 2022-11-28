package TP2.function;

import TP2.Instruction;

public class EndFunctionInstruction extends Instruction {

    public EndFunctionInstruction() {
        super(0);
    }

    @Override
    public String toString() {
        return "}\n";
    }
}
