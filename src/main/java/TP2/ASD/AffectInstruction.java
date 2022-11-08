package TP2.ASD;

public class AffectInstruction extends AsdInstruction {

    private final String varName;
    private final Expression expression;
    private final boolean newVar;

    public AffectInstruction(boolean newVar, String varName, Expression expression) {
        this.newVar = newVar;
        this.varName = varName;
        this.expression = expression;
    }

    @Override
    public String pp() {
        String str = "";
        if(newVar)
            str += "INT ";
        str += varName + " := " + expression.pp();
        return str;
    }
}
