package TP2.function;

import TP2.Instruction;
import TP2.Llvm;
import TP2.Utils;
import org.antlr.v4.runtime.misc.Pair;

import java.util.List;

public class FunctionCallInstruction extends Instruction {

    private final String ident;
    private final List<Pair<Llvm.Type, String>> parameters;
    private final String storeVar;
    private final Llvm.Type returnType;

    public FunctionCallInstruction(int indent, String ident, List<Pair<Llvm.Type, String>> parametersList, String tmpVar, Llvm.Type returnType) {
        super(indent);
        this.ident = ident;
        this.parameters = parametersList;
        this.storeVar = tmpVar;
        this.returnType = returnType;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder(Utils.indent(indent));
        if(storeVar != null && !returnType.equals(new Llvm.Void())) {
            str.append(storeVar).append(" = ");
        }
        str.append("call ").append(returnType.toString()).append(" ").append(ident).append("(");
        for(int i = 0; i < parameters.size(); i++) {
            Pair<Llvm.Type, String> par = parameters.get(i);
            str.append(par.a.toString()).append(" ").append(par.b);
            if(i != parameters.size() - 1) {
                str.append(", ");
            }
        }
        return str.append(")\n").toString();
    }
}