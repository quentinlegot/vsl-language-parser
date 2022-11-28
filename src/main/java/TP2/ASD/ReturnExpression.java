package TP2.ASD;

import TP2.*;

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
        Instruction returnIns = new Return(indent, new Llvm.Int(), ret.result);
        ret.ir.appendCode(returnIns);
        return ret;
    }
}
