package TP2.operation;

import TP2.Instruction;
import TP2.Llvm;
import TP2.Utils;

public class LoadVariable extends Instruction {

    protected final String name;
    protected final Llvm.Type type;
    protected final String toTmp;

    public LoadVariable(int indent, String toTmp, Llvm.Type type, String name) {
        super(indent);
        this.toTmp = toTmp;
        this.type = type;
        this.name = name;
    }

    @Override
    public String toString() {
        return Utils.indent(indent) + toTmp + " = load " + type.toString() + ", " + type.toString() + "* " + name + "\n";
    }
}
