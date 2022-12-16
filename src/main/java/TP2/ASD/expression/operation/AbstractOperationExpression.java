package TP2.ASD.expression.operation;

import TP2.ASD.expression.Expression;
import TP2.ASD.RetExpression;
import TP2.*;
import TP2.instruction.Instruction;
import TP2.llvm.type.Type;

public abstract class AbstractOperationExpression extends Expression {

    protected Expression left;
    protected Expression right;
    protected String ppSymbol;

    protected AbstractOperationExpression(Expression left, Expression right, String ppSymbol) {
        this.left = left;
        this.right = right;
        this.ppSymbol = ppSymbol;
    }

    @Override
    public String pp() {
        return  "(" + left.pp() + " " + ppSymbol + " " + right.pp() + ")";
    }

    @Override
    public RetExpression toIR(SymbolTable table, int indent) throws TypeException {
        RetExpression leftRet = left.toIR(table, indent);
        RetExpression rightRet = right.toIR(table, indent);
        if(!leftRet.type.equals(rightRet.type)) {
            throw new TypeException("type mismatch: have " + leftRet.type + " and " + rightRet.type);
        }
        leftRet.ir.append(rightRet.ir);
        String result = Utils.newtmp();
        Instruction ins = operationInstruction(indent, leftRet.type.toLlvmType(), leftRet.result, rightRet.result, result);
        leftRet.ir.appendCode(ins);
        return new RetExpression(leftRet.ir, leftRet.type, result);
    }

    protected abstract Instruction operationInstruction(int indent, Type type, String leftResult, String rightResult, String tmpVar);
}
