package TP2.ASD.expression;

import TP2.ASD.RetExpression;
import TP2.ASD.type.Int;
import TP2.ASD.type.Type;
import TP2.*;
import TP2.instruction.Affect;
import TP2.instruction.Declare;
import TP2.instruction.Instruction;

import java.util.LinkedList;

public abstract class AbstractDeclareExpression extends Expression {

    protected final Type type;
    protected final String name;
    protected final LinkedList<AbstractDeclareExpression> othersDeclare;
    private final Expression content;

    protected AbstractDeclareExpression(Type type, String name, LinkedList<AbstractDeclareExpression> othersDeclare, Expression content) {
        this.type = type;
        this.name = "%" + name;
        this.othersDeclare = othersDeclare;
        this.content = content;
    }

    @Override
    public String pp() {
        StringBuilder str = new StringBuilder(type.pp() + " ");
        for(AbstractDeclareExpression declare : othersDeclare) {
            str.append(declare.name);
            if(declare instanceof TabDeclareExpression) {
                str.append("[").append(((TabDeclareExpression) declare).numberTab.getValue()).append("]");
            }
            str.append(", ");
        }
        str.append(name);
        if(this instanceof TabDeclareExpression) {
            str.append("[").append(((TabDeclareExpression) this).numberTab.getValue()).append("]");
        }
        if(content != null) {
            str.append(" = ").append(content.pp());
        }
        return str.append("\n").toString();
    }

    @Override
    public RetExpression toIR(SymbolTable table, int indent) throws TypeException {
        if(table.lookup(name) != null)
            throw new IllegalStateException(name + " has already been declared");
        table.add(new SymbolTable.VariableSymbol(type, name));
        String result = "";
        Llvm.IR ir;
        if(!othersDeclare.isEmpty()) {
            AbstractDeclareExpression other = othersDeclare.pop();
            RetExpression ret = other.toIR(table, indent);
            result = ret.result;
            ir = ret.ir;
        } else {
            // last declaration of the list
            ir = new Llvm.IR();
        }
        int numberTab = 1;
        if(this instanceof TabDeclareExpression)
            numberTab = ((TabDeclareExpression) this).numberTab.getValue();
        Instruction declare = new Declare(indent, type.toLlvmType(), name, result, numberTab);
        ir.appendCode(declare);
        if(content != null) {
            RetExpression content = this.content.toIR(table, indent);
            ir.append(content.ir);
            Instruction affect = new Affect(indent, type.toLlvmType(), name, content.result, null);
            ir.appendCode(affect);
        }



        return new RetExpression(ir, new Int(), "");
    }
}
