package TP2.ASD.operation;

import TP2.ASD.Expression;
import TP2.ASD.RetExpression;
import TP2.ASD.type.Int;
import TP2.Llvm;
import TP2.TypeException;

public class VariableExpression extends Expression {

    private final String name;

    public VariableExpression(String name) {
        this.name = name;
    }

    @Override
    public String pp() {
        return name;
    }

    @Override
    public RetExpression toIR() throws TypeException {
        if(table.lookup(name) == null)
            throw new NullPointerException(name + " variable is undeclared");
        return new RetExpression(new Llvm.IR(Llvm.empty(), Llvm.empty()), new Int(), "%" + name);
    }
}
