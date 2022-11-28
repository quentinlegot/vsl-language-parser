package TP2;

import org.antlr.v4.runtime.misc.Pair;

import java.util.List;

public class PrintInstruction extends AbstractIOInstruction {

    public PrintInstruction(int indent, Utils.LLVMStringConstant content, String varName, List<Pair<Llvm.Type, String>> parameters) {
        super(indent, content, varName, parameters, "printf");
    }



}
