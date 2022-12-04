package TP2.operation;

import TP2.Instruction;
import TP2.Llvm;
import TP2.Utils;

public class Sub extends Instruction {
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
    public Sub(int indent, Llvm.Type type, String left, String right, String lvalue) {
        super(indent);
        this.type = type;
        this.left = left;
        this.right = right;
        this.lvalue = lvalue;
    }

    public String toString() {
        return Utils.indent(indent) + lvalue + " = sub " + type + " " + left + ", " + right +  "\n";
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this)
            return true;
        if(obj == null)
            return false;
        if(!(obj instanceof Sub))
            return false;
        Sub o = (Sub) obj;
        return this.left.equals(o.left) && this.right.equals(o.right) && this.type.equals(o.type) && this.lvalue.equals(o.lvalue);
    }
}
