package TP2.ASD;

import TP2.ASD.type.Void;
import TP2.*;
import org.antlr.v4.runtime.misc.Pair;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class PrintExpression extends Expression {

    private final LinkedList<Expression> content;

    public PrintExpression(LinkedList<Expression> content) {
        this.content = content;
    }

    @Override
    public String pp() {
        StringBuilder str = new StringBuilder();
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
        return "PRINT " + str.append("\n");
    }

    @Override
    public RetExpression toIR(SymbolTable table, int indent) throws TypeException {
        RetExpression ret = new RetExpression(new Llvm.IR(), new Void(), "");
        StringBuilder str = new StringBuilder();
        List<Pair<Llvm.Type, String>> parameters = new ArrayList<>();
        for (Expression expression : content) {
            if (expression instanceof StringExpression) {
                str.append(expression.toIR(table, indent).result);
            } else {
                str.append("%d");
                RetExpression ret1 = expression.toIR(table, indent);
                ret.ir.append(ret1.ir);
                parameters.add(new Pair<>(ret1.type.toLlvmType(), ret1.result));
            }
        }
        Utils.LLVMStringConstant result = Utils.stringTransform(str.toString());
        String globalVar = Utils.newglob("fmt");
        Instruction globalIns = new DeclareGlobalVarInstruction(globalVar, result.getStr(), result.getLength(), new Llvm.Char());
        ret.ir.appendHeader(globalIns);
        Instruction printIns = new PrintInstruction(indent, result, globalVar, parameters);
        ret.ir.appendCode(printIns);
        return ret;
    }
}
