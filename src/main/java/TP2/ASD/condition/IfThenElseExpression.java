package TP2.ASD.condition;

import TP2.ASD.Expression;
import TP2.ASD.RetExpression;
import TP2.SymbolTable;
import TP2.TypeException;

public class IfThenElseExpression extends Expression {

    private final Expression ifPart;
    private final Expression elsePart;

    public IfThenElseExpression(Expression ifPart, /* Nullable */Expression elsePart) {
        this.ifPart = ifPart;
        this.elsePart = elsePart;
    }
    @Override
    public String pp() {
        return ifPart.pp() + (elsePart != null ? elsePart.pp() : "") + "FI";
    }

    @Override
    public RetExpression toIR(SymbolTable table) throws TypeException {
        return null;
    }
}
