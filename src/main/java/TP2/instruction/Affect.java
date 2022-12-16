package TP2.instruction;

import TP2.Utils;
import TP2.llvm.type.Tab;
import TP2.llvm.type.Type;

public class Affect extends Instruction {

    private final String ident;
    private final String index;
    private final Type type;
    private final String value;

    /**
     * The return instruction
     * @param type type of the return value
     * @param value value to be returned
     */
    public Affect(int indent, Type type, String ident, String value, String index) {
        super(indent);
        this.type = type;
        this.ident = ident;
        this.value = value;
        this.index = index;
    }

    @Override
    public String toString() {
        if(type instanceof Tab) {
            Type innerType = ((Tab<?>) type).getInnerType();
            String tmpVar = Utils.newtmp();
            String indexVar;
            Object tabType =  "[" + type.getSize() + " x " + innerType + "]";
            if(index.startsWith("%")) { // not a IntegerExpression
                String[] lines = index.split("\n");
                indexVar = lines[lines.length - 1].split(" ")[0];
            } else {
                indexVar = index;
            }
            String str = Utils.indent(indent) + tmpVar + " = getelementptr " + tabType + ", " + tabType + "* " + ident + ", i64 0, i32 " + indexVar + "\n";
            str += Utils.indent(indent) + "store " + innerType.toString() + " " + value + ", " + type + " " + tmpVar + "\n";
            return str;
        } else {
            return Utils.indent(indent) + "store " + type.toString() + " " +  value + ", " + type + "* " + ident + "\n";
        }

    }
}
