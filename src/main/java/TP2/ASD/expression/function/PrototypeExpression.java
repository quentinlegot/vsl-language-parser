package TP2.ASD.expression.function;

import TP2.ASD.RetExpression;
import TP2.ASD.type.Type;
import TP2.Llvm;
import TP2.SymbolTable;
import TP2.TypeException;
import org.antlr.v4.runtime.misc.Pair;

import java.util.LinkedList;

public class PrototypeExpression extends AbstractFunctionExpression {

    public PrototypeExpression(Type type, String ident, LinkedList<Pair<Type, String>> parameters) {
        super(type, ident, parameters);
    }

    @Override
    public String pp() {
        return "PROTO " + type.pp() + " " + ident + "(" + super.pp() + ")\n";
    }

    @Override
    public RetExpression toIR(SymbolTable table, int indent) throws TypeException {
        SymbolTable.Symbol symbol = table.lookup("@" + ident);
        if(symbol == null) {
            return new RetExpression(new Llvm.IR(), type, ""); // we display nothing, we wait for the definition to display something
        } else {
            throw new IllegalArgumentException(ident + " has already been declared");
        }
    }
}
