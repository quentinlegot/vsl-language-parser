package TP2;

import org.antlr.v4.runtime.misc.Pair;

import java.util.List;

public class PrintInstruction extends Instruction {

    private final Utils.LLVMStringConstant content;
    private final List<Pair<Llvm.Type, String>> parameters;
    private final String name;

    public PrintInstruction(int indent, Utils.LLVMStringConstant content, String name, List<Pair<Llvm.Type, String>> parameters) {
        super(indent);
        this.content = content;
        this.name = name;
        this.parameters = parameters;
    }

    @Override
    public String toString() {
        String sizeFormat = "[" + content.getLength() + " x " + "i8]";
        StringBuilder str = new StringBuilder(Utils.indent(indent) + "call i32 (i8*, ...) @printf(i8* getelementptr inbounds " +
                "(" + sizeFormat + ", " + sizeFormat +"* @" + name + ", i64 0, i64 0))");
        for (Pair<Llvm.Type, String> parameter : parameters) {
            str.append(", ").append(parameter.a.toString()).append(" ").append(parameter.b);
        }
        return str.append("\n").toString();
    }



}
