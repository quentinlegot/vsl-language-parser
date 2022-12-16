package TP2.instruction;

import TP2.Llvm;
import TP2.Utils;

public class Declare extends Instruction {

    private final String ident;
    private final int number;
    Llvm.Type type;
    String value;

    /**
     * The return instruction
     *
     * @param type  type of the return value
     * @param value value to be returned
     */
    public Declare(int indent, Llvm.Type type, String ident, String value, int number) {
        super(indent);
        this.type = type;
        this.ident = ident;
        this.value = value;
        this.number = number;
    }

    @Override
    public String toString() {
        String str = Utils.indent(indent) + value + ident + " = alloca ";
        if(type instanceof Llvm.Tab) {
            Llvm.Tab<?> tab = (Llvm.Tab<?>) type;
            str += "[" + number + " x " + tab.type + "]\n";
        }
        else {
            str += type + "\n";
        }
        return str;
    }
}
