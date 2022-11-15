package TP2.ASD;

import TP2.ASD.type.Type;

import java.util.LinkedList;

public class SimpleDeclareExpression extends AbstractDeclareExpression {
    private final IntegerExpression numberTab;

    public SimpleDeclareExpression(Type type, String name, LinkedList<AbstractDeclareExpression> othersDeclare) {
        this(type, name, othersDeclare, new IntegerExpression(1));
    }

    public SimpleDeclareExpression(Type type, String name, LinkedList<AbstractDeclareExpression> othersDeclare, IntegerExpression numberTab) {
        super(type, name, othersDeclare);
        this.numberTab = numberTab;
    }
}
