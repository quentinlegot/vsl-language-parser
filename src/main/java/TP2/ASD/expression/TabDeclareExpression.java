package TP2.ASD.expression;

import TP2.ASD.expression.operation.IntegerExpression;
import TP2.ASD.type.Type;

import java.util.LinkedList;

public class TabDeclareExpression extends AbstractDeclareExpression {

    protected final IntegerExpression numberTab;

    public TabDeclareExpression(Type type, String name, LinkedList<AbstractDeclareExpression> othersDeclare, IntegerExpression numberTab) {
        super(type, name, othersDeclare, null);
        this.numberTab = numberTab;
    }
}
