package TP2.ASD.condition;

import TP2.ASD.Expression;
import TP2.ASD.RetExpression;
import TP2.SymbolTable;
import TP2.TypeException;

public class ElseConditionExpression extends AbstractCondition {

    public ElseConditionExpression(Expression blockOrInstruction) {
        super(blockOrInstruction);
    }
    @Override
    public String pp() {
        return "ELSE" + instruction.pp();
    }

    @Override
    public RetExpression toIR(SymbolTable table) throws TypeException {
        return null;
    }
}
