package TP2.ASD;

import TP2.ASD.type.Char;
import TP2.ASD.type.Tab;
import TP2.*;

import java.util.LinkedList;

public class PrintExpression extends Expression {

    private final LinkedList<Expression> content;

    public PrintExpression(LinkedList<Expression> content) {
        this.content = content;
    }

    @Override
    public String pp() {
        StringBuilder str = new StringBuilder("PRINT ");
        for(int i = 0; i < content.size(); i++) {
            Expression expression = content.get(i);
            if(expression instanceof StringExpression) {
                str.append("\"").append(expression.pp()).append("\"");
            } else {
                str.append(expression.pp());
            }
            if(i != content.size() - 1) {
                str.append(", ");
            }
        }
        return str.toString();
    }

    @Override
    public RetExpression toIR(SymbolTable table) throws TypeException {
        StringBuilder str = new StringBuilder("c\"");
        for (Expression expression : content) {
            if (expression instanceof StringExpression) {
                str.append(expression.toIR(table).result);
            } else {
                str.append("%d");
            }
        }
        str.append("\"");
        Utils.LLVMStringConstant result = Utils.stringTransform(str.toString());
        RetExpression ret = new RetExpression(new Llvm.IR(), new Tab<>(new Char(), result.getLength()), result.getStr());
        String globalVar = Utils.newglob("fmt");
        Instruction globalIns = new DeclareGlobalVarInstruction(globalVar, result.getStr(), result.getLength(), new Llvm.Char());
        ret.ir.appendHeader(globalIns);
        return ret;
    }
}
