package TP2.instruction.operation;

import TP2.instruction.Instruction;
import TP2.Llvm;
import TP2.Utils;

public class Div extends Instruction {

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
    public Div(int indent, Llvm.Type type, String left, String right, String lvalue) {
        super(indent);
        this.type = type;
        this.left = left;
        this.right = right;
        this.lvalue = lvalue;
    }

    @Override
    public String toString() {
        return Utils.indent(indent) + lvalue + " = sdiv " + type + " " + left + ", " + right +  "\n";
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this)
            return true;
        if(obj == null)
            return false;
        if(!(obj instanceof Div))
            return false;
        Div o = (Div) obj;
        return this.left.equals(o.left) && this.right.equals(o.right) && this.type.equals(o.type) && this.lvalue.equals(o.lvalue);
    }
}
