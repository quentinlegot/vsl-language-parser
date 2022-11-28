package TP2.ASD.function;

import TP2.ASD.Expression;
import TP2.ASD.type.Type;
import org.antlr.v4.runtime.misc.Pair;

import java.util.LinkedList;

public abstract class AbstractFunctionExpression extends Expression {


    protected final Type type;
    protected final String ident;
    protected final LinkedList<Pair<Type, String>> parameters;

    protected AbstractFunctionExpression(Type type, String ident, LinkedList<Pair<Type, String>> parameters) {
        this.type = type;
        this.ident = ident;
        this.parameters = parameters;
    }

    public String getIdent() {
        return ident;
    }

    @Override
    public String pp() {
        StringBuilder str = new StringBuilder();
        if(parameters != null) {
            for(int i = 0; i < parameters.size(); i++) {
                Pair<Type, String> parameter = parameters.get(i);
                str.append(parameter.a.pp()).append(" ").append(parameter.b);
                if(i != parameters.size() - 1) {
                    str.append(", ");
                }
            }
        }
        return str.toString();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + ": " + getIdent();
    }
}
