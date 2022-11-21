package TP2.ASD.condition;

import TP2.ASD.Expression;
import TP2.ASD.RetExpression;
import TP2.SymbolTable;
import TP2.TypeException;

public class BooleanConditionExpression extends Expression {

    private final Expression expression;

    public BooleanConditionExpression(Expression expression) {
        this.expression = expression;
    }

    @Override
    public String pp() {
        return expression.pp();
    }

    @Override
    public RetExpression toIR(SymbolTable table) throws TypeException {
        return null;
    }
}
