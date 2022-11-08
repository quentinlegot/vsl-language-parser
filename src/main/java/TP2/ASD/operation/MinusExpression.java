package TP2.ASD.operation;

import TP2.ASD.Expression;
import TP2.ASD.RetExpression;
import TP2.Instruction;
import TP2.TypeException;
import TP2.Utils;
import TP2.operation.Minus;

public class MinusExpression extends Expression {

    Expression left;
    Expression right;

    public MinusExpression(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    /**
     * Pretty-printer
     */
    public String pp() {
        return "(" + left.pp() + " - " + right.pp() + ")";
    }
    public RetExpression toIR() throws TypeException {
        left.setTable(table);
        right.setTable(table);
        RetExpression leftRet = left.toIR();
        RetExpression rightRet = right.toIR();

        // We check if the types mismatches
        if(!leftRet.type.equals(rightRet.type)) {
            throw new TypeException("type mismatch: have " + leftRet.type + " and " + rightRet.type);
        }

        // We base our build on the left generated IR:
        // append right code
        leftRet.ir.append(rightRet.ir);

        // allocate a new identifier for the result
        String result = Utils.newtmp();

        // new add instruction result = left + right
        Instruction minus = new Minus(leftRet.type.toLlvmType(), leftRet.result, rightRet.result, result);

        // append this instruction
        leftRet.ir.appendCode(minus);

        // return the generated IR, plus the type of this expression
        // and where to find its result
        return new RetExpression(leftRet.ir, leftRet.type, result);
    }
}
