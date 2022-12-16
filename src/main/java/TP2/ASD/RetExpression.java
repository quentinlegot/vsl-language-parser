package TP2.ASD;

import TP2.ASD.type.Type;
import TP2.llvm.Llvm;

/**
 * Object returned by toIR on expressions, with IR + synthesized attributes
 */
public class RetExpression {
    /**
     * The LLVM IR:
     */
    public Llvm.IR ir;

    // And additional stuff:

    /**
     * The type of the expression
     */
    public Type type;
    /**
     * The name containing the expression's result
     * (either an identifier, or an immediate value)
     */
    public String result;

    /**
     * Construct the object returned by toIR() on expressions.
     * @param ir the IR corresponding to the expression
     * @param type the type of the expression
     * @param result the name containing the expressions' result (either an identifier, or an immediate value)
     */
    public RetExpression(Llvm.IR ir, Type type, String result) {
        this.ir = ir;
        this.type = type;
        this.result = result;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;
        if(obj == null)
            return false;
        if(!(obj instanceof RetExpression))
            return false;
        RetExpression other = (RetExpression) obj;
        return this.ir.equals(other.ir) && this.type.equals(other.type) && this.result.equals(other.result);
    }

    @Override
    public String toString() {
        return "RetExpression[ir=" + this.ir.debug() + ", type=" + this.type.toString() + ",result:" + this.result+ "]";
    }
}
