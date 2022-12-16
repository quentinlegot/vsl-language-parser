package TP2.instruction;

import TP2.Utils;
import TP2.llvm.type.Tab;
import TP2.llvm.type.Type;

public class Declare extends Instruction {

    private final String ident;
    private final int number;
    private final Type type;
    private final String value;

    /**
     * The return instruction
     *
     * @param type  type of the return value
     * @param value value to be returned
     */
    public Declare(int indent, Type type, String ident, String value, int number) {
        super(indent);
        this.type = type;
        this.ident = ident;
        this.value = value;
        this.number = number;
    }

    @Override
    public String toString() {
        String str = Utils.indent(indent) + value + ident + " = alloca ";
        if(type instanceof Tab) {
            Tab<?> tab = (Tab<?>) type;
            str += "[" + number + " x " + tab.type + "]\n";
        }
        else {
            str += type + "\n";
        }
        return str;
    }
}
