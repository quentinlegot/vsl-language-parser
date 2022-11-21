package TP2.ASD;

public abstract class AbstractAffectExpression extends Expression  {

    protected final String name;
    protected final Expression expression;

    protected AbstractAffectExpression(String name, Expression expression) {
        this.name = "%" + name;
        this.expression = expression;
    }

}
