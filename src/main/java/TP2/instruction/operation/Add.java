package TP2.instruction.operation;

import TP2.instruction.Instruction;
import TP2.Utils;
import TP2.llvm.type.Type;

/**
 * Class representing the add instruction
 */
public class Add extends Instruction {
   private final Type type;
    private final String left;
    private final String right;
    private final String lvalue;

    /**
     * The add instruction.
     * lvalue = left + right
     * @param type The type of lvalue, left and right
     * @param left the left side of the addition
     * @param right the right side of the addition
     * @param lvalue the variable containing the result of the addition
     */
    public Add(int indent, Type type, String left, String right, String lvalue) {
        super(indent);
        this.type = type;
        this.left = left;
        this.right = right;
        this.lvalue = lvalue;
    }

    public String toString() {
        return Utils.indent(indent) + lvalue + " = add " + type + " " + left + ", " + right +  "\n";
    }

    @Override
    public boolean equals(Object obj) {
       if(obj == this)
           return true;
       if(obj == null)
           return false;
       if(!(obj instanceof Add))
           return false;
       Add o = (Add) obj;
       return this.left.equals(o.left) && this.right.equals(o.right) && this.type.equals(o.type) && this.lvalue.equals(o.lvalue);
    }
}
