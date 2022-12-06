package TP2.function;

import TP2.ASD.type.Type;
import TP2.Instruction;
import TP2.Llvm;
import org.antlr.v4.runtime.misc.Pair;

import java.util.LinkedList;

public class PrototypeInstruction extends Instruction {

    private final Llvm.Type type;
    private final String ident;
    private final LinkedList<Pair<Type, String>> parameters;

    public PrototypeInstruction(int indent, Llvm.Type type, String ident, LinkedList<Pair<Type, String>> parameters) {
        super(indent);
        this.type = type;
        this.ident = ident;
        this.parameters = parameters;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("define " + type + " " + ident + "(");
        if(parameters != null) {
            for(int i = 0; i < parameters.size(); i++) {
                Pair<Type, String> parameter = parameters.get(i);
                str.append(parameter.a.toLlvmType()).append(" ").append("%" + parameter.b);
                if(i != parameters.size() - 1) {
                    str.append(", ");
                }
            }
        }
        return str.append(")\n").toString();
    }
}
