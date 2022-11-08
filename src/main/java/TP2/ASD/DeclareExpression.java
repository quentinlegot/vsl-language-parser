package TP2.ASD;

import TP2.ASD.type.Int;
import TP2.ASD.type.Tab;
import TP2.ASD.type.Type;
import TP2.*;

import java.util.LinkedList;

public class DeclareExpression extends Expression {

    private final Type type;
    private final String name;
    private final LinkedList<DeclareExpression> othersDeclare;
    private final IntegerExpression numberTab;

    public DeclareExpression(Type type, String name, LinkedList<DeclareExpression> othersDeclare) {
        this(type, name, othersDeclare, new IntegerExpression(1));
    }

    public DeclareExpression(Type type, String name, LinkedList<DeclareExpression> othersDeclare, IntegerExpression numberTab) {
        this.type = type;
        this.name = name;
        this.othersDeclare = othersDeclare;
        this.numberTab = numberTab;
    }

    @Override
    public String pp() {
        StringBuilder str = new StringBuilder(type.pp() + " ");
        for(DeclareExpression declare : othersDeclare) {
            str.append(declare.name);
            if(declare.type instanceof Tab) {
                str.append("[").append(numberTab.value).append("]");
            }
            str.append(", ");
        }
        str.append(name);
        if(type instanceof Tab) {
            str.append("[").append(numberTab.value).append("]");
        }

        return str.append("\n").toString();
    }

    @Override
    public RetExpression toIR() throws TypeException {
        if(table.lookup(name) != null)
            throw new IllegalStateException(name + " has already been declared");
        table.add(new SymbolTable.VariableSymbol(type, name));
        String result = "";
        Llvm.IR ir;
        if(!othersDeclare.isEmpty()) {
            DeclareExpression other = othersDeclare.pop();
            other.setTable(table);
            RetExpression ret = other.toIR();
            result = ret.result;
            ir = ret.ir;
        } else {
            // last declaration of the list
            ir = new Llvm.IR();
        }
        Instruction declare = new Declare(type.toLlvmType(), name, result, numberTab.value);
        ir.appendCode(declare);
        return new RetExpression(ir, new Int(), "");
    }
}
