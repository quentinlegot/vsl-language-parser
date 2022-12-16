package TP2.ASD.expression;

import TP2.*;
import TP2.ASD.RetExpression;
import TP2.instruction.Instruction;
import TP2.instruction.Return;

public class ReturnExpression extends Expression {

    private final Expression content;

    public ReturnExpression(Expression content) {
        this.content = content;
    }

    @Override
    public String pp() {
        return "RETURN " + content.pp() + "\n";
    }

    @Override
    public RetExpression toIR(SymbolTable table, int indent) throws TypeException {
        RetExpression ret = content.toIR(table, indent);
        Instruction returnIns = new Return(indent, ret.type.toLlvmType(), ret.result);
        ret.ir.appendCode(returnIns);
        return ret;
    }
}
