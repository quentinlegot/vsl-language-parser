package TP2.ASD;

import TP2.ASD.type.Type;

import java.util.LinkedList;

public class TabDeclareExpression extends AbstractDeclareExpression {

    protected final IntegerExpression numberTab;

    public TabDeclareExpression(Type type, String name, LinkedList<AbstractDeclareExpression> othersDeclare, IntegerExpression numberTab) {
        super(type, name, othersDeclare);
        this.numberTab = numberTab;
    }
}
