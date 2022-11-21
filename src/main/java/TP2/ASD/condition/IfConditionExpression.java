package TP2.ASD.condition;

import TP2.ASD.Expression;
import TP2.ASD.RetExpression;
import TP2.SymbolTable;
import TP2.TypeException;

public class IfConditionExpression extends AbstractCondition {

    private final Expression booleanExpression;

    public IfConditionExpression(Expression booleanExpression, Expression blockOrInstruction) {
        super(blockOrInstruction);
        this.booleanExpression = booleanExpression;
    }

    @Override
    public String pp() {
        return "IF " + booleanExpression.pp() + " THEN\n" + instruction.pp();
    }

    @Override
    public RetExpression toIR(SymbolTable table) throws TypeException {
        return null;
    }
}
