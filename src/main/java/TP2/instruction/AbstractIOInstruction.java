package TP2.instruction;

import TP2.Utils;
import TP2.llvm.type.Type;
import org.antlr.v4.runtime.misc.Pair;

import java.util.List;

public abstract class AbstractIOInstruction extends Instruction {

    protected final Utils.LLVMStringConstant content;
    protected final String varName;
    protected final List<Pair<Type, String>> parameters;
    protected final String fnName;

    protected AbstractIOInstruction(int indent, Utils.LLVMStringConstant content, String varName, List<Pair<Type, String>> parameters, String fnName) {
        super(indent);
        this.content = content;
        this.varName = varName;
        this.parameters = parameters;
        this.fnName = fnName;
    }

    @Override
    public String toString() {
        String sizeFormat = "[" + content.getLength() + " x " + "i8]";
        StringBuilder str = new StringBuilder(Utils.indent(indent) + "call i32 (i8*, ...) @" + fnName + "(i8* getelementptr inbounds " +
                "(" + sizeFormat + ", " + sizeFormat +"* @" + varName + ", i64 0, i64 0)");
        for (Pair<Type, String> parameter : parameters) {
            str.append(", ").append(parameter.a.toString()).append(" ").append(parameter.b);
        }
        return str.append(")\n").toString();
    }
}
