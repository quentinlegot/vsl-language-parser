package TP2.operation;

import TP2.Instruction;
import TP2.Llvm;
import TP2.Utils;

/**
 * Class representing the add instruction
 */
public class Add extends Instruction {
    Llvm.Type type;
    String left;
    String right;
    String lvalue;

    /**
     * The add instruction.
     * lvalue = left + right
     * @param type The type of lvalue, left and right
     * @param left the left side of the addition
     * @param right the right side of the addition
     * @param lvalue the variable containing the result of the addition
     */
    public Add(int indent, Llvm.Type type, String left, String right, String lvalue) {
        super(indent);
        this.type = type;
        this.left = left;
        this.right = right;
        this.lvalue = lvalue;
    }

    public String toString() {
        return Utils.indent(indent) + lvalue + " = add " + type + " " + left + ", " + right +  "\n";
    }
}
