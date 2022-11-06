package TP2.operation;

import TP2.Instruction;
import TP2.Llvm;

/**
 * Class representing the add instruction
 */
public class Mul extends Instruction {
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
    public Mul(Llvm.Type type, String left, String right, String lvalue) {
        this.type = type;
        this.left = left;
        this.right = right;
        this.lvalue = lvalue;
    }

    @Override
    public String toString() {
        return lvalue + " = mul " + type + " " + left + ", " + right +  "\n";
    }
}
