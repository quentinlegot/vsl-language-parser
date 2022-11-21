package TP2.ASD;

import TP2.ASD.type.Int;
import TP2.ASD.type.Type;
import TP2.*;

import java.util.LinkedList;

public abstract class AbstractDeclareExpression extends Expression {

    protected final Type type;
    protected final String name;
    protected final LinkedList<AbstractDeclareExpression> othersDeclare;

    protected AbstractDeclareExpression(Type type, String name, LinkedList<AbstractDeclareExpression> othersDeclare) {
        this.type = type;
        this.name = "%" + name;
        this.othersDeclare = othersDeclare;
    }

    @Override
    public String pp() {
        StringBuilder str = new StringBuilder(type.pp() + " ");
        for(AbstractDeclareExpression declare : othersDeclare) {
            str.append(declare.name);
            if(declare instanceof TabDeclareExpression) {
                str.append("[").append(((TabDeclareExpression) declare).numberTab.value).append("]");
            }
            str.append(", ");
        }
        str.append(name);
        if(this instanceof TabDeclareExpression) {
            str.append("[").append(((TabDeclareExpression) this).numberTab.value).append("]");
        }

        return str.append("\n").toString();
    }

    @Override
    public RetExpression toIR(SymbolTable table) throws TypeException {
        if(table.lookup(name) != null)
            throw new IllegalStateException(name + " has already been declared");
        table.add(new SymbolTable.VariableSymbol(type, name));
        String result = "";
        Llvm.IR ir;
        if(!othersDeclare.isEmpty()) {
            AbstractDeclareExpression other = othersDeclare.pop();
            RetExpression ret = other.toIR(table);
            result = ret.result;
            ir = ret.ir;
        } else {
            // last declaration of the list
            ir = new Llvm.IR();
        }
        int numberTab = 1;
        if(this instanceof TabDeclareExpression)
            numberTab = ((TabDeclareExpression) this).numberTab.value;
        Instruction declare = new Declare(type.toLlvmType(), name, result, numberTab);
        ir.appendCode(declare);
        return new RetExpression(ir, new Int(), "");
    }
}
