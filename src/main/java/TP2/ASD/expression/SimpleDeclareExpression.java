package TP2.ASD.expression;

import TP2.ASD.type.Type;

import java.util.LinkedList;

public class SimpleDeclareExpression extends AbstractDeclareExpression {

    public SimpleDeclareExpression(Type type, String name, LinkedList<AbstractDeclareExpression> othersDeclare, Expression content) {
        super(type, name, othersDeclare, content);
    }
}
