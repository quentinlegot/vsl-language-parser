package TP2.ASD.operation;

import TP2.ASD.Expression;
import TP2.ASD.RetExpression;
import TP2.Instruction;
import TP2.SymbolTable;
import TP2.TypeException;
import TP2.Utils;
import TP2.operation.Mul;

public class MulExpression extends Expression {

    Expression left;
    Expression right;

    public MulExpression(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }
    @Override
    public String pp() {
        return "(" + left.pp() + " * " + right.pp() + ")";
    }

    @Override
    public RetExpression toIR(SymbolTable table, int indent) throws TypeException {
        RetExpression leftRet = left.toIR(table, indent);
        RetExpression rightRet = right.toIR(table, indent);

        if(!leftRet.type.equals(rightRet.type)) {
            throw new TypeException("Type mismatch: have " + leftRet.type + " and " + rightRet.type);
        }

        leftRet.ir.append(rightRet.ir);
        String result = Utils.newtmp();
        Instruction mul = new Mul(indent, leftRet.type.toLlvmType(), leftRet.result, rightRet.result, result);
        leftRet.ir.appendCode(mul);
        return new RetExpression(leftRet.ir, leftRet.type, result);
    }
}
