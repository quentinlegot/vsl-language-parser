package TP2.operation;

import TP2.Instruction;
import TP2.Llvm;
import TP2.Utils;

public class LoadVariable extends Instruction {

    protected final String name;
    protected final Llvm.Type type;
    protected final String toTmp;
    private final String index;

    public LoadVariable(int indent, String toTmp, Llvm.Type type, String name) {
        this(indent, toTmp, type, name, null);
    }

    public LoadVariable(int indent, String toTmp, Llvm.Type type, String name, String index) {
        super(indent);
        this.toTmp = toTmp;
        this.type = type;
        this.name = name;
        this.index = index;
    }

    @Override
    public String toString() {
        String finalString;
        if(type instanceof Llvm.Tab) {
            String tmpVar = Utils.newtmp();
            Llvm.Type innerType = ((Llvm.Tab<?>) type).getInnerType();
            String tabType = "[" + type.getSize() + " x " + innerType + "]";
            finalString = Utils.indent(indent) + tmpVar + " = getelementptr " + tabType + ", " + tabType+ "* " + name + ", i64 0, i32 " + index + "\n";
            finalString += Utils.indent(indent) + toTmp + " = load " + innerType + ", " + type + " " + tmpVar + "\n";
        } else {
            finalString = Utils.indent(indent) + toTmp + " = load " + type.toString() + ", " + type + "* " + name + "\n";
        }
        return finalString;
    }
}
