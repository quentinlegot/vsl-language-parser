package TP2.instruction.operation;

import TP2.instruction.Instruction;
import TP2.Utils;
import TP2.llvm.type.Tab;
import TP2.llvm.type.Type;

import java.util.Objects;

public class LoadVariable extends Instruction {

    protected final String name;
    protected final Type type;
    protected final String toTmp;
    private final String index;

    public LoadVariable(int indent, String toTmp, Type type, String name) {
        this(indent, toTmp, type, name, null);
    }

    public LoadVariable(int indent, String toTmp, Type type, String name, String index) {
        super(indent);
        this.toTmp = toTmp;
        this.type = type;
        this.name = name;
        this.index = index;
    }

    @Override
    public String toString() {
        String finalString;
        if(type instanceof Tab) {
            String tmpVar = Utils.newtmp();
            Type innerType = ((Tab<?>) type).getInnerType();
            String tabType = "[" + type.getSize() + " x " + innerType + "]";
            finalString = Utils.indent(indent) + tmpVar + " = getelementptr " + tabType + ", " + tabType+ "* " + name + ", i64 0, i32 " + index + "\n";
            finalString += Utils.indent(indent) + toTmp + " = load " + innerType + ", " + type + " " + tmpVar + "\n";
        } else {
            finalString = Utils.indent(indent) + toTmp + " = load " + type.toString() + ", " + type + "* " + name + "\n";
        }
        return finalString;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this)
            return true;
        if(obj == null)
            return false;
        if(!(obj instanceof LoadVariable))
            return false;
        LoadVariable o = (LoadVariable) obj;
        return this.name.equals(o.name) && this.toTmp.equals(o.toTmp) && this.type.equals(o.type)
                && this.indent == o.indent && Objects.equals(this.index, o.index);
    }
}
