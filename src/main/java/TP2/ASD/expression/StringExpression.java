package TP2.ASD.expression;

import TP2.ASD.RetExpression;
import TP2.ASD.type.Char;
import TP2.ASD.type.Tab;
import TP2.Llvm;
import TP2.SymbolTable;
import TP2.TypeException;

public class StringExpression extends Expression {

    private final String content;

    public StringExpression(String content) {
        this.content = content;
    }

    @Override
    public String pp() {
        return content;
    }

    @Override
    public RetExpression toIR(SymbolTable table, int indent) throws TypeException {
        return new RetExpression(new Llvm.IR(), new Tab<>(new Char(), content.length()), content);
    }
}
