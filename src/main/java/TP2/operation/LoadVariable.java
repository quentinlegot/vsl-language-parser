package TP2.operation;

import TP2.Instruction;
import TP2.Llvm;

public class LoadVariable extends Instruction {

    private final String name;
    private final Llvm.Type type;
    private final String toTmp;

    public LoadVariable(String toTmp, Llvm.Type type, String name) {
        this.toTmp = toTmp;
        this.type = type;
        this.name = name;
    }

    @Override
    public String toString() {
        return toTmp + " = load " + type.toString() + ", " + type.toString() + "* " + name + "\n";
    }
}
